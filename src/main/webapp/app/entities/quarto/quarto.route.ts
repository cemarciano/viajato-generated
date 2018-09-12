import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Quarto } from 'app/shared/model/quarto.model';
import { QuartoService } from './quarto.service';
import { QuartoComponent } from './quarto.component';
import { QuartoDetailComponent } from './quarto-detail.component';
import { QuartoUpdateComponent } from './quarto-update.component';
import { QuartoDeletePopupComponent } from './quarto-delete-dialog.component';
import { IQuarto } from 'app/shared/model/quarto.model';

@Injectable({ providedIn: 'root' })
export class QuartoResolve implements Resolve<IQuarto> {
    constructor(private service: QuartoService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((quarto: HttpResponse<Quarto>) => quarto.body));
        }
        return of(new Quarto());
    }
}

export const quartoRoute: Routes = [
    {
        path: 'quarto',
        component: QuartoComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'viajatoApp.quarto.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'quarto/:id/view',
        component: QuartoDetailComponent,
        resolve: {
            quarto: QuartoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'viajatoApp.quarto.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'quarto/new',
        component: QuartoUpdateComponent,
        resolve: {
            quarto: QuartoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'viajatoApp.quarto.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'quarto/:id/edit',
        component: QuartoUpdateComponent,
        resolve: {
            quarto: QuartoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'viajatoApp.quarto.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const quartoPopupRoute: Routes = [
    {
        path: 'quarto/:id/delete',
        component: QuartoDeletePopupComponent,
        resolve: {
            quarto: QuartoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'viajatoApp.quarto.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
