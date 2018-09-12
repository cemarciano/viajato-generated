import { IReserva } from 'app/shared/model//reserva.model';
import { IHotel } from 'app/shared/model//hotel.model';

export interface IQuarto {
    id?: number;
    tipo?: string;
    capacidade?: number;
    diaria?: number;
    descricao?: string;
    reservas?: IReserva[];
    hotel?: IHotel;
}

export class Quarto implements IQuarto {
    constructor(
        public id?: number,
        public tipo?: string,
        public capacidade?: number,
        public diaria?: number,
        public descricao?: string,
        public reservas?: IReserva[],
        public hotel?: IHotel
    ) {}
}
