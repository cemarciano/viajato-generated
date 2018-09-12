/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { ViajatoTestModule } from '../../../test.module';
import { SeguradoraComponent } from 'app/entities/seguradora/seguradora.component';
import { SeguradoraService } from 'app/entities/seguradora/seguradora.service';
import { Seguradora } from 'app/shared/model/seguradora.model';

describe('Component Tests', () => {
    describe('Seguradora Management Component', () => {
        let comp: SeguradoraComponent;
        let fixture: ComponentFixture<SeguradoraComponent>;
        let service: SeguradoraService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ViajatoTestModule],
                declarations: [SeguradoraComponent],
                providers: []
            })
                .overrideTemplate(SeguradoraComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(SeguradoraComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SeguradoraService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Seguradora(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.seguradoras[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
