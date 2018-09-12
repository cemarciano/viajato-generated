/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { ViajatoTestModule } from '../../../test.module';
import { QuartoDeleteDialogComponent } from 'app/entities/quarto/quarto-delete-dialog.component';
import { QuartoService } from 'app/entities/quarto/quarto.service';

describe('Component Tests', () => {
    describe('Quarto Management Delete Component', () => {
        let comp: QuartoDeleteDialogComponent;
        let fixture: ComponentFixture<QuartoDeleteDialogComponent>;
        let service: QuartoService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ViajatoTestModule],
                declarations: [QuartoDeleteDialogComponent]
            })
                .overrideTemplate(QuartoDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(QuartoDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(QuartoService);
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
