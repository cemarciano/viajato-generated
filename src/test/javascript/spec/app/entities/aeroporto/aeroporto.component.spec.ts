/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { ViajatoTestModule } from '../../../test.module';
import { AeroportoComponent } from 'app/entities/aeroporto/aeroporto.component';
import { AeroportoService } from 'app/entities/aeroporto/aeroporto.service';
import { Aeroporto } from 'app/shared/model/aeroporto.model';

describe('Component Tests', () => {
    describe('Aeroporto Management Component', () => {
        let comp: AeroportoComponent;
        let fixture: ComponentFixture<AeroportoComponent>;
        let service: AeroportoService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ViajatoTestModule],
                declarations: [AeroportoComponent],
                providers: []
            })
                .overrideTemplate(AeroportoComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(AeroportoComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AeroportoService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Aeroporto(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.aeroportos[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
