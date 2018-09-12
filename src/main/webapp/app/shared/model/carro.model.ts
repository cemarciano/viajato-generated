import { ILocadora } from 'app/shared/model//locadora.model';

export interface ICarro {
    id?: number;
    fabricante?: string;
    modelo?: string;
    ano?: string;
    cor?: string;
    locadora?: ILocadora;
    locadora?: ILocadora;
}

export class Carro implements ICarro {
    constructor(
        public id?: number,
        public fabricante?: string,
        public modelo?: string,
        public ano?: string,
        public cor?: string,
        public locadora?: ILocadora,
        public locadora?: ILocadora
    ) {}
}
