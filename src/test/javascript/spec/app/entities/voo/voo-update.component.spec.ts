/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { ViajatoTestModule } from '../../../test.module';
import { VooUpdateComponent } from 'app/entities/voo/voo-update.component';
import { VooService } from 'app/entities/voo/voo.service';
import { Voo } from 'app/shared/model/voo.model';

describe('Component Tests', () => {
    describe('Voo Management Update Component', () => {
        let comp: VooUpdateComponent;
        let fixture: ComponentFixture<VooUpdateComponent>;
        let service: VooService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ViajatoTestModule],
                declarations: [VooUpdateComponent]
            })
                .overrideTemplate(VooUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(VooUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(VooService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Voo(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.voo = entity;
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
                    const entity = new Voo();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.voo = entity;
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
