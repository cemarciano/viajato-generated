import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Aeroporto } from 'app/shared/model/aeroporto.model';
import { AeroportoService } from './aeroporto.service';
import { AeroportoComponent } from './aeroporto.component';
import { AeroportoDetailComponent } from './aeroporto-detail.component';
import { AeroportoUpdateComponent } from './aeroporto-update.component';
import { AeroportoDeletePopupComponent } from './aeroporto-delete-dialog.component';
import { IAeroporto } from 'app/shared/model/aeroporto.model';

@Injectable({ providedIn: 'root' })
export class AeroportoResolve implements Resolve<IAeroporto> {
    constructor(private service: AeroportoService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((aeroporto: HttpResponse<Aeroporto>) => aeroporto.body));
        }
        return of(new Aeroporto());
    }
}

export const aeroportoRoute: Routes = [
    {
        path: 'aeroporto',
        component: AeroportoComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'viajatoApp.aeroporto.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'aeroporto/:id/view',
        component: AeroportoDetailComponent,
        resolve: {
            aeroporto: AeroportoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'viajatoApp.aeroporto.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'aeroporto/new',
        component: AeroportoUpdateComponent,
        resolve: {
            aeroporto: AeroportoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'viajatoApp.aeroporto.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'aeroporto/:id/edit',
        component: AeroportoUpdateComponent,
        resolve: {
            aeroporto: AeroportoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'viajatoApp.aeroporto.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const aeroportoPopupRoute: Routes = [
    {
        path: 'aeroporto/:id/delete',
        component: AeroportoDeletePopupComponent,
        resolve: {
            aeroporto: AeroportoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'viajatoApp.aeroporto.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
