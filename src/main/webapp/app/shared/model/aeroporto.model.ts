import { IVoo } from 'app/shared/model//voo.model';
import { ICidade } from 'app/shared/model//cidade.model';

export interface IAeroporto {
    id?: number;
    nome?: string;
    codigo?: string;
    origems?: IVoo[];
    destinos?: IVoo[];
    cidade?: ICidade;
}

export class Aeroporto implements IAeroporto {
    constructor(
        public id?: number,
        public nome?: string,
        public codigo?: string,
        public origems?: IVoo[],
        public destinos?: IVoo[],
        public cidade?: ICidade
    ) {}
}
