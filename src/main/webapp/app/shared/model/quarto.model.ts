import { IHotel } from 'app/shared/model//hotel.model';

export interface IQuarto {
    id?: number;
    nome?: string;
    capacidade?: number;
    preco?: number;
    hotel?: IHotel;
    hotel?: IHotel;
}

export class Quarto implements IQuarto {
    constructor(
        public id?: number,
        public nome?: string,
        public capacidade?: number,
        public preco?: number,
        public hotel?: IHotel,
        public hotel?: IHotel
    ) {}
}
