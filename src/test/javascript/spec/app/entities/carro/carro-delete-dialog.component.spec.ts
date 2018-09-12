/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { ViajatoTestModule } from '../../../test.module';
import { CarroDeleteDialogComponent } from 'app/entities/carro/carro-delete-dialog.component';
import { CarroService } from 'app/entities/carro/carro.service';

describe('Component Tests', () => {
    describe('Carro Management Delete Component', () => {
        let comp: CarroDeleteDialogComponent;
        let fixture: ComponentFixture<CarroDeleteDialogComponent>;
        let service: CarroService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ViajatoTestModule],
                declarations: [CarroDeleteDialogComponent]
            })
                .overrideTemplate(CarroDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(CarroDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CarroService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
