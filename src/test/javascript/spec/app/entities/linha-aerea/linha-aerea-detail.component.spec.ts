/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ViajatoTestModule } from '../../../test.module';
import { LinhaAereaDetailComponent } from 'app/entities/linha-aerea/linha-aerea-detail.component';
import { LinhaAerea } from 'app/shared/model/linha-aerea.model';

describe('Component Tests', () => {
    describe('LinhaAerea Management Detail Component', () => {
        let comp: LinhaAereaDetailComponent;
        let fixture: ComponentFixture<LinhaAereaDetailComponent>;
        const route = ({ data: of({ linhaAerea: new LinhaAerea(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ViajatoTestModule],
                declarations: [LinhaAereaDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(LinhaAereaDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(LinhaAereaDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.linhaAerea).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
