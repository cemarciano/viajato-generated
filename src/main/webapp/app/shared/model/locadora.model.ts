import { ICarro } from 'app/shared/model//carro.model';

export interface ILocadora {
    id?: number;
    nome?: string;
    carros?: ICarro[];
    carro?: ICarro;
}

export class Locadora implements ILocadora {
    constructor(public id?: number, public nome?: string, public carros?: ICarro[], public carro?: ICarro) {}
}
