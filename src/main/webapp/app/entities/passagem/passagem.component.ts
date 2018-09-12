import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IPassagem } from 'app/shared/model/passagem.model';
import { Principal } from 'app/core';
import { PassagemService } from './passagem.service';

@Component({
    selector: 'jhi-passagem',
    templateUrl: './passagem.component.html'
})
export class PassagemComponent implements OnInit, OnDestroy {
    passagems: IPassagem[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private passagemService: PassagemService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.passagemService.query().subscribe(
            (res: HttpResponse<IPassagem[]>) => {
                this.passagems = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInPassagems();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IPassagem) {
        return item.id;
    }

    registerChangeInPassagems() {
        this.eventSubscriber = this.eventManager.subscribe('passagemListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
