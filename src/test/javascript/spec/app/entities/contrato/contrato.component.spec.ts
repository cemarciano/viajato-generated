/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { ViajatoTestModule } from '../../../test.module';
import { ContratoComponent } from 'app/entities/contrato/contrato.component';
import { ContratoService } from 'app/entities/contrato/contrato.service';
import { Contrato } from 'app/shared/model/contrato.model';

describe('Component Tests', () => {
    describe('Contrato Management Component', () => {
        let comp: ContratoComponent;
        let fixture: ComponentFixture<ContratoComponent>;
        let service: ContratoService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ViajatoTestModule],
                declarations: [ContratoComponent],
                providers: []
            })
                .overrideTemplate(ContratoComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ContratoComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ContratoService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Contrato(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.contratoes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
