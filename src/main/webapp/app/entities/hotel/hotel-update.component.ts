import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IHotel } from 'app/shared/model/hotel.model';
import { HotelService } from './hotel.service';
import { IEndereco } from 'app/shared/model/endereco.model';
import { EnderecoService } from 'app/entities/endereco';

@Component({
    selector: 'jhi-hotel-update',
    templateUrl: './hotel-update.component.html'
})
export class HotelUpdateComponent implements OnInit {
    private _hotel: IHotel;
    isSaving: boolean;

    enderecos: IEndereco[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private hotelService: HotelService,
        private enderecoService: EnderecoService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ hotel }) => {
            this.hotel = hotel;
        });
        this.enderecoService.query({ filter: 'hotel-is-null' }).subscribe(
            (res: HttpResponse<IEndereco[]>) => {
                if (!this.hotel.endereco || !this.hotel.endereco.id) {
                    this.enderecos = res.body;
                } else {
                    this.enderecoService.find(this.hotel.endereco.id).subscribe(
                        (subRes: HttpResponse<IEndereco>) => {
                            this.enderecos = [subRes.body].concat(res.body);
                        },
                        (subRes: HttpErrorResponse) => this.onError(subRes.message)
                    );
                }
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.hotel.id !== undefined) {
            this.subscribeToSaveResponse(this.hotelService.update(this.hotel));
        } else {
            this.subscribeToSaveResponse(this.hotelService.create(this.hotel));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IHotel>>) {
        result.subscribe((res: HttpResponse<IHotel>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackEnderecoById(index: number, item: IEndereco) {
        return item.id;
    }
    get hotel() {
        return this._hotel;
    }

    set hotel(hotel: IHotel) {
        this._hotel = hotel;
    }
}
