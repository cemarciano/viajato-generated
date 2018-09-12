import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { ILinhaAerea } from 'app/shared/model/linha-aerea.model';
import { LinhaAereaService } from './linha-aerea.service';
import { IVoo } from 'app/shared/model/voo.model';
import { VooService } from 'app/entities/voo';

@Component({
    selector: 'jhi-linha-aerea-update',
    templateUrl: './linha-aerea-update.component.html'
})
export class LinhaAereaUpdateComponent implements OnInit {
    private _linhaAerea: ILinhaAerea;
    isSaving: boolean;

    voos: IVoo[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private linhaAereaService: LinhaAereaService,
        private vooService: VooService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ linhaAerea }) => {
            this.linhaAerea = linhaAerea;
        });
        this.vooService.query().subscribe(
            (res: HttpResponse<IVoo[]>) => {
                this.voos = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
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

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackVooById(index: number, item: IVoo) {
        return item.id;
    }
    get linhaAerea() {
        return this._linhaAerea;
    }

    set linhaAerea(linhaAerea: ILinhaAerea) {
        this._linhaAerea = linhaAerea;
    }
}
