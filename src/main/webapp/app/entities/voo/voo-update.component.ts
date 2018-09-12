import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IVoo } from 'app/shared/model/voo.model';
import { VooService } from './voo.service';
import { ILinhaAerea } from 'app/shared/model/linha-aerea.model';
import { LinhaAereaService } from 'app/entities/linha-aerea';
import { IAeroporto } from 'app/shared/model/aeroporto.model';
import { AeroportoService } from 'app/entities/aeroporto';

@Component({
    selector: 'jhi-voo-update',
    templateUrl: './voo-update.component.html'
})
export class VooUpdateComponent implements OnInit {
    private _voo: IVoo;
    isSaving: boolean;

    linhaaereas: ILinhaAerea[];

    aeroportos: IAeroporto[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private vooService: VooService,
        private linhaAereaService: LinhaAereaService,
        private aeroportoService: AeroportoService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ voo }) => {
            this.voo = voo;
        });
        this.linhaAereaService.query().subscribe(
            (res: HttpResponse<ILinhaAerea[]>) => {
                this.linhaaereas = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.aeroportoService.query().subscribe(
            (res: HttpResponse<IAeroporto[]>) => {
                this.aeroportos = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.voo.id !== undefined) {
            this.subscribeToSaveResponse(this.vooService.update(this.voo));
        } else {
            this.subscribeToSaveResponse(this.vooService.create(this.voo));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IVoo>>) {
        result.subscribe((res: HttpResponse<IVoo>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackAeroportoById(index: number, item: IAeroporto) {
        return item.id;
    }
    get voo() {
        return this._voo;
    }

    set voo(voo: IVoo) {
        this._voo = voo;
    }
}
