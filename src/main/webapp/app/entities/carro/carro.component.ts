import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ICarro } from 'app/shared/model/carro.model';
import { Principal } from 'app/core';
import { CarroService } from './carro.service';

@Component({
    selector: 'jhi-carro',
    templateUrl: './carro.component.html'
})
export class CarroComponent implements OnInit, OnDestroy {
    carros: ICarro[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private carroService: CarroService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.carroService.query().subscribe(
            (res: HttpResponse<ICarro[]>) => {
                this.carros = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInCarros();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ICarro) {
        return item.id;
    }

    registerChangeInCarros() {
        this.eventSubscriber = this.eventManager.subscribe('carroListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
