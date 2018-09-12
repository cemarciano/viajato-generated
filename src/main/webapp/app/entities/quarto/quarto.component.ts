import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IQuarto } from 'app/shared/model/quarto.model';
import { Principal } from 'app/core';
import { QuartoService } from './quarto.service';

@Component({
    selector: 'jhi-quarto',
    templateUrl: './quarto.component.html'
})
export class QuartoComponent implements OnInit, OnDestroy {
    quartos: IQuarto[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private quartoService: QuartoService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.quartoService.query().subscribe(
            (res: HttpResponse<IQuarto[]>) => {
                this.quartos = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInQuartos();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IQuarto) {
        return item.id;
    }

    registerChangeInQuartos() {
        this.eventSubscriber = this.eventManager.subscribe('quartoListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
