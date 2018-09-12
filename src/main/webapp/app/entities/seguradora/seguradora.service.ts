import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ISeguradora } from 'app/shared/model/seguradora.model';

type EntityResponseType = HttpResponse<ISeguradora>;
type EntityArrayResponseType = HttpResponse<ISeguradora[]>;

@Injectable({ providedIn: 'root' })
export class SeguradoraService {
    private resourceUrl = SERVER_API_URL + 'api/seguradoras';

    constructor(private http: HttpClient) {}

    create(seguradora: ISeguradora): Observable<EntityResponseType> {
        return this.http.post<ISeguradora>(this.resourceUrl, seguradora, { observe: 'response' });
    }

    update(seguradora: ISeguradora): Observable<EntityResponseType> {
        return this.http.put<ISeguradora>(this.resourceUrl, seguradora, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ISeguradora>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ISeguradora[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
