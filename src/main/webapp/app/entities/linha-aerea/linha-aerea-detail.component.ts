import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ILinhaAerea } from 'app/shared/model/linha-aerea.model';

@Component({
    selector: 'jhi-linha-aerea-detail',
    templateUrl: './linha-aerea-detail.component.html'
})
export class LinhaAereaDetailComponent implements OnInit {
    linhaAerea: ILinhaAerea;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ linhaAerea }) => {
            this.linhaAerea = linhaAerea;
        });
    }

    previousState() {
        window.history.back();
    }
}
