import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IContrato } from 'app/shared/model/contrato.model';
import { ContratoService } from './contrato.service';

@Component({
    selector: 'jhi-contrato-delete-dialog',
    templateUrl: './contrato-delete-dialog.component.html'
})
export class ContratoDeleteDialogComponent {
    contrato: IContrato;

    constructor(private contratoService: ContratoService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.contratoService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'contratoListModification',
                content: 'Deleted an contrato'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-contrato-delete-popup',
    template: ''
})
export class ContratoDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ contrato }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ContratoDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.contrato = contrato;
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
