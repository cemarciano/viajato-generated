import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { ICliente } from 'app/shared/model/cliente.model';
import { ClienteService } from './cliente.service';
import { IContrato } from 'app/shared/model/contrato.model';
import { ContratoService } from 'app/entities/contrato';

@Component({
    selector: 'jhi-cliente-update',
    templateUrl: './cliente-update.component.html'
})
export class ClienteUpdateComponent implements OnInit {
    private _cliente: ICliente;
    isSaving: boolean;

    contratoes: IContrato[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private clienteService: ClienteService,
        private contratoService: ContratoService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ cliente }) => {
            this.cliente = cliente;
        });
        this.contratoService.query().subscribe(
            (res: HttpResponse<IContrato[]>) => {
                this.contratoes = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.cliente.id !== undefined) {
            this.subscribeToSaveResponse(this.clienteService.update(this.cliente));
        } else {
            this.subscribeToSaveResponse(this.clienteService.create(this.cliente));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ICliente>>) {
        result.subscribe((res: HttpResponse<ICliente>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackContratoById(index: number, item: IContrato) {
        return item.id;
    }
    get cliente() {
        return this._cliente;
    }

    set cliente(cliente: ICliente) {
        this._cliente = cliente;
    }
}
