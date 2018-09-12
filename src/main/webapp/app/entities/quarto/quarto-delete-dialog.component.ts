import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IQuarto } from 'app/shared/model/quarto.model';
import { QuartoService } from './quarto.service';

@Component({
    selector: 'jhi-quarto-delete-dialog',
    templateUrl: './quarto-delete-dialog.component.html'
})
export class QuartoDeleteDialogComponent {
    quarto: IQuarto;

    constructor(private quartoService: QuartoService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.quartoService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'quartoListModification',
                content: 'Deleted an quarto'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-quarto-delete-popup',
    template: ''
})
export class QuartoDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ quarto }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(QuartoDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.quarto = quarto;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
