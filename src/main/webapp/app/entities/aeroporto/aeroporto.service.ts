import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IAeroporto } from 'app/shared/model/aeroporto.model';

type EntityResponseType = HttpResponse<IAeroporto>;
type EntityArrayResponseType = HttpResponse<IAeroporto[]>;

@Injectable({ providedIn: 'root' })
export class AeroportoService {
    private resourceUrl = SERVER_API_URL + 'api/aeroportos';

    constructor(private http: HttpClient) {}

    create(aeroporto: IAeroporto): Observable<EntityResponseType> {
        return this.http.post<IAeroporto>(this.resourceUrl, aeroporto, { observe: 'response' });
    }

    update(aeroporto: IAeroporto): Observable<EntityResponseType> {
        return this.http.put<IAeroporto>(this.resourceUrl, aeroporto, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IAeroporto>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IAeroporto[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
