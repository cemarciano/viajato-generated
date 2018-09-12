/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { ViajatoTestModule } from '../../../test.module';
import { VooDeleteDialogComponent } from 'app/entities/voo/voo-delete-dialog.component';
import { VooService } from 'app/entities/voo/voo.service';

describe('Component Tests', () => {
    describe('Voo Management Delete Component', () => {
        let comp: VooDeleteDialogComponent;
        let fixture: ComponentFixture<VooDeleteDialogComponent>;
        let service: VooService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ViajatoTestModule],
                declarations: [VooDeleteDialogComponent]
            })
                .overrideTemplate(VooDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(VooDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(VooService);
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
