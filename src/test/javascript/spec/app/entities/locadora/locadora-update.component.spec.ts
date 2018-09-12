/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { ViajatoTestModule } from '../../../test.module';
import { LocadoraUpdateComponent } from 'app/entities/locadora/locadora-update.component';
import { LocadoraService } from 'app/entities/locadora/locadora.service';
import { Locadora } from 'app/shared/model/locadora.model';

describe('Component Tests', () => {
    describe('Locadora Management Update Component', () => {
        let comp: LocadoraUpdateComponent;
        let fixture: ComponentFixture<LocadoraUpdateComponent>;
        let service: LocadoraService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ViajatoTestModule],
                declarations: [LocadoraUpdateComponent]
            })
                .overrideTemplate(LocadoraUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(LocadoraUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LocadoraService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Locadora(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.locadora = entity;
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
                    const entity = new Locadora();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.locadora = entity;
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
