import { IVoo } from 'app/shared/model//voo.model';
import { ICliente } from 'app/shared/model//cliente.model';

export interface IPassagem {
    id?: number;
    classe?: string;
    valor?: number;
    nome?: string;
    cpf?: number;
    voo?: IVoo;
    cliente?: ICliente;
}

export class Passagem implements IPassagem {
    constructor(
        public id?: number,
        public classe?: string,
        public valor?: number,
        public nome?: string,
        public cpf?: number,
        public voo?: IVoo,
        public cliente?: ICliente
    ) {}
}
