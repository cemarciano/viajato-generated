import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { ITelefone } from 'app/shared/model/telefone.model';
import { TelefoneService } from './telefone.service';
import { ILinhaAerea } from 'app/shared/model/linha-aerea.model';
import { LinhaAereaService } from 'app/entities/linha-aerea';
import { ILocadora } from 'app/shared/model/locadora.model';
import { LocadoraService } from 'app/entities/locadora';
import { IHotel } from 'app/shared/model/hotel.model';
import { HotelService } from 'app/entities/hotel';
import { ISeguradora } from 'app/shared/model/seguradora.model';
import { SeguradoraService } from 'app/entities/seguradora';

@Component({
    selector: 'jhi-telefone-update',
    templateUrl: './telefone-update.component.html'
})
export class TelefoneUpdateComponent implements OnInit {
    private _telefone: ITelefone;
    isSaving: boolean;

    linhaaereas: ILinhaAerea[];

    locadoras: ILocadora[];

    hotels: IHotel[];

    seguradoras: ISeguradora[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private telefoneService: TelefoneService,
        private linhaAereaService: LinhaAereaService,
        private locadoraService: LocadoraService,
        private hotelService: HotelService,
        private seguradoraService: SeguradoraService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ telefone }) => {
            this.telefone = telefone;
        });
        this.linhaAereaService.query().subscribe(
            (res: HttpResponse<ILinhaAerea[]>) => {
                this.linhaaereas = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.locadoraService.query().subscribe(
            (res: HttpResponse<ILocadora[]>) => {
                this.locadoras = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.hotelService.query().subscribe(
            (res: HttpResponse<IHotel[]>) => {
                this.hotels = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.seguradoraService.query().subscribe(
            (res: HttpResponse<ISeguradora[]>) => {
                this.seguradoras = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.telefone.id !== undefined) {
            this.subscribeToSaveResponse(this.telefoneService.update(this.telefone));
        } else {
            this.subscribeToSaveResponse(this.telefoneService.create(this.telefone));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ITelefone>>) {
        result.subscribe((res: HttpResponse<ITelefone>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackLinhaAereaById(index: number, item: ILinhaAerea) {
        return item.id;
    }

    trackLocadoraById(index: number, item: ILocadora) {
        return item.id;
    }

    trackHotelById(index: number, item: IHotel) {
        return item.id;
    }

    trackSeguradoraById(index: number, item: ISeguradora) {
        return item.id;
    }
    get telefone() {
        return this._telefone;
    }

    set telefone(telefone: ITelefone) {
        this._telefone = telefone;
    }
}
