/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ViajatoTestModule } from '../../../test.module';
import { LocadoraDetailComponent } from 'app/entities/locadora/locadora-detail.component';
import { Locadora } from 'app/shared/model/locadora.model';

describe('Component Tests', () => {
    describe('Locadora Management Detail Component', () => {
        let comp: LocadoraDetailComponent;
        let fixture: ComponentFixture<LocadoraDetailComponent>;
        const route = ({ data: of({ locadora: new Locadora(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ViajatoTestModule],
                declarations: [LocadoraDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(LocadoraDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(LocadoraDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.locadora).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
