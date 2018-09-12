import { IPassagem } from 'app/shared/model//passagem.model';
import { ILinhaAerea } from 'app/shared/model//linha-aerea.model';
import { IAeroporto } from 'app/shared/model//aeroporto.model';

export interface IVoo {
    id?: number;
    numero?: number;
    partida?: string;
    chegada?: string;
    passagems?: IPassagem[];
    linhaAerea?: ILinhaAerea;
    origem?: IAeroporto;
    destino?: IAeroporto;
}

export class Voo implements IVoo {
    constructor(
        public id?: number,
        public numero?: number,
        public partida?: string,
        public chegada?: string,
        public passagems?: IPassagem[],
        public linhaAerea?: ILinhaAerea,
        public origem?: IAeroporto,
        public destino?: IAeroporto
    ) {}
}
