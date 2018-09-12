import { Moment } from 'moment';
import { ILinhaAerea } from 'app/shared/model//linha-aerea.model';

export const enum Aeroporto {
    SDU = 'SDU',
    GIG = 'GIG',
    CGH = 'CGH',
    VCP = 'VCP',
    BSB = 'BSB',
    CNF = 'CNF',
    PLU = 'PLU'
}

export interface IVoo {
    id?: number;
    numero?: number;
    origem?: Aeroporto;
    destino?: Aeroporto;
    partida?: Moment;
    chegada?: Moment;
    linhaAerea?: ILinhaAerea;
    linhaAerea?: ILinhaAerea;
}

export class Voo implements IVoo {
    constructor(
        public id?: number,
        public numero?: number,
        public origem?: Aeroporto,
        public destino?: Aeroporto,
        public partida?: Moment,
        public chegada?: Moment,
        public linhaAerea?: ILinhaAerea,
        public linhaAerea?: ILinhaAerea
    ) {}
}
