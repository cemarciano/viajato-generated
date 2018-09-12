import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IContrato } from 'app/shared/model/contrato.model';

type EntityResponseType = HttpResponse<IContrato>;
type EntityArrayResponseType = HttpResponse<IContrato[]>;

@Injectable({ providedIn: 'root' })
export class ContratoService {
    private resourceUrl = SERVER_API_URL + 'api/contratoes';

    constructor(private http: HttpClient) {}

    create(contrato: IContrato): Observable<EntityResponseType> {
        return this.http.post<IContrato>(this.resourceUrl, contrato, { observe: 'response' });
    }

    update(contrato: IContrato): Observable<EntityResponseType> {
        return this.http.put<IContrato>(this.resourceUrl, contrato, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IContrato>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IContrato[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
