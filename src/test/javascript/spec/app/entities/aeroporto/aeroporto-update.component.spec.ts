/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { ViajatoTestModule } from '../../../test.module';
import { AeroportoUpdateComponent } from 'app/entities/aeroporto/aeroporto-update.component';
import { AeroportoService } from 'app/entities/aeroporto/aeroporto.service';
import { Aeroporto } from 'app/shared/model/aeroporto.model';

describe('Component Tests', () => {
    describe('Aeroporto Management Update Component', () => {
        let comp: AeroportoUpdateComponent;
        let fixture: ComponentFixture<AeroportoUpdateComponent>;
        let service: AeroportoService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ViajatoTestModule],
                declarations: [AeroportoUpdateComponent]
            })
                .overrideTemplate(AeroportoUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(AeroportoUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AeroportoService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Aeroporto(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.aeroporto = entity;
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
                    const entity = new Aeroporto();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.aeroporto = entity;
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
