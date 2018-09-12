import { ILocadora } from 'app/shared/model//locadora.model';
import { IHotel } from 'app/shared/model//hotel.model';
import { ICidade } from 'app/shared/model//cidade.model';

export interface IEndereco {
    id?: number;
    tipo?: string;
    logradouro?: string;
    numero?: number;
    complemento?: string;
    locadora?: ILocadora;
    hotel?: IHotel;
    cidade?: ICidade;
}

export class Endereco implements IEndereco {
    constructor(
        public id?: number,
        public tipo?: string,
        public logradouro?: string,
        public numero?: number,
        public complemento?: string,
        public locadora?: ILocadora,
        public hotel?: IHotel,
        public cidade?: ICidade
    ) {}
}
