import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IContrato } from 'app/shared/model/contrato.model';
import { Principal } from 'app/core';
import { ContratoService } from './contrato.service';

@Component({
    selector: 'jhi-contrato',
    templateUrl: './contrato.component.html'
})
export class ContratoComponent implements OnInit, OnDestroy {
    contratoes: IContrato[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private contratoService: ContratoService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.contratoService.query().subscribe(
            (res: HttpResponse<IContrato[]>) => {
                this.contratoes = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInContratoes();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IContrato) {
        return item.id;
    }

    registerChangeInContratoes() {
        this.eventSubscriber = this.eventManager.subscribe('contratoListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
