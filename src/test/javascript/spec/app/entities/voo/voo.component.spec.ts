/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { ViajatoTestModule } from '../../../test.module';
import { VooComponent } from 'app/entities/voo/voo.component';
import { VooService } from 'app/entities/voo/voo.service';
import { Voo } from 'app/shared/model/voo.model';

describe('Component Tests', () => {
    describe('Voo Management Component', () => {
        let comp: VooComponent;
        let fixture: ComponentFixture<VooComponent>;
        let service: VooService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ViajatoTestModule],
                declarations: [VooComponent],
                providers: []
            })
                .overrideTemplate(VooComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(VooComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(VooService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Voo(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.voos[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
