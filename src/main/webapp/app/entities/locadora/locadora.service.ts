import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ILocadora } from 'app/shared/model/locadora.model';

type EntityResponseType = HttpResponse<ILocadora>;
type EntityArrayResponseType = HttpResponse<ILocadora[]>;

@Injectable({ providedIn: 'root' })
export class LocadoraService {
    private resourceUrl = SERVER_API_URL + 'api/locadoras';

    constructor(private http: HttpClient) {}

    create(locadora: ILocadora): Observable<EntityResponseType> {
        return this.http.post<ILocadora>(this.resourceUrl, locadora, { observe: 'response' });
    }

    update(locadora: ILocadora): Observable<EntityResponseType> {
        return this.http.put<ILocadora>(this.resourceUrl, locadora, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ILocadora>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ILocadora[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
