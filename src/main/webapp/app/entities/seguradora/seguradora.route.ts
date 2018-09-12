import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Seguradora } from 'app/shared/model/seguradora.model';
import { SeguradoraService } from './seguradora.service';
import { SeguradoraComponent } from './seguradora.component';
import { SeguradoraDetailComponent } from './seguradora-detail.component';
import { SeguradoraUpdateComponent } from './seguradora-update.component';
import { SeguradoraDeletePopupComponent } from './seguradora-delete-dialog.component';
import { ISeguradora } from 'app/shared/model/seguradora.model';

@Injectable({ providedIn: 'root' })
export class SeguradoraResolve implements Resolve<ISeguradora> {
    constructor(private service: SeguradoraService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((seguradora: HttpResponse<Seguradora>) => seguradora.body));
        }
        return of(new Seguradora());
    }
}

export const seguradoraRoute: Routes = [
    {
        path: 'seguradora',
        component: SeguradoraComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'viajatoApp.seguradora.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'seguradora/:id/view',
        component: SeguradoraDetailComponent,
        resolve: {
            seguradora: SeguradoraResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'viajatoApp.seguradora.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'seguradora/new',
        component: SeguradoraUpdateComponent,
        resolve: {
            seguradora: SeguradoraResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'viajatoApp.seguradora.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'seguradora/:id/edit',
        component: SeguradoraUpdateComponent,
        resolve: {
            seguradora: SeguradoraResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'viajatoApp.seguradora.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const seguradoraPopupRoute: Routes = [
    {
        path: 'seguradora/:id/delete',
        component: SeguradoraDeletePopupComponent,
        resolve: {
            seguradora: SeguradoraResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'viajatoApp.seguradora.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
