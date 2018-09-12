/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { ViajatoTestModule } from '../../../test.module';
import { CarroComponent } from 'app/entities/carro/carro.component';
import { CarroService } from 'app/entities/carro/carro.service';
import { Carro } from 'app/shared/model/carro.model';

describe('Component Tests', () => {
    describe('Carro Management Component', () => {
        let comp: CarroComponent;
        let fixture: ComponentFixture<CarroComponent>;
        let service: CarroService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ViajatoTestModule],
                declarations: [CarroComponent],
                providers: []
            })
                .overrideTemplate(CarroComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(CarroComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CarroService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Carro(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.carros[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
