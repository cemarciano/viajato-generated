import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Passagem } from 'app/shared/model/passagem.model';
import { PassagemService } from './passagem.service';
import { PassagemComponent } from './passagem.component';
import { PassagemDetailComponent } from './passagem-detail.component';
import { PassagemUpdateComponent } from './passagem-update.component';
import { PassagemDeletePopupComponent } from './passagem-delete-dialog.component';
import { IPassagem } from 'app/shared/model/passagem.model';

@Injectable({ providedIn: 'root' })
export class PassagemResolve implements Resolve<IPassagem> {
    constructor(private service: PassagemService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((passagem: HttpResponse<Passagem>) => passagem.body));
        }
        return of(new Passagem());
    }
}

export const passagemRoute: Routes = [
    {
        path: 'passagem',
        component: PassagemComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'viajatoApp.passagem.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'passagem/:id/view',
        component: PassagemDetailComponent,
        resolve: {
            passagem: PassagemResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'viajatoApp.passagem.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'passagem/new',
        component: PassagemUpdateComponent,
        resolve: {
            passagem: PassagemResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'viajatoApp.passagem.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'passagem/:id/edit',
        component: PassagemUpdateComponent,
        resolve: {
            passagem: PassagemResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'viajatoApp.passagem.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const passagemPopupRoute: Routes = [
    {
        path: 'passagem/:id/delete',
        component: PassagemDeletePopupComponent,
        resolve: {
            passagem: PassagemResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'viajatoApp.passagem.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
