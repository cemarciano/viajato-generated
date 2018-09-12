import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ViajatoSharedModule } from 'app/shared';
import {
    CarroComponent,
    CarroDetailComponent,
    CarroUpdateComponent,
    CarroDeletePopupComponent,
    CarroDeleteDialogComponent,
    carroRoute,
    carroPopupRoute
} from './';

const ENTITY_STATES = [...carroRoute, ...carroPopupRoute];

@NgModule({
    imports: [ViajatoSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [CarroComponent, CarroDetailComponent, CarroUpdateComponent, CarroDeleteDialogComponent, CarroDeletePopupComponent],
    entryComponents: [CarroComponent, CarroUpdateComponent, CarroDeleteDialogComponent, CarroDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ViajatoCarroModule {}
