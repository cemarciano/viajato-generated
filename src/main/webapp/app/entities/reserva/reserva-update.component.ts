import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IReserva } from 'app/shared/model/reserva.model';
import { ReservaService } from './reserva.service';
import { IQuarto } from 'app/shared/model/quarto.model';
import { QuartoService } from 'app/entities/quarto';
import { ICliente } from 'app/shared/model/cliente.model';
import { ClienteService } from 'app/entities/cliente';

@Component({
    selector: 'jhi-reserva-update',
    templateUrl: './reserva-update.component.html'
})
export class ReservaUpdateComponent implements OnInit {
    private _reserva: IReserva;
    isSaving: boolean;

    quartos: IQuarto[];

    clientes: ICliente[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private reservaService: ReservaService,
        private quartoService: QuartoService,
        private clienteService: ClienteService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ reserva }) => {
            this.reserva = reserva;
        });
        this.quartoService.query().subscribe(
            (res: HttpResponse<IQuarto[]>) => {
                this.quartos = res.body;
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
        if (this.reserva.id !== undefined) {
            this.subscribeToSaveResponse(this.reservaService.update(this.reserva));
        } else {
            this.subscribeToSaveResponse(this.reservaService.create(this.reserva));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IReserva>>) {
        result.subscribe((res: HttpResponse<IReserva>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackQuartoById(index: number, item: IQuarto) {
        return item.id;
    }

    trackClienteById(index: number, item: ICliente) {
        return item.id;
    }
    get reserva() {
        return this._reserva;
    }

    set reserva(reserva: IReserva) {
        this._reserva = reserva;
    }
}
