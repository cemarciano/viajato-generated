import { IVoo } from 'app/shared/model//voo.model';

export interface ILinhaAerea {
    id?: number;
    nome?: string;
    codigo?: string;
    voos?: IVoo[];
    voo?: IVoo;
}

export class LinhaAerea implements ILinhaAerea {
    constructor(public id?: number, public nome?: string, public codigo?: string, public voos?: IVoo[], public voo?: IVoo) {}
}
