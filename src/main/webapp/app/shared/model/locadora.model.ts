import { IEndereco } from 'app/shared/model//endereco.model';
import { ITelefone } from 'app/shared/model//telefone.model';
import { IVeiculo } from 'app/shared/model//veiculo.model';
import { IContrato } from 'app/shared/model//contrato.model';

export interface ILocadora {
    id?: number;
    nome?: string;
    endereco?: IEndereco;
    telefones?: ITelefone[];
    veiculos?: IVeiculo[];
    contratoes?: IContrato[];
}

export class Locadora implements ILocadora {
    constructor(
        public id?: number,
        public nome?: string,
        public endereco?: IEndereco,
        public telefones?: ITelefone[],
        public veiculos?: IVeiculo[],
        public contratoes?: IContrato[]
    ) {}
}
