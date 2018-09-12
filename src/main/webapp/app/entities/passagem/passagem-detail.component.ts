import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPassagem } from 'app/shared/model/passagem.model';

@Component({
    selector: 'jhi-passagem-detail',
    templateUrl: './passagem-detail.component.html'
})
export class PassagemDetailComponent implements OnInit {
    passagem: IPassagem;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ passagem }) => {
            this.passagem = passagem;
        });
    }

    previousState() {
        window.history.back();
    }
}
