import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICarro } from 'app/shared/model/carro.model';

@Component({
    selector: 'jhi-carro-detail',
    templateUrl: './carro-detail.component.html'
})
export class CarroDetailComponent implements OnInit {
    carro: ICarro;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ carro }) => {
            this.carro = carro;
        });
    }

    previousState() {
        window.history.back();
    }
}
