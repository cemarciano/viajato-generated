/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { ViajatoTestModule } from '../../../test.module';
import { PassagemComponent } from 'app/entities/passagem/passagem.component';
import { PassagemService } from 'app/entities/passagem/passagem.service';
import { Passagem } from 'app/shared/model/passagem.model';

describe('Component Tests', () => {
    describe('Passagem Management Component', () => {
        let comp: PassagemComponent;
        let fixture: ComponentFixture<PassagemComponent>;
        let service: PassagemService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ViajatoTestModule],
                declarations: [PassagemComponent],
                providers: []
            })
                .overrideTemplate(PassagemComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(PassagemComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PassagemService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Passagem(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.passagems[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
