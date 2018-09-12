import { ICliente } from 'app/shared/model//cliente.model';
import { ILocadora } from 'app/shared/model//locadora.model';
import { ISeguradora } from 'app/shared/model//seguradora.model';

export interface IContrato {
    id?: number;
    numero?: number;
    valor?: number;
    descricao?: string;
    clientes?: ICliente[];
    locadora?: ILocadora;
    seguradora?: ISeguradora;
}

export class Contrato implements IContrato {
    constructor(
        public id?: number,
        public numero?: number,
        public valor?: number,
        public descricao?: string,
        public clientes?: ICliente[],
        public locadora?: ILocadora,
        public seguradora?: ISeguradora
    ) {}
}
