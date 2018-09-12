/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { ViajatoTestModule } from '../../../test.module';
import { SeguradoraUpdateComponent } from 'app/entities/seguradora/seguradora-update.component';
import { SeguradoraService } from 'app/entities/seguradora/seguradora.service';
import { Seguradora } from 'app/shared/model/seguradora.model';

describe('Component Tests', () => {
    describe('Seguradora Management Update Component', () => {
        let comp: SeguradoraUpdateComponent;
        let fixture: ComponentFixture<SeguradoraUpdateComponent>;
        let service: SeguradoraService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ViajatoTestModule],
                declarations: [SeguradoraUpdateComponent]
            })
                .overrideTemplate(SeguradoraUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(SeguradoraUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SeguradoraService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Seguradora(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.seguradora = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Seguradora();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.seguradora = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
