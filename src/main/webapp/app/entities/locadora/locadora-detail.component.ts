import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ILocadora } from 'app/shared/model/locadora.model';

@Component({
    selector: 'jhi-locadora-detail',
    templateUrl: './locadora-detail.component.html'
})
export class LocadoraDetailComponent implements OnInit {
    locadora: ILocadora;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ locadora }) => {
            this.locadora = locadora;
        });
    }

    previousState() {
        window.history.back();
    }
}
