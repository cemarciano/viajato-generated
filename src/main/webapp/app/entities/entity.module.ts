import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { ViajatoEstadoModule } from './estado/estado.module';
import { ViajatoCidadeModule } from './cidade/cidade.module';
import { ViajatoAeroportoModule } from './aeroporto/aeroporto.module';
import { ViajatoEnderecoModule } from './endereco/endereco.module';
import { ViajatoTelefoneModule } from './telefone/telefone.module';
import { ViajatoLinhaAereaModule } from './linha-aerea/linha-aerea.module';
import { ViajatoVooModule } from './voo/voo.module';
import { ViajatoPassagemModule } from './passagem/passagem.module';
import { ViajatoLocadoraModule } from './locadora/locadora.module';
import { ViajatoVeiculoModule } from './veiculo/veiculo.module';
import { ViajatoLocacaoModule } from './locacao/locacao.module';
import { ViajatoHotelModule } from './hotel/hotel.module';
import { ViajatoQuartoModule } from './quarto/quarto.module';
import { ViajatoReservaModule } from './reserva/reserva.module';
import { ViajatoClienteModule } from './cliente/cliente.module';
import { ViajatoSeguradoraModule } from './seguradora/seguradora.module';
import { ViajatoContratoModule } from './contrato/contrato.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        ViajatoEstadoModule,
        ViajatoCidadeModule,
        ViajatoAeroportoModule,
        ViajatoEnderecoModule,
        ViajatoTelefoneModule,
        ViajatoLinhaAereaModule,
        ViajatoVooModule,
        ViajatoPassagemModule,
        ViajatoLocadoraModule,
        ViajatoVeiculoModule,
        ViajatoLocacaoModule,
        ViajatoHotelModule,
        ViajatoQuartoModule,
        ViajatoReservaModule,
        ViajatoClienteModule,
        ViajatoSeguradoraModule,
        ViajatoContratoModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ViajatoEntityModule {}
