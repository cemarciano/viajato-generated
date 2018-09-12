import { IAeroporto } from 'app/shared/model//aeroporto.model';
import { IEndereco } from 'app/shared/model//endereco.model';
import { IEstado } from 'app/shared/model//estado.model';

export interface ICidade {
    id?: number;
    nome?: string;
    aeroportos?: IAeroporto[];
    enderecos?: IEndereco[];
    estado?: IEstado;
}

export class Cidade implements ICidade {
    constructor(
        public id?: number,
        public nome?: string,
        public aeroportos?: IAeroporto[],
        public enderecos?: IEndereco[],
        public estado?: IEstado
    ) {}
}
