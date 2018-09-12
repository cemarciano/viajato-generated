/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ViajatoTestModule } from '../../../test.module';
import { CarroDetailComponent } from 'app/entities/carro/carro-detail.component';
import { Carro } from 'app/shared/model/carro.model';

describe('Component Tests', () => {
    describe('Carro Management Detail Component', () => {
        let comp: CarroDetailComponent;
        let fixture: ComponentFixture<CarroDetailComponent>;
        const route = ({ data: of({ carro: new Carro(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ViajatoTestModule],
                declarations: [CarroDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(CarroDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(CarroDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.carro).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
