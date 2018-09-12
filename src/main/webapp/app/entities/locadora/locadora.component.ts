import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ILocadora } from 'app/shared/model/locadora.model';
import { Principal } from 'app/core';
import { LocadoraService } from './locadora.service';

@Component({
    selector: 'jhi-locadora',
    templateUrl: './locadora.component.html'
})
export class LocadoraComponent implements OnInit, OnDestroy {
    locadoras: ILocadora[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private locadoraService: LocadoraService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.locadoraService.query().subscribe(
            (res: HttpResponse<ILocadora[]>) => {
                this.locadoras = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInLocadoras();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ILocadora) {
        return item.id;
    }

    registerChangeInLocadoras() {
        this.eventSubscriber = this.eventManager.subscribe('locadoraListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
