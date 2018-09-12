import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ViajatoSharedModule } from 'app/shared';
import {
    SeguradoraComponent,
    SeguradoraDetailComponent,
    SeguradoraUpdateComponent,
    SeguradoraDeletePopupComponent,
    SeguradoraDeleteDialogComponent,
    seguradoraRoute,
    seguradoraPopupRoute
} from './';

const ENTITY_STATES = [...seguradoraRoute, ...seguradoraPopupRoute];

@NgModule({
    imports: [ViajatoSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        SeguradoraComponent,
        SeguradoraDetailComponent,
        SeguradoraUpdateComponent,
        SeguradoraDeleteDialogComponent,
        SeguradoraDeletePopupComponent
    ],
    entryComponents: [SeguradoraComponent, SeguradoraUpdateComponent, SeguradoraDeleteDialogComponent, SeguradoraDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ViajatoSeguradoraModule {}
