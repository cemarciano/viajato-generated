import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { LinhaAerea } from 'app/shared/model/linha-aerea.model';
import { LinhaAereaService } from './linha-aerea.service';
import { LinhaAereaComponent } from './linha-aerea.component';
import { LinhaAereaDetailComponent } from './linha-aerea-detail.component';
import { LinhaAereaUpdateComponent } from './linha-aerea-update.component';
import { LinhaAereaDeletePopupComponent } from './linha-aerea-delete-dialog.component';
import { ILinhaAerea } from 'app/shared/model/linha-aerea.model';

@Injectable({ providedIn: 'root' })
export class LinhaAereaResolve implements Resolve<ILinhaAerea> {
    constructor(private service: LinhaAereaService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((linhaAerea: HttpResponse<LinhaAerea>) => linhaAerea.body));
        }
        return of(new LinhaAerea());
    }
}

export const linhaAereaRoute: Routes = [
    {
        path: 'linha-aerea',
        component: LinhaAereaComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'viajatoApp.linhaAerea.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'linha-aerea/:id/view',
        component: LinhaAereaDetailComponent,
        resolve: {
            linhaAerea: LinhaAereaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'viajatoApp.linhaAerea.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'linha-aerea/new',
        component: LinhaAereaUpdateComponent,
        resolve: {
            linhaAerea: LinhaAereaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'viajatoApp.linhaAerea.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'linha-aerea/:id/edit',
        component: LinhaAereaUpdateComponent,
        resolve: {
            linhaAerea: LinhaAereaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'viajatoApp.linhaAerea.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const linhaAereaPopupRoute: Routes = [
    {
        path: 'linha-aerea/:id/delete',
        component: LinhaAereaDeletePopupComponent,
        resolve: {
            linhaAerea: LinhaAereaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'viajatoApp.linhaAerea.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
