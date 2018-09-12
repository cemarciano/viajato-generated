/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { ViajatoTestModule } from '../../../test.module';
import { LocadoraComponent } from 'app/entities/locadora/locadora.component';
import { LocadoraService } from 'app/entities/locadora/locadora.service';
import { Locadora } from 'app/shared/model/locadora.model';

describe('Component Tests', () => {
    describe('Locadora Management Component', () => {
        let comp: LocadoraComponent;
        let fixture: ComponentFixture<LocadoraComponent>;
        let service: LocadoraService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ViajatoTestModule],
                declarations: [LocadoraComponent],
                providers: []
            })
                .overrideTemplate(LocadoraComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(LocadoraComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LocadoraService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Locadora(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.locadoras[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
