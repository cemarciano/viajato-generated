import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ViajatoSharedModule } from 'app/shared';
import {
    HotelComponent,
    HotelDetailComponent,
    HotelUpdateComponent,
    HotelDeletePopupComponent,
    HotelDeleteDialogComponent,
    hotelRoute,
    hotelPopupRoute
} from './';

const ENTITY_STATES = [...hotelRoute, ...hotelPopupRoute];

@NgModule({
    imports: [ViajatoSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [HotelComponent, HotelDetailComponent, HotelUpdateComponent, HotelDeleteDialogComponent, HotelDeletePopupComponent],
    entryComponents: [HotelComponent, HotelUpdateComponent, HotelDeleteDialogComponent, HotelDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ViajatoHotelModule {}
