import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IAeroporto } from 'app/shared/model/aeroporto.model';
import { Principal } from 'app/core';
import { AeroportoService } from './aeroporto.service';

@Component({
    selector: 'jhi-aeroporto',
    templateUrl: './aeroporto.component.html'
})
export class AeroportoComponent implements OnInit, OnDestroy {
    aeroportos: IAeroporto[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private aeroportoService: AeroportoService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.aeroportoService.query().subscribe(
            (res: HttpResponse<IAeroporto[]>) => {
                this.aeroportos = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInAeroportos();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IAeroporto) {
        return item.id;
    }

    registerChangeInAeroportos() {
        this.eventSubscriber = this.eventManager.subscribe('aeroportoListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
