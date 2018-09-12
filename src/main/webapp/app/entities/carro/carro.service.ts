import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ICarro } from 'app/shared/model/carro.model';

type EntityResponseType = HttpResponse<ICarro>;
type EntityArrayResponseType = HttpResponse<ICarro[]>;

@Injectable({ providedIn: 'root' })
export class CarroService {
    private resourceUrl = SERVER_API_URL + 'api/carros';

    constructor(private http: HttpClient) {}

    create(carro: ICarro): Observable<EntityResponseType> {
        return this.http.post<ICarro>(this.resourceUrl, carro, { observe: 'response' });
    }

    update(carro: ICarro): Observable<EntityResponseType> {
        return this.http.put<ICarro>(this.resourceUrl, carro, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ICarro>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ICarro[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
