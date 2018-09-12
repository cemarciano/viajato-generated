import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { ICidade } from 'app/shared/model/cidade.model';
import { CidadeService } from './cidade.service';
import { IEstado } from 'app/shared/model/estado.model';
import { EstadoService } from 'app/entities/estado';

@Component({
    selector: 'jhi-cidade-update',
    templateUrl: './cidade-update.component.html'
})
export class CidadeUpdateComponent implements OnInit {
    private _cidade: ICidade;
    isSaving: boolean;

    estados: IEstado[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private cidadeService: CidadeService,
        private estadoService: EstadoService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ cidade }) => {
            this.cidade = cidade;
        });
        this.estadoService.query().subscribe(
            (res: HttpResponse<IEstado[]>) => {
                this.estados = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.cidade.id !== undefined) {
            this.subscribeToSaveResponse(this.cidadeService.update(this.cidade));
        } else {
            this.subscribeToSaveResponse(this.cidadeService.create(this.cidade));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ICidade>>) {
        result.subscribe((res: HttpResponse<ICidade>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackEstadoById(index: number, item: IEstado) {
        return item.id;
    }
    get cidade() {
        return this._cidade;
    }

    set cidade(cidade: ICidade) {
        this._cidade = cidade;
    }
}
