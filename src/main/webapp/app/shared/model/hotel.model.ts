import { IQuarto } from 'app/shared/model//quarto.model';

export const enum Cidade {
    RIODEJANEIRO = 'RIODEJANEIRO',
    SAOPAULO = 'SAOPAULO',
    CAMPINAS = 'CAMPINAS',
    BRASILIA = 'BRASILIA',
    BELOHORIZONTE = 'BELOHORIZONTE'
}

export interface IHotel {
    id?: number;
    cidade?: Cidade;
    endereco?: string;
    telefone?: string;
    nota?: string;
    quartos?: IQuarto[];
    quarto?: IQuarto;
}

export class Hotel implements IHotel {
    constructor(
        public id?: number,
        public cidade?: Cidade,
        public endereco?: string,
        public telefone?: string,
        public nota?: string,
        public quartos?: IQuarto[],
        public quarto?: IQuarto
    ) {}
}
