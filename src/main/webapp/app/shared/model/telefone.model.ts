import { ILinhaAerea } from 'app/shared/model//linha-aerea.model';
import { ILocadora } from 'app/shared/model//locadora.model';
import { IHotel } from 'app/shared/model//hotel.model';

export interface ITelefone {
    id?: number;
    ddd?: number;
    numero?: number;
    linhaAerea?: ILinhaAerea;
    locadora?: ILocadora;
    hotel?: IHotel;
}

export class Telefone implements ITelefone {
    constructor(
        public id?: number,
        public ddd?: number,
        public numero?: number,
        public linhaAerea?: ILinhaAerea,
        public locadora?: ILocadora,
        public hotel?: IHotel
    ) {}
}
