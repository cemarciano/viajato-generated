/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ViajatoTestModule } from '../../../test.module';
import { SeguradoraDetailComponent } from 'app/entities/seguradora/seguradora-detail.component';
import { Seguradora } from 'app/shared/model/seguradora.model';

describe('Component Tests', () => {
    describe('Seguradora Management Detail Component', () => {
        let comp: SeguradoraDetailComponent;
        let fixture: ComponentFixture<SeguradoraDetailComponent>;
        const route = ({ data: of({ seguradora: new Seguradora(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ViajatoTestModule],
                declarations: [SeguradoraDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(SeguradoraDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(SeguradoraDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.seguradora).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
