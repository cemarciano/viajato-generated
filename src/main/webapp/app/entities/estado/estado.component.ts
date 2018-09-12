import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IEstado } from 'app/shared/model/estado.model';
import { Principal } from 'app/core';
import { EstadoService } from './estado.service';

@Component({
    selector: 'jhi-estado',
    templateUrl: './estado.component.html'
})
export class EstadoComponent implements OnInit, OnDestroy {
    estados: IEstado[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private estadoService: EstadoService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.estadoService.query().subscribe(
            (res: HttpResponse<IEstado[]>) => {
                this.estados = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInEstados();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IEstado) {
        return item.id;
    }

    registerChangeInEstados() {
        this.eventSubscriber = this.eventManager.subscribe('estadoListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
