/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { ViajatoTestModule } from '../../../test.module';
import { LocadoraDeleteDialogComponent } from 'app/entities/locadora/locadora-delete-dialog.component';
import { LocadoraService } from 'app/entities/locadora/locadora.service';

describe('Component Tests', () => {
    describe('Locadora Management Delete Component', () => {
        let comp: LocadoraDeleteDialogComponent;
        let fixture: ComponentFixture<LocadoraDeleteDialogComponent>;
        let service: LocadoraService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ViajatoTestModule],
                declarations: [LocadoraDeleteDialogComponent]
            })
                .overrideTemplate(LocadoraDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(LocadoraDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LocadoraService);
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
