import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ICidade } from 'app/shared/model/cidade.model';
import { Principal } from 'app/core';
import { CidadeService } from './cidade.service';

@Component({
    selector: 'jhi-cidade',
    templateUrl: './cidade.component.html'
})
export class CidadeComponent implements OnInit, OnDestroy {
    cidades: ICidade[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private cidadeService: CidadeService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.cidadeService.query().subscribe(
            (res: HttpResponse<ICidade[]>) => {
                this.cidades = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInCidades();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ICidade) {
        return item.id;
    }

    registerChangeInCidades() {
        this.eventSubscriber = this.eventManager.subscribe('cidadeListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
