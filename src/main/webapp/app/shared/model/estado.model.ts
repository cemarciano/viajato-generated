import { ICidade } from 'app/shared/model//cidade.model';

export interface IEstado {
    id?: number;
    nome?: string;
    codigo?: string;
    cidades?: ICidade[];
}

export class Estado implements IEstado {
    constructor(public id?: number, public nome?: string, public codigo?: string, public cidades?: ICidade[]) {}
}
