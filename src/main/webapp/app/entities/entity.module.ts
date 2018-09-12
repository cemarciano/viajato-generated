import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { ViajatoLinhaAereaModule } from './linha-aerea/linha-aerea.module';
import { ViajatoVooModule } from './voo/voo.module';
import { ViajatoLocadoraModule } from './locadora/locadora.module';
import { ViajatoCarroModule } from './carro/carro.module';
import { ViajatoHotelModule } from './hotel/hotel.module';
import { ViajatoQuartoModule } from './quarto/quarto.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        ViajatoLinhaAereaModule,
        ViajatoVooModule,
        ViajatoLocadoraModule,
        ViajatoCarroModule,
        ViajatoHotelModule,
        ViajatoQuartoModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ViajatoEntityModule {}
