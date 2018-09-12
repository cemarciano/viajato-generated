import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { ISeguradora } from 'app/shared/model/seguradora.model';
import { SeguradoraService } from './seguradora.service';

@Component({
    selector: 'jhi-seguradora-update',
    templateUrl: './seguradora-update.component.html'
})
export class SeguradoraUpdateComponent implements OnInit {
    private _seguradora: ISeguradora;
    isSaving: boolean;

    constructor(private seguradoraService: SeguradoraService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ seguradora }) => {
            this.seguradora = seguradora;
        });
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
    get seguradora() {
        return this._seguradora;
    }

    set seguradora(seguradora: ISeguradora) {
        this._seguradora = seguradora;
    }
}
