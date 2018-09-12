/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { ViajatoTestModule } from '../../../test.module';
import { QuartoComponent } from 'app/entities/quarto/quarto.component';
import { QuartoService } from 'app/entities/quarto/quarto.service';
import { Quarto } from 'app/shared/model/quarto.model';

describe('Component Tests', () => {
    describe('Quarto Management Component', () => {
        let comp: QuartoComponent;
        let fixture: ComponentFixture<QuartoComponent>;
        let service: QuartoService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ViajatoTestModule],
                declarations: [QuartoComponent],
                providers: []
            })
                .overrideTemplate(QuartoComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(QuartoComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(QuartoService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Quarto(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.quartos[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
