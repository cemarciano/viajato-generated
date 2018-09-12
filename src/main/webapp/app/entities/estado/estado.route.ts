import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Estado } from 'app/shared/model/estado.model';
import { EstadoService } from './estado.service';
import { EstadoComponent } from './estado.component';
import { EstadoDetailComponent } from './estado-detail.component';
import { EstadoUpdateComponent } from './estado-update.component';
import { EstadoDeletePopupComponent } from './estado-delete-dialog.component';
import { IEstado } from 'app/shared/model/estado.model';

@Injectable({ providedIn: 'root' })
export class EstadoResolve implements Resolve<IEstado> {
    constructor(private service: EstadoService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((estado: HttpResponse<Estado>) => estado.body));
        }
        return of(new Estado());
    }
}

export const estadoRoute: Routes = [
    {
        path: 'estado',
        component: EstadoComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'viajatoApp.estado.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'estado/:id/view',
        component: EstadoDetailComponent,
        resolve: {
            estado: EstadoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'viajatoApp.estado.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'estado/new',
        component: EstadoUpdateComponent,
        resolve: {
            estado: EstadoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'viajatoApp.estado.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'estado/:id/edit',
        component: EstadoUpdateComponent,
        resolve: {
            estado: EstadoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'viajatoApp.estado.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const estadoPopupRoute: Routes = [
    {
        path: 'estado/:id/delete',
        component: EstadoDeletePopupComponent,
        resolve: {
            estado: EstadoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'viajatoApp.estado.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
