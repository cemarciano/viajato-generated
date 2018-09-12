import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Contrato } from 'app/shared/model/contrato.model';
import { ContratoService } from './contrato.service';
import { ContratoComponent } from './contrato.component';
import { ContratoDetailComponent } from './contrato-detail.component';
import { ContratoUpdateComponent } from './contrato-update.component';
import { ContratoDeletePopupComponent } from './contrato-delete-dialog.component';
import { IContrato } from 'app/shared/model/contrato.model';

@Injectable({ providedIn: 'root' })
export class ContratoResolve implements Resolve<IContrato> {
    constructor(private service: ContratoService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((contrato: HttpResponse<Contrato>) => contrato.body));
        }
        return of(new Contrato());
    }
}

export const contratoRoute: Routes = [
    {
        path: 'contrato',
        component: ContratoComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'viajatoApp.contrato.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'contrato/:id/view',
        component: ContratoDetailComponent,
        resolve: {
            contrato: ContratoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'viajatoApp.contrato.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'contrato/new',
        component: ContratoUpdateComponent,
        resolve: {
            contrato: ContratoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'viajatoApp.contrato.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'contrato/:id/edit',
        component: ContratoUpdateComponent,
        resolve: {
            contrato: ContratoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'viajatoApp.contrato.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const contratoPopupRoute: Routes = [
    {
        path: 'contrato/:id/delete',
        component: ContratoDeletePopupComponent,
        resolve: {
            contrato: ContratoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'viajatoApp.contrato.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
