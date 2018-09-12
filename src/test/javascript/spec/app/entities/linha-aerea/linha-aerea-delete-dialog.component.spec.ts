/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { ViajatoTestModule } from '../../../test.module';
import { LinhaAereaDeleteDialogComponent } from 'app/entities/linha-aerea/linha-aerea-delete-dialog.component';
import { LinhaAereaService } from 'app/entities/linha-aerea/linha-aerea.service';

describe('Component Tests', () => {
    describe('LinhaAerea Management Delete Component', () => {
        let comp: LinhaAereaDeleteDialogComponent;
        let fixture: ComponentFixture<LinhaAereaDeleteDialogComponent>;
        let service: LinhaAereaService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ViajatoTestModule],
                declarations: [LinhaAereaDeleteDialogComponent]
            })
                .overrideTemplate(LinhaAereaDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(LinhaAereaDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LinhaAereaService);
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
