import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';

import { IVoo } from 'app/shared/model/voo.model';
import { VooService } from './voo.service';
import { ILinhaAerea } from 'app/shared/model/linha-aerea.model';
import { LinhaAereaService } from 'app/entities/linha-aerea';

@Component({
    selector: 'jhi-voo-update',
    templateUrl: './voo-update.component.html'
})
export class VooUpdateComponent implements OnInit {
    private _voo: IVoo;
    isSaving: boolean;

    linhaaereas: ILinhaAerea[];

    linhaaereas: ILinhaAerea[];
    partida: string;
    chegada: string;

    constructor(
        private jhiAlertService: JhiAlertService,
        private vooService: VooService,
        private linhaAereaService: LinhaAereaService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ voo }) => {
            this.voo = voo;
        });
        this.linhaAereaService.query({ filter: 'voo-is-null' }).subscribe(
            (res: HttpResponse<ILinhaAerea[]>) => {
                if (!this.voo.linhaAerea || !this.voo.linhaAerea.id) {
                    this.linhaaereas = res.body;
                } else {
                    this.linhaAereaService.find(this.voo.linhaAerea.id).subscribe(
                        (subRes: HttpResponse<ILinhaAerea>) => {
                            this.linhaaereas = [subRes.body].concat(res.body);
                        },
                        (subRes: HttpErrorResponse) => this.onError(subRes.message)
                    );
                }
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.linhaAereaService.query().subscribe(
            (res: HttpResponse<ILinhaAerea[]>) => {
                this.linhaaereas = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.voo.partida = moment(this.partida, DATE_TIME_FORMAT);
        this.voo.chegada = moment(this.chegada, DATE_TIME_FORMAT);
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
    get voo() {
        return this._voo;
    }

    set voo(voo: IVoo) {
        this._voo = voo;
        this.partida = moment(voo.partida).format(DATE_TIME_FORMAT);
        this.chegada = moment(voo.chegada).format(DATE_TIME_FORMAT);
    }
}
