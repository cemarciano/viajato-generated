import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Telefone } from 'app/shared/model/telefone.model';
import { TelefoneService } from './telefone.service';
import { TelefoneComponent } from './telefone.component';
import { TelefoneDetailComponent } from './telefone-detail.component';
import { TelefoneUpdateComponent } from './telefone-update.component';
import { TelefoneDeletePopupComponent } from './telefone-delete-dialog.component';
import { ITelefone } from 'app/shared/model/telefone.model';

@Injectable({ providedIn: 'root' })
export class TelefoneResolve implements Resolve<ITelefone> {
    constructor(private service: TelefoneService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((telefone: HttpResponse<Telefone>) => telefone.body));
        }
        return of(new Telefone());
    }
}

export const telefoneRoute: Routes = [
    {
        path: 'telefone',
        component: TelefoneComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'viajatoApp.telefone.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'telefone/:id/view',
        component: TelefoneDetailComponent,
        resolve: {
            telefone: TelefoneResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'viajatoApp.telefone.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'telefone/new',
        component: TelefoneUpdateComponent,
        resolve: {
            telefone: TelefoneResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'viajatoApp.telefone.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'telefone/:id/edit',
        component: TelefoneUpdateComponent,
        resolve: {
            telefone: TelefoneResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'viajatoApp.telefone.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const telefonePopupRoute: Routes = [
    {
        path: 'telefone/:id/delete',
        component: TelefoneDeletePopupComponent,
        resolve: {
            telefone: TelefoneResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'viajatoApp.telefone.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
