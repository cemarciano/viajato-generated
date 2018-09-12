import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISeguradora } from 'app/shared/model/seguradora.model';
import { SeguradoraService } from './seguradora.service';

@Component({
    selector: 'jhi-seguradora-delete-dialog',
    templateUrl: './seguradora-delete-dialog.component.html'
})
export class SeguradoraDeleteDialogComponent {
    seguradora: ISeguradora;

    constructor(private seguradoraService: SeguradoraService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.seguradoraService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'seguradoraListModification',
                content: 'Deleted an seguradora'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-seguradora-delete-popup',
    template: ''
})
export class SeguradoraDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ seguradora }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(SeguradoraDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.seguradora = seguradora;
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
