import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

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
        return this.http.post<IVoo>(this.resourceUrl, voo, { observe: 'response' });
    }

    update(voo: IVoo): Observable<EntityResponseType> {
        return this.http.put<IVoo>(this.resourceUrl, voo, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IVoo>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IVoo[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
