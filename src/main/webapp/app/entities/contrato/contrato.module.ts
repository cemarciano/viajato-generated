import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ViajatoSharedModule } from 'app/shared';
import {
    ContratoComponent,
    ContratoDetailComponent,
    ContratoUpdateComponent,
    ContratoDeletePopupComponent,
    ContratoDeleteDialogComponent,
    contratoRoute,
    contratoPopupRoute
} from './';

const ENTITY_STATES = [...contratoRoute, ...contratoPopupRoute];

@NgModule({
    imports: [ViajatoSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ContratoComponent,
        ContratoDetailComponent,
        ContratoUpdateComponent,
        ContratoDeleteDialogComponent,
        ContratoDeletePopupComponent
    ],
    entryComponents: [ContratoComponent, ContratoUpdateComponent, ContratoDeleteDialogComponent, ContratoDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ViajatoContratoModule {}
