import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IVoo } from 'app/shared/model/voo.model';
import { Principal } from 'app/core';
import { VooService } from './voo.service';

@Component({
    selector: 'jhi-voo',
    templateUrl: './voo.component.html'
})
export class VooComponent implements OnInit, OnDestroy {
    voos: IVoo[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private vooService: VooService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.vooService.query().subscribe(
            (res: HttpResponse<IVoo[]>) => {
                this.voos = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInVoos();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IVoo) {
        return item.id;
    }

    registerChangeInVoos() {
        this.eventSubscriber = this.eventManager.subscribe('vooListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
