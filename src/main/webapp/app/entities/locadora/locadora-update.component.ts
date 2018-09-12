import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { ILocadora } from 'app/shared/model/locadora.model';
import { LocadoraService } from './locadora.service';
import { IEndereco } from 'app/shared/model/endereco.model';
import { EnderecoService } from 'app/entities/endereco';

@Component({
    selector: 'jhi-locadora-update',
    templateUrl: './locadora-update.component.html'
})
export class LocadoraUpdateComponent implements OnInit {
    private _locadora: ILocadora;
    isSaving: boolean;

    enderecos: IEndereco[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private locadoraService: LocadoraService,
        private enderecoService: EnderecoService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ locadora }) => {
            this.locadora = locadora;
        });
        this.enderecoService.query({ filter: 'locadora-is-null' }).subscribe(
            (res: HttpResponse<IEndereco[]>) => {
                if (!this.locadora.endereco || !this.locadora.endereco.id) {
                    this.enderecos = res.body;
                } else {
                    this.enderecoService.find(this.locadora.endereco.id).subscribe(
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

    trackEnderecoById(index: number, item: IEndereco) {
        return item.id;
    }
    get locadora() {
        return this._locadora;
    }

    set locadora(locadora: ILocadora) {
        this._locadora = locadora;
    }
}
