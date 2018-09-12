import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ViajatoSharedModule } from 'app/shared';
import {
    AeroportoComponent,
    AeroportoDetailComponent,
    AeroportoUpdateComponent,
    AeroportoDeletePopupComponent,
    AeroportoDeleteDialogComponent,
    aeroportoRoute,
    aeroportoPopupRoute
} from './';

const ENTITY_STATES = [...aeroportoRoute, ...aeroportoPopupRoute];

@NgModule({
    imports: [ViajatoSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        AeroportoComponent,
        AeroportoDetailComponent,
        AeroportoUpdateComponent,
        AeroportoDeleteDialogComponent,
        AeroportoDeletePopupComponent
    ],
    entryComponents: [AeroportoComponent, AeroportoUpdateComponent, AeroportoDeleteDialogComponent, AeroportoDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ViajatoAeroportoModule {}
