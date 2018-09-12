import { IQuarto } from 'app/shared/model//quarto.model';
import { ICliente } from 'app/shared/model//cliente.model';

export interface IReserva {
    id?: number;
    numPessoas?: number;
    inicio?: string;
    duracao?: number;
    valor?: number;
    quarto?: IQuarto;
    cliente?: ICliente;
}

export class Reserva implements IReserva {
    constructor(
        public id?: number,
        public numPessoas?: number,
        public inicio?: string,
        public duracao?: number,
        public valor?: number,
        public quarto?: IQuarto,
        public cliente?: ICliente
    ) {}
}
