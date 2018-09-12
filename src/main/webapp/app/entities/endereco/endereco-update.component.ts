import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IEndereco } from 'app/shared/model/endereco.model';
import { EnderecoService } from './endereco.service';
import { ILocadora } from 'app/shared/model/locadora.model';
import { LocadoraService } from 'app/entities/locadora';
import { IHotel } from 'app/shared/model/hotel.model';
import { HotelService } from 'app/entities/hotel';
import { ISeguradora } from 'app/shared/model/seguradora.model';
import { SeguradoraService } from 'app/entities/seguradora';
import { ICidade } from 'app/shared/model/cidade.model';
import { CidadeService } from 'app/entities/cidade';

@Component({
    selector: 'jhi-endereco-update',
    templateUrl: './endereco-update.component.html'
})
export class EnderecoUpdateComponent implements OnInit {
    private _endereco: IEndereco;
    isSaving: boolean;

    locadoras: ILocadora[];

    hotels: IHotel[];

    seguradoras: ISeguradora[];

    cidades: ICidade[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private enderecoService: EnderecoService,
        private locadoraService: LocadoraService,
        private hotelService: HotelService,
        private seguradoraService: SeguradoraService,
        private cidadeService: CidadeService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ endereco }) => {
            this.endereco = endereco;
        });
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
        this.cidadeService.query().subscribe(
            (res: HttpResponse<ICidade[]>) => {
                this.cidades = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.endereco.id !== undefined) {
            this.subscribeToSaveResponse(this.enderecoService.update(this.endereco));
        } else {
            this.subscribeToSaveResponse(this.enderecoService.create(this.endereco));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IEndereco>>) {
        result.subscribe((res: HttpResponse<IEndereco>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackLocadoraById(index: number, item: ILocadora) {
        return item.id;
    }

    trackHotelById(index: number, item: IHotel) {
        return item.id;
    }

    trackSeguradoraById(index: number, item: ISeguradora) {
        return item.id;
    }

    trackCidadeById(index: number, item: ICidade) {
        return item.id;
    }
    get endereco() {
        return this._endereco;
    }

    set endereco(endereco: IEndereco) {
        this._endereco = endereco;
    }
}
