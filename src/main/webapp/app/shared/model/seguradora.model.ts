import { IEndereco } from 'app/shared/model//endereco.model';
import { IContrato } from 'app/shared/model//contrato.model';
import { ITelefone } from 'app/shared/model//telefone.model';

export interface ISeguradora {
    id?: number;
    nome?: string;
    endereco?: IEndereco;
    contratoes?: IContrato[];
    telefones?: ITelefone[];
}

export class Seguradora implements ISeguradora {
    constructor(
        public id?: number,
        public nome?: string,
        public endereco?: IEndereco,
        public contratoes?: IContrato[],
        public telefones?: ITelefone[]
    ) {}
}
