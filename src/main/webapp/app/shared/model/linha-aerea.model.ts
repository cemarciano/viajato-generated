import { ITelefone } from 'app/shared/model//telefone.model';
import { IVoo } from 'app/shared/model//voo.model';

export interface ILinhaAerea {
    id?: number;
    nome?: string;
    codigo?: string;
    telefones?: ITelefone[];
    voos?: IVoo[];
}

export class LinhaAerea implements ILinhaAerea {
    constructor(public id?: number, public nome?: string, public codigo?: string, public telefones?: ITelefone[], public voos?: IVoo[]) {}
}
