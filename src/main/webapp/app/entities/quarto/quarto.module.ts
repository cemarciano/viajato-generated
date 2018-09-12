import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ViajatoSharedModule } from 'app/shared';
import {
    QuartoComponent,
    QuartoDetailComponent,
    QuartoUpdateComponent,
    QuartoDeletePopupComponent,
    QuartoDeleteDialogComponent,
    quartoRoute,
    quartoPopupRoute
} from './';

const ENTITY_STATES = [...quartoRoute, ...quartoPopupRoute];

@NgModule({
    imports: [ViajatoSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [QuartoComponent, QuartoDetailComponent, QuartoUpdateComponent, QuartoDeleteDialogComponent, QuartoDeletePopupComponent],
    entryComponents: [QuartoComponent, QuartoUpdateComponent, QuartoDeleteDialogComponent, QuartoDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ViajatoQuartoModule {}
