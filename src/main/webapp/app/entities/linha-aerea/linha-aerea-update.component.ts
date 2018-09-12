import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { ILinhaAerea } from 'app/shared/model/linha-aerea.model';
import { LinhaAereaService } from './linha-aerea.service';

@Component({
    selector: 'jhi-linha-aerea-update',
    templateUrl: './linha-aerea-update.component.html'
})
export class LinhaAereaUpdateComponent implements OnInit {
    private _linhaAerea: ILinhaAerea;
    isSaving: boolean;

    constructor(private linhaAereaService: LinhaAereaService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ linhaAerea }) => {
            this.linhaAerea = linhaAerea;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.linhaAerea.id !== undefined) {
            this.subscribeToSaveResponse(this.linhaAereaService.update(this.linhaAerea));
        } else {
            this.subscribeToSaveResponse(this.linhaAereaService.create(this.linhaAerea));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ILinhaAerea>>) {
        result.subscribe((res: HttpResponse<ILinhaAerea>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get linhaAerea() {
        return this._linhaAerea;
    }

    set linhaAerea(linhaAerea: ILinhaAerea) {
        this._linhaAerea = linhaAerea;
    }
}
