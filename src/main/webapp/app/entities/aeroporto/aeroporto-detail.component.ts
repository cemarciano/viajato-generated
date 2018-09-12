import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAeroporto } from 'app/shared/model/aeroporto.model';

@Component({
    selector: 'jhi-aeroporto-detail',
    templateUrl: './aeroporto-detail.component.html'
})
export class AeroportoDetailComponent implements OnInit {
    aeroporto: IAeroporto;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ aeroporto }) => {
            this.aeroporto = aeroporto;
        });
    }

    previousState() {
        window.history.back();
    }
}
