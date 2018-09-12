import { IPassagem } from 'app/shared/model//passagem.model';
import { ILocacao } from 'app/shared/model//locacao.model';
import { IReserva } from 'app/shared/model//reserva.model';
import { IContrato } from 'app/shared/model//contrato.model';

export interface ICliente {
    id?: number;
    nome?: string;
    cpf?: number;
    email?: string;
    senha?: string;
    passagems?: IPassagem[];
    locacaos?: ILocacao[];
    reservas?: IReserva[];
    contrato?: IContrato;
}

export class Cliente implements ICliente {
    constructor(
        public id?: number,
        public nome?: string,
        public cpf?: number,
        public email?: string,
        public senha?: string,
        public passagems?: IPassagem[],
        public locacaos?: ILocacao[],
        public reservas?: IReserva[],
        public contrato?: IContrato
    ) {}
}
