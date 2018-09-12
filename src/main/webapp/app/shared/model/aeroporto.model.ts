import { IVoo } from 'app/shared/model//voo.model';
import { ICidade } from 'app/shared/model//cidade.model';

export interface IAeroporto {
    id?: number;
    nome?: string;
    codigo?: string;
    vemDes?: IVoo[];
    vaiParas?: IVoo[];
    cidade?: ICidade;
}

export class Aeroporto implements IAeroporto {
    constructor(
        public id?: number,
        public nome?: string,
        public codigo?: string,
        public vemDes?: IVoo[],
        public vaiParas?: IVoo[],
        public cidade?: ICidade
    ) {}
}
