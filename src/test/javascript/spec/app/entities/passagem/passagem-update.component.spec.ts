/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { ViajatoTestModule } from '../../../test.module';
import { PassagemUpdateComponent } from 'app/entities/passagem/passagem-update.component';
import { PassagemService } from 'app/entities/passagem/passagem.service';
import { Passagem } from 'app/shared/model/passagem.model';

describe('Component Tests', () => {
    describe('Passagem Management Update Component', () => {
        let comp: PassagemUpdateComponent;
        let fixture: ComponentFixture<PassagemUpdateComponent>;
        let service: PassagemService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ViajatoTestModule],
                declarations: [PassagemUpdateComponent]
            })
                .overrideTemplate(PassagemUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(PassagemUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PassagemService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Passagem(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.passagem = entity;
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
                    const entity = new Passagem();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.passagem = entity;
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
