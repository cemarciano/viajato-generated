/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { ViajatoTestModule } from '../../../test.module';
import { QuartoUpdateComponent } from 'app/entities/quarto/quarto-update.component';
import { QuartoService } from 'app/entities/quarto/quarto.service';
import { Quarto } from 'app/shared/model/quarto.model';

describe('Component Tests', () => {
    describe('Quarto Management Update Component', () => {
        let comp: QuartoUpdateComponent;
        let fixture: ComponentFixture<QuartoUpdateComponent>;
        let service: QuartoService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ViajatoTestModule],
                declarations: [QuartoUpdateComponent]
            })
                .overrideTemplate(QuartoUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(QuartoUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(QuartoService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Quarto(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.quarto = entity;
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
                    const entity = new Quarto();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.quarto = entity;
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
