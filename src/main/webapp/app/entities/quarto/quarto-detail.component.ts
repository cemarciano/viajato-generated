import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IQuarto } from 'app/shared/model/quarto.model';

@Component({
    selector: 'jhi-quarto-detail',
    templateUrl: './quarto-detail.component.html'
})
export class QuartoDetailComponent implements OnInit {
    quarto: IQuarto;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ quarto }) => {
            this.quarto = quarto;
        });
    }

    previousState() {
        window.history.back();
    }
}
