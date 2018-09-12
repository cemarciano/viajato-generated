import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IQuarto } from 'app/shared/model/quarto.model';
import { QuartoService } from './quarto.service';
import { IHotel } from 'app/shared/model/hotel.model';
import { HotelService } from 'app/entities/hotel';

@Component({
    selector: 'jhi-quarto-update',
    templateUrl: './quarto-update.component.html'
})
export class QuartoUpdateComponent implements OnInit {
    private _quarto: IQuarto;
    isSaving: boolean;

    hotels: IHotel[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private quartoService: QuartoService,
        private hotelService: HotelService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ quarto }) => {
            this.quarto = quarto;
        });
        this.hotelService.query().subscribe(
            (res: HttpResponse<IHotel[]>) => {
                this.hotels = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.quarto.id !== undefined) {
            this.subscribeToSaveResponse(this.quartoService.update(this.quarto));
        } else {
            this.subscribeToSaveResponse(this.quartoService.create(this.quarto));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IQuarto>>) {
        result.subscribe((res: HttpResponse<IQuarto>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackHotelById(index: number, item: IHotel) {
        return item.id;
    }
    get quarto() {
        return this._quarto;
    }

    set quarto(quarto: IQuarto) {
        this._quarto = quarto;
    }
}
