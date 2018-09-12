/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { ViajatoTestModule } from '../../../test.module';
import { LinhaAereaComponent } from 'app/entities/linha-aerea/linha-aerea.component';
import { LinhaAereaService } from 'app/entities/linha-aerea/linha-aerea.service';
import { LinhaAerea } from 'app/shared/model/linha-aerea.model';

describe('Component Tests', () => {
    describe('LinhaAerea Management Component', () => {
        let comp: LinhaAereaComponent;
        let fixture: ComponentFixture<LinhaAereaComponent>;
        let service: LinhaAereaService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ViajatoTestModule],
                declarations: [LinhaAereaComponent],
                providers: []
            })
                .overrideTemplate(LinhaAereaComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(LinhaAereaComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LinhaAereaService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new LinhaAerea(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.linhaAereas[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
