import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IVoo } from 'app/shared/model/voo.model';

type EntityResponseType = HttpResponse<IVoo>;
type EntityArrayResponseType = HttpResponse<IVoo[]>;

@Injectable({ providedIn: 'root' })
export class VooService {
    private resourceUrl = SERVER_API_URL + 'api/voos';

    constructor(private http: HttpClient) {}

    create(voo: IVoo): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(voo);
        return this.http
            .post<IVoo>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(voo: IVoo): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(voo);
        return this.http
            .put<IVoo>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IVoo>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IVoo[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertDateFromClient(voo: IVoo): IVoo {
        const copy: IVoo = Object.assign({}, voo, {
            partida: voo.partida != null && voo.partida.isValid() ? voo.partida.toJSON() : null,
            chegada: voo.chegada != null && voo.chegada.isValid() ? voo.chegada.toJSON() : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.partida = res.body.partida != null ? moment(res.body.partida) : null;
        res.body.chegada = res.body.chegada != null ? moment(res.body.chegada) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((voo: IVoo) => {
            voo.partida = voo.partida != null ? moment(voo.partida) : null;
            voo.chegada = voo.chegada != null ? moment(voo.chegada) : null;
        });
        return res;
    }
}
