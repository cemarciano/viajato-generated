import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Locadora } from 'app/shared/model/locadora.model';
import { LocadoraService } from './locadora.service';
import { LocadoraComponent } from './locadora.component';
import { LocadoraDetailComponent } from './locadora-detail.component';
import { LocadoraUpdateComponent } from './locadora-update.component';
import { LocadoraDeletePopupComponent } from './locadora-delete-dialog.component';
import { ILocadora } from 'app/shared/model/locadora.model';

@Injectable({ providedIn: 'root' })
export class LocadoraResolve implements Resolve<ILocadora> {
    constructor(private service: LocadoraService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((locadora: HttpResponse<Locadora>) => locadora.body));
        }
        return of(new Locadora());
    }
}

export const locadoraRoute: Routes = [
    {
        path: 'locadora',
        component: LocadoraComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'viajatoApp.locadora.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'locadora/:id/view',
        component: LocadoraDetailComponent,
        resolve: {
            locadora: LocadoraResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'viajatoApp.locadora.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'locadora/new',
        component: LocadoraUpdateComponent,
        resolve: {
            locadora: LocadoraResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'viajatoApp.locadora.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'locadora/:id/edit',
        component: LocadoraUpdateComponent,
        resolve: {
            locadora: LocadoraResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'viajatoApp.locadora.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const locadoraPopupRoute: Routes = [
    {
        path: 'locadora/:id/delete',
        component: LocadoraDeletePopupComponent,
        resolve: {
            locadora: LocadoraResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'viajatoApp.locadora.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
