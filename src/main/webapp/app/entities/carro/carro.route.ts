import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Carro } from 'app/shared/model/carro.model';
import { CarroService } from './carro.service';
import { CarroComponent } from './carro.component';
import { CarroDetailComponent } from './carro-detail.component';
import { CarroUpdateComponent } from './carro-update.component';
import { CarroDeletePopupComponent } from './carro-delete-dialog.component';
import { ICarro } from 'app/shared/model/carro.model';

@Injectable({ providedIn: 'root' })
export class CarroResolve implements Resolve<ICarro> {
    constructor(private service: CarroService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((carro: HttpResponse<Carro>) => carro.body));
        }
        return of(new Carro());
    }
}

export const carroRoute: Routes = [
    {
        path: 'carro',
        component: CarroComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'viajatoApp.carro.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'carro/:id/view',
        component: CarroDetailComponent,
        resolve: {
            carro: CarroResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'viajatoApp.carro.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'carro/new',
        component: CarroUpdateComponent,
        resolve: {
            carro: CarroResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'viajatoApp.carro.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'carro/:id/edit',
        component: CarroUpdateComponent,
        resolve: {
            carro: CarroResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'viajatoApp.carro.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const carroPopupRoute: Routes = [
    {
        path: 'carro/:id/delete',
        component: CarroDeletePopupComponent,
        resolve: {
            carro: CarroResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'viajatoApp.carro.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
