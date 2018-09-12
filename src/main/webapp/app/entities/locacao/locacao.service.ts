import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ILocacao } from 'app/shared/model/locacao.model';

type EntityResponseType = HttpResponse<ILocacao>;
type EntityArrayResponseType = HttpResponse<ILocacao[]>;

@Injectable({ providedIn: 'root' })
export class LocacaoService {
    private resourceUrl = SERVER_API_URL + 'api/locacaos';

    constructor(private http: HttpClient) {}

    create(locacao: ILocacao): Observable<EntityResponseType> {
        return this.http.post<ILocacao>(this.resourceUrl, locacao, { observe: 'response' });
    }

    update(locacao: ILocacao): Observable<EntityResponseType> {
        return this.http.put<ILocacao>(this.resourceUrl, locacao, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ILocacao>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ILocacao[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
