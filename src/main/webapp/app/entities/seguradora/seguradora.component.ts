import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ISeguradora } from 'app/shared/model/seguradora.model';
import { Principal } from 'app/core';
import { SeguradoraService } from './seguradora.service';

@Component({
    selector: 'jhi-seguradora',
    templateUrl: './seguradora.component.html'
})
export class SeguradoraComponent implements OnInit, OnDestroy {
    seguradoras: ISeguradora[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private seguradoraService: SeguradoraService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.seguradoraService.query().subscribe(
            (res: HttpResponse<ISeguradora[]>) => {
                this.seguradoras = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInSeguradoras();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ISeguradora) {
        return item.id;
    }

    registerChangeInSeguradoras() {
        this.eventSubscriber = this.eventManager.subscribe('seguradoraListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
