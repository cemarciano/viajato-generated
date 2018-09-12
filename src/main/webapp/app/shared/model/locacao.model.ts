import { IVeiculo } from 'app/shared/model//veiculo.model';
import { ICliente } from 'app/shared/model//cliente.model';

export interface ILocacao {
    id?: number;
    inicio?: string;
    duracao?: number;
    valor?: number;
    veiculo?: IVeiculo;
    cliente?: ICliente;
}

export class Locacao implements ILocacao {
    constructor(
        public id?: number,
        public inicio?: string,
        public duracao?: number,
        public valor?: number,
        public veiculo?: IVeiculo,
        public cliente?: ICliente
    ) {}
}
