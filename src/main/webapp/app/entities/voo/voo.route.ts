import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Voo } from 'app/shared/model/voo.model';
import { VooService } from './voo.service';
import { VooComponent } from './voo.component';
import { VooDetailComponent } from './voo-detail.component';
import { VooUpdateComponent } from './voo-update.component';
import { VooDeletePopupComponent } from './voo-delete-dialog.component';
import { IVoo } from 'app/shared/model/voo.model';

@Injectable({ providedIn: 'root' })
export class VooResolve implements Resolve<IVoo> {
    constructor(private service: VooService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((voo: HttpResponse<Voo>) => voo.body));
        }
        return of(new Voo());
    }
}

export const vooRoute: Routes = [
    {
        path: 'voo',
        component: VooComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'viajatoApp.voo.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'voo/:id/view',
        component: VooDetailComponent,
        resolve: {
            voo: VooResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'viajatoApp.voo.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'voo/new',
        component: VooUpdateComponent,
        resolve: {
            voo: VooResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'viajatoApp.voo.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'voo/:id/edit',
        component: VooUpdateComponent,
        resolve: {
            voo: VooResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'viajatoApp.voo.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const vooPopupRoute: Routes = [
    {
        path: 'voo/:id/delete',
        component: VooDeletePopupComponent,
        resolve: {
            voo: VooResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'viajatoApp.voo.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
