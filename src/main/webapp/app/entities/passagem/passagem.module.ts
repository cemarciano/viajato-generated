import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ViajatoSharedModule } from 'app/shared';
import {
    PassagemComponent,
    PassagemDetailComponent,
    PassagemUpdateComponent,
    PassagemDeletePopupComponent,
    PassagemDeleteDialogComponent,
    passagemRoute,
    passagemPopupRoute
} from './';

const ENTITY_STATES = [...passagemRoute, ...passagemPopupRoute];

@NgModule({
    imports: [ViajatoSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        PassagemComponent,
        PassagemDetailComponent,
        PassagemUpdateComponent,
        PassagemDeleteDialogComponent,
        PassagemDeletePopupComponent
    ],
    entryComponents: [PassagemComponent, PassagemUpdateComponent, PassagemDeleteDialogComponent, PassagemDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ViajatoPassagemModule {}
