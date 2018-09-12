import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IHotel } from 'app/shared/model/hotel.model';
import { Principal } from 'app/core';
import { HotelService } from './hotel.service';

@Component({
    selector: 'jhi-hotel',
    templateUrl: './hotel.component.html'
})
export class HotelComponent implements OnInit, OnDestroy {
    hotels: IHotel[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private hotelService: HotelService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.hotelService.query().subscribe(
            (res: HttpResponse<IHotel[]>) => {
                this.hotels = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInHotels();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IHotel) {
        return item.id;
    }

    registerChangeInHotels() {
        this.eventSubscriber = this.eventManager.subscribe('hotelListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
