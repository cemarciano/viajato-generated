/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ViajatoTestModule } from '../../../test.module';
import { VooDetailComponent } from 'app/entities/voo/voo-detail.component';
import { Voo } from 'app/shared/model/voo.model';

describe('Component Tests', () => {
    describe('Voo Management Detail Component', () => {
        let comp: VooDetailComponent;
        let fixture: ComponentFixture<VooDetailComponent>;
        const route = ({ data: of({ voo: new Voo(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ViajatoTestModule],
                declarations: [VooDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(VooDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(VooDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.voo).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
