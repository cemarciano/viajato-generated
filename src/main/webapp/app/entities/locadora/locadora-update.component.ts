import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { ILocadora } from 'app/shared/model/locadora.model';
import { LocadoraService } from './locadora.service';
import { ICarro } from 'app/shared/model/carro.model';
import { CarroService } from 'app/entities/carro';

@Component({
    selector: 'jhi-locadora-update',
    templateUrl: './locadora-update.component.html'
})
export class LocadoraUpdateComponent implements OnInit {
    private _locadora: ILocadora;
    isSaving: boolean;

    carros: ICarro[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private locadoraService: LocadoraService,
        private carroService: CarroService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ locadora }) => {
            this.locadora = locadora;
        });
        this.carroService.query().subscribe(
            (res: HttpResponse<ICarro[]>) => {
                this.carros = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.locadora.id !== undefined) {
            this.subscribeToSaveResponse(this.locadoraService.update(this.locadora));
        } else {
            this.subscribeToSaveResponse(this.locadoraService.create(this.locadora));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ILocadora>>) {
        result.subscribe((res: HttpResponse<ILocadora>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackCarroById(index: number, item: ICarro) {
        return item.id;
    }
    get locadora() {
        return this._locadora;
    }

    set locadora(locadora: ILocadora) {
        this._locadora = locadora;
    }
}
