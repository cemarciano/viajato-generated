import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISeguradora } from 'app/shared/model/seguradora.model';

@Component({
    selector: 'jhi-seguradora-detail',
    templateUrl: './seguradora-detail.component.html'
})
export class SeguradoraDetailComponent implements OnInit {
    seguradora: ISeguradora;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ seguradora }) => {
            this.seguradora = seguradora;
        });
    }

    previousState() {
        window.history.back();
    }
}
