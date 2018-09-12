import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ViajatoSharedModule } from 'app/shared';
import {
    LinhaAereaComponent,
    LinhaAereaDetailComponent,
    LinhaAereaUpdateComponent,
    LinhaAereaDeletePopupComponent,
    LinhaAereaDeleteDialogComponent,
    linhaAereaRoute,
    linhaAereaPopupRoute
} from './';

const ENTITY_STATES = [...linhaAereaRoute, ...linhaAereaPopupRoute];

@NgModule({
    imports: [ViajatoSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        LinhaAereaComponent,
        LinhaAereaDetailComponent,
        LinhaAereaUpdateComponent,
        LinhaAereaDeleteDialogComponent,
        LinhaAereaDeletePopupComponent
    ],
    entryComponents: [LinhaAereaComponent, LinhaAereaUpdateComponent, LinhaAereaDeleteDialogComponent, LinhaAereaDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ViajatoLinhaAereaModule {}
