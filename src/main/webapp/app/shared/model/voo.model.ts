import { IAeroporto } from 'app/shared/model//aeroporto.model';
import { IPassagem } from 'app/shared/model//passagem.model';
import { ILinhaAerea } from 'app/shared/model//linha-aerea.model';

export interface IVoo {
    id?: number;
    numero?: number;
    partida?: string;
    chegada?: string;
    aeroporto?: IAeroporto;
    passagems?: IPassagem[];
    linhaAerea?: ILinhaAerea;
}

export class Voo implements IVoo {
    constructor(
        public id?: number,
        public numero?: number,
        public partida?: string,
        public chegada?: string,
        public aeroporto?: IAeroporto,
        public passagems?: IPassagem[],
        public linhaAerea?: ILinhaAerea
    ) {}
}
