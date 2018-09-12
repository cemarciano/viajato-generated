import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ViajatoSharedModule } from 'app/shared';
import {
    VooComponent,
    VooDetailComponent,
    VooUpdateComponent,
    VooDeletePopupComponent,
    VooDeleteDialogComponent,
    vooRoute,
    vooPopupRoute
} from './';

const ENTITY_STATES = [...vooRoute, ...vooPopupRoute];

@NgModule({
    imports: [ViajatoSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [VooComponent, VooDetailComponent, VooUpdateComponent, VooDeleteDialogComponent, VooDeletePopupComponent],
    entryComponents: [VooComponent, VooUpdateComponent, VooDeleteDialogComponent, VooDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ViajatoVooModule {}
