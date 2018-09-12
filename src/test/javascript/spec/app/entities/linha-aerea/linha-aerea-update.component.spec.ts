/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { ViajatoTestModule } from '../../../test.module';
import { LinhaAereaUpdateComponent } from 'app/entities/linha-aerea/linha-aerea-update.component';
import { LinhaAereaService } from 'app/entities/linha-aerea/linha-aerea.service';
import { LinhaAerea } from 'app/shared/model/linha-aerea.model';

describe('Component Tests', () => {
    describe('LinhaAerea Management Update Component', () => {
        let comp: LinhaAereaUpdateComponent;
        let fixture: ComponentFixture<LinhaAereaUpdateComponent>;
        let service: LinhaAereaService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ViajatoTestModule],
                declarations: [LinhaAereaUpdateComponent]
            })
                .overrideTemplate(LinhaAereaUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(LinhaAereaUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LinhaAereaService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new LinhaAerea(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.linhaAerea = entity;
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
                    const entity = new LinhaAerea();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.linhaAerea = entity;
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
