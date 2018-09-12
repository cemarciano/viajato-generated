import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ILinhaAerea } from 'app/shared/model/linha-aerea.model';

type EntityResponseType = HttpResponse<ILinhaAerea>;
type EntityArrayResponseType = HttpResponse<ILinhaAerea[]>;

@Injectable({ providedIn: 'root' })
export class LinhaAereaService {
    private resourceUrl = SERVER_API_URL + 'api/linha-aereas';

    constructor(private http: HttpClient) {}

    create(linhaAerea: ILinhaAerea): Observable<EntityResponseType> {
        return this.http.post<ILinhaAerea>(this.resourceUrl, linhaAerea, { observe: 'response' });
    }

    update(linhaAerea: ILinhaAerea): Observable<EntityResponseType> {
        return this.http.put<ILinhaAerea>(this.resourceUrl, linhaAerea, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ILinhaAerea>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ILinhaAerea[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
