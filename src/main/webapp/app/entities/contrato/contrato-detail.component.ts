import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IContrato } from 'app/shared/model/contrato.model';

@Component({
    selector: 'jhi-contrato-detail',
    templateUrl: './contrato-detail.component.html'
})
export class ContratoDetailComponent implements OnInit {
    contrato: IContrato;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ contrato }) => {
            this.contrato = contrato;
        });
    }

    previousState() {
        window.history.back();
    }
}
