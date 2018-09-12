export interface ISeguradora {
    id?: number;
    nome?: string;
}

export class Seguradora implements ISeguradora {
    constructor(public id?: number, public nome?: string) {}
}
