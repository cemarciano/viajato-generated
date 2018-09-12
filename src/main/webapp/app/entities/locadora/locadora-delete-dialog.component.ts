import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ILocadora } from 'app/shared/model/locadora.model';
import { LocadoraService } from './locadora.service';

@Component({
    selector: 'jhi-locadora-delete-dialog',
    templateUrl: './locadora-delete-dialog.component.html'
})
export class LocadoraDeleteDialogComponent {
    locadora: ILocadora;

    constructor(private locadoraService: LocadoraService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.locadoraService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'locadoraListModification',
                content: 'Deleted an locadora'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-locadora-delete-popup',
    template: ''
})
export class LocadoraDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ locadora }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(LocadoraDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.locadora = locadora;
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
