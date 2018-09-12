import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IVeiculo } from 'app/shared/model/veiculo.model';
import { VeiculoService } from './veiculo.service';
import { ILocadora } from 'app/shared/model/locadora.model';
import { LocadoraService } from 'app/entities/locadora';

@Component({
    selector: 'jhi-veiculo-update',
    templateUrl: './veiculo-update.component.html'
})
export class VeiculoUpdateComponent implements OnInit {
    private _veiculo: IVeiculo;
    isSaving: boolean;

    locadoras: ILocadora[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private veiculoService: VeiculoService,
        private locadoraService: LocadoraService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ veiculo }) => {
            this.veiculo = veiculo;
        });
        this.locadoraService.query().subscribe(
            (res: HttpResponse<ILocadora[]>) => {
                this.locadoras = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.veiculo.id !== undefined) {
            this.subscribeToSaveResponse(this.veiculoService.update(this.veiculo));
        } else {
            this.subscribeToSaveResponse(this.veiculoService.create(this.veiculo));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IVeiculo>>) {
        result.subscribe((res: HttpResponse<IVeiculo>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
    get veiculo() {
        return this._veiculo;
    }

    set veiculo(veiculo: IVeiculo) {
        this._veiculo = veiculo;
    }
}
