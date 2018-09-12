/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { ViajatoTestModule } from '../../../test.module';
import { CarroUpdateComponent } from 'app/entities/carro/carro-update.component';
import { CarroService } from 'app/entities/carro/carro.service';
import { Carro } from 'app/shared/model/carro.model';

describe('Component Tests', () => {
    describe('Carro Management Update Component', () => {
        let comp: CarroUpdateComponent;
        let fixture: ComponentFixture<CarroUpdateComponent>;
        let service: CarroService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ViajatoTestModule],
                declarations: [CarroUpdateComponent]
            })
                .overrideTemplate(CarroUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(CarroUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CarroService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Carro(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.carro = entity;
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
                    const entity = new Carro();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.carro = entity;
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
