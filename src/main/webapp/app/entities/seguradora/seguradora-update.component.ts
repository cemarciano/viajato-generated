import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { ISeguradora } from 'app/shared/model/seguradora.model';
import { SeguradoraService } from './seguradora.service';
import { IEndereco } from 'app/shared/model/endereco.model';
import { EnderecoService } from 'app/entities/endereco';

@Component({
    selector: 'jhi-seguradora-update',
    templateUrl: './seguradora-update.component.html'
})
export class SeguradoraUpdateComponent implements OnInit {
    private _seguradora: ISeguradora;
    isSaving: boolean;

    enderecos: IEndereco[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private seguradoraService: SeguradoraService,
        private enderecoService: EnderecoService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ seguradora }) => {
            this.seguradora = seguradora;
        });
        this.enderecoService.query({ filter: 'seguradora-is-null' }).subscribe(
            (res: HttpResponse<IEndereco[]>) => {
                if (!this.seguradora.endereco || !this.seguradora.endereco.id) {
                    this.enderecos = res.body;
                } else {
                    this.enderecoService.find(this.seguradora.endereco.id).subscribe(
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
        if (this.seguradora.id !== undefined) {
            this.subscribeToSaveResponse(this.seguradoraService.update(this.seguradora));
        } else {
            this.subscribeToSaveResponse(this.seguradoraService.create(this.seguradora));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ISeguradora>>) {
        result.subscribe((res: HttpResponse<ISeguradora>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
    get seguradora() {
        return this._seguradora;
    }

    set seguradora(seguradora: ISeguradora) {
        this._seguradora = seguradora;
    }
}
