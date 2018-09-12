/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ViajatoTestModule } from '../../../test.module';
import { QuartoDetailComponent } from 'app/entities/quarto/quarto-detail.component';
import { Quarto } from 'app/shared/model/quarto.model';

describe('Component Tests', () => {
    describe('Quarto Management Detail Component', () => {
        let comp: QuartoDetailComponent;
        let fixture: ComponentFixture<QuartoDetailComponent>;
        const route = ({ data: of({ quarto: new Quarto(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ViajatoTestModule],
                declarations: [QuartoDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(QuartoDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(QuartoDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.quarto).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
