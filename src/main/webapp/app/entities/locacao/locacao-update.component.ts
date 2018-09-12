import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { ILocacao } from 'app/shared/model/locacao.model';
import { LocacaoService } from './locacao.service';
import { IVeiculo } from 'app/shared/model/veiculo.model';
import { VeiculoService } from 'app/entities/veiculo';
import { ICliente } from 'app/shared/model/cliente.model';
import { ClienteService } from 'app/entities/cliente';

@Component({
    selector: 'jhi-locacao-update',
    templateUrl: './locacao-update.component.html'
})
export class LocacaoUpdateComponent implements OnInit {
    private _locacao: ILocacao;
    isSaving: boolean;

    veiculos: IVeiculo[];

    clientes: ICliente[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private locacaoService: LocacaoService,
        private veiculoService: VeiculoService,
        private clienteService: ClienteService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ locacao }) => {
            this.locacao = locacao;
        });
        this.veiculoService.query().subscribe(
            (res: HttpResponse<IVeiculo[]>) => {
                this.veiculos = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.clienteService.query().subscribe(
            (res: HttpResponse<ICliente[]>) => {
                this.clientes = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.locacao.id !== undefined) {
            this.subscribeToSaveResponse(this.locacaoService.update(this.locacao));
        } else {
            this.subscribeToSaveResponse(this.locacaoService.create(this.locacao));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ILocacao>>) {
        result.subscribe((res: HttpResponse<ILocacao>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackVeiculoById(index: number, item: IVeiculo) {
        return item.id;
    }

    trackClienteById(index: number, item: ICliente) {
        return item.id;
    }
    get locacao() {
        return this._locacao;
    }

    set locacao(locacao: ILocacao) {
        this._locacao = locacao;
    }
}
