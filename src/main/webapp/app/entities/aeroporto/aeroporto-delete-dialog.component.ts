import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAeroporto } from 'app/shared/model/aeroporto.model';
import { AeroportoService } from './aeroporto.service';

@Component({
    selector: 'jhi-aeroporto-delete-dialog',
    templateUrl: './aeroporto-delete-dialog.component.html'
})
export class AeroportoDeleteDialogComponent {
    aeroporto: IAeroporto;

    constructor(private aeroportoService: AeroportoService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.aeroportoService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'aeroportoListModification',
                content: 'Deleted an aeroporto'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-aeroporto-delete-popup',
    template: ''
})
export class AeroportoDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ aeroporto }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(AeroportoDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.aeroporto = aeroporto;
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
