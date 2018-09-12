import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IAeroporto } from 'app/shared/model/aeroporto.model';
import { AeroportoService } from './aeroporto.service';
import { ICidade } from 'app/shared/model/cidade.model';
import { CidadeService } from 'app/entities/cidade';

@Component({
    selector: 'jhi-aeroporto-update',
    templateUrl: './aeroporto-update.component.html'
})
export class AeroportoUpdateComponent implements OnInit {
    private _aeroporto: IAeroporto;
    isSaving: boolean;

    cidades: ICidade[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private aeroportoService: AeroportoService,
        private cidadeService: CidadeService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ aeroporto }) => {
            this.aeroporto = aeroporto;
        });
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
        if (this.aeroporto.id !== undefined) {
            this.subscribeToSaveResponse(this.aeroportoService.update(this.aeroporto));
        } else {
            this.subscribeToSaveResponse(this.aeroportoService.create(this.aeroporto));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IAeroporto>>) {
        result.subscribe((res: HttpResponse<IAeroporto>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackCidadeById(index: number, item: ICidade) {
        return item.id;
    }
    get aeroporto() {
        return this._aeroporto;
    }

    set aeroporto(aeroporto: IAeroporto) {
        this._aeroporto = aeroporto;
    }
}
