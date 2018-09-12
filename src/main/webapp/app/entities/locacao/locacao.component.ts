import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ILocacao } from 'app/shared/model/locacao.model';
import { Principal } from 'app/core';
import { LocacaoService } from './locacao.service';

@Component({
    selector: 'jhi-locacao',
    templateUrl: './locacao.component.html'
})
export class LocacaoComponent implements OnInit, OnDestroy {
    locacaos: ILocacao[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private locacaoService: LocacaoService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.locacaoService.query().subscribe(
            (res: HttpResponse<ILocacao[]>) => {
                this.locacaos = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInLocacaos();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ILocacao) {
        return item.id;
    }

    registerChangeInLocacaos() {
        this.eventSubscriber = this.eventManager.subscribe('locacaoListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
