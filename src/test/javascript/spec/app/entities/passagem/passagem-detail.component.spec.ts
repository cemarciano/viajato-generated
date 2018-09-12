/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ViajatoTestModule } from '../../../test.module';
import { PassagemDetailComponent } from 'app/entities/passagem/passagem-detail.component';
import { Passagem } from 'app/shared/model/passagem.model';

describe('Component Tests', () => {
    describe('Passagem Management Detail Component', () => {
        let comp: PassagemDetailComponent;
        let fixture: ComponentFixture<PassagemDetailComponent>;
        const route = ({ data: of({ passagem: new Passagem(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ViajatoTestModule],
                declarations: [PassagemDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(PassagemDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(PassagemDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.passagem).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
