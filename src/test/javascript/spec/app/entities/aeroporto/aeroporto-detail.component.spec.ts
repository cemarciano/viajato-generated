/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ViajatoTestModule } from '../../../test.module';
import { AeroportoDetailComponent } from 'app/entities/aeroporto/aeroporto-detail.component';
import { Aeroporto } from 'app/shared/model/aeroporto.model';

describe('Component Tests', () => {
    describe('Aeroporto Management Detail Component', () => {
        let comp: AeroportoDetailComponent;
        let fixture: ComponentFixture<AeroportoDetailComponent>;
        const route = ({ data: of({ aeroporto: new Aeroporto(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ViajatoTestModule],
                declarations: [AeroportoDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(AeroportoDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(AeroportoDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.aeroporto).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
