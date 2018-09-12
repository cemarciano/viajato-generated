import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IVoo } from 'app/shared/model/voo.model';

@Component({
    selector: 'jhi-voo-detail',
    templateUrl: './voo-detail.component.html'
})
export class VooDetailComponent implements OnInit {
    voo: IVoo;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ voo }) => {
            this.voo = voo;
        });
    }

    previousState() {
        window.history.back();
    }
}
