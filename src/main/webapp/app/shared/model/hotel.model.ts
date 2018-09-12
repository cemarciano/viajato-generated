import { IEndereco } from 'app/shared/model//endereco.model';
import { ITelefone } from 'app/shared/model//telefone.model';
import { IQuarto } from 'app/shared/model//quarto.model';

export interface IHotel {
    id?: number;
    nome?: string;
    nota?: number;
    endereco?: IEndereco;
    telefones?: ITelefone[];
    quartos?: IQuarto[];
}

export class Hotel implements IHotel {
    constructor(
        public id?: number,
        public nome?: string,
        public nota?: number,
        public endereco?: IEndereco,
        public telefones?: ITelefone[],
        public quartos?: IQuarto[]
    ) {}
}
