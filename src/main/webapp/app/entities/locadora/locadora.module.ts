import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ViajatoSharedModule } from 'app/shared';
import {
    LocadoraComponent,
    LocadoraDetailComponent,
    LocadoraUpdateComponent,
    LocadoraDeletePopupComponent,
    LocadoraDeleteDialogComponent,
    locadoraRoute,
    locadoraPopupRoute
} from './';

const ENTITY_STATES = [...locadoraRoute, ...locadoraPopupRoute];

@NgModule({
    imports: [ViajatoSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        LocadoraComponent,
        LocadoraDetailComponent,
        LocadoraUpdateComponent,
        LocadoraDeleteDialogComponent,
        LocadoraDeletePopupComponent
    ],
    entryComponents: [LocadoraComponent, LocadoraUpdateComponent, LocadoraDeleteDialogComponent, LocadoraDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ViajatoLocadoraModule {}
