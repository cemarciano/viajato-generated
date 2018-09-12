import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPassagem } from 'app/shared/model/passagem.model';

type EntityResponseType = HttpResponse<IPassagem>;
type EntityArrayResponseType = HttpResponse<IPassagem[]>;

@Injectable({ providedIn: 'root' })
export class PassagemService {
    private resourceUrl = SERVER_API_URL + 'api/passagems';

    constructor(private http: HttpClient) {}

    create(passagem: IPassagem): Observable<EntityResponseType> {
        return this.http.post<IPassagem>(this.resourceUrl, passagem, { observe: 'response' });
    }

    update(passagem: IPassagem): Observable<EntityResponseType> {
        return this.http.put<IPassagem>(this.resourceUrl, passagem, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IPassagem>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IPassagem[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
