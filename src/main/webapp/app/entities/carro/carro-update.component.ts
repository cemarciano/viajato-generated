import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { ICarro } from 'app/shared/model/carro.model';
import { CarroService } from './carro.service';
import { ILocadora } from 'app/shared/model/locadora.model';
import { LocadoraService } from 'app/entities/locadora';

@Component({
    selector: 'jhi-carro-update',
    templateUrl: './carro-update.component.html'
})
export class CarroUpdateComponent implements OnInit {
    private _carro: ICarro;
    isSaving: boolean;

    locadoras: ILocadora[];

    locadoras: ILocadora[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private carroService: CarroService,
        private locadoraService: LocadoraService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ carro }) => {
            this.carro = carro;
        });
        this.locadoraService.query({ filter: 'carro-is-null' }).subscribe(
            (res: HttpResponse<ILocadora[]>) => {
                if (!this.carro.locadora || !this.carro.locadora.id) {
                    this.locadoras = res.body;
                } else {
                    this.locadoraService.find(this.carro.locadora.id).subscribe(
                        (subRes: HttpResponse<ILocadora>) => {
                            this.locadoras = [subRes.body].concat(res.body);
                        },
                        (subRes: HttpErrorResponse) => this.onError(subRes.message)
                    );
                }
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
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
        if (this.carro.id !== undefined) {
            this.subscribeToSaveResponse(this.carroService.update(this.carro));
        } else {
            this.subscribeToSaveResponse(this.carroService.create(this.carro));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ICarro>>) {
        result.subscribe((res: HttpResponse<ICarro>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
    get carro() {
        return this._carro;
    }

    set carro(carro: ICarro) {
        this._carro = carro;
    }
}
