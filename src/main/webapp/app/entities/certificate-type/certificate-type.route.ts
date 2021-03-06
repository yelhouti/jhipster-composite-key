import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { CertificateType } from 'app/shared/model/certificate-type.model';
import { CertificateTypeService } from './certificate-type.service';
import { CertificateTypeComponent } from './certificate-type.component';
import { CertificateTypeDetailComponent } from './certificate-type-detail.component';
import { CertificateTypeUpdateComponent } from './certificate-type-update.component';
import { CertificateTypeDeletePopupComponent } from './certificate-type-delete-dialog.component';
import { ICertificateType } from 'app/shared/model/certificate-type.model';

@Injectable({ providedIn: 'root' })
export class CertificateTypeResolve implements Resolve<ICertificateType> {
    constructor(private service: CertificateTypeService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((certificateType: HttpResponse<CertificateType>) => certificateType.body));
        }
        return of(new CertificateType());
    }
}

export const certificateTypeRoute: Routes = [
    {
        path: 'certificate-type',
        component: CertificateTypeComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CertificateTypes'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'certificate-type/:id/view',
        component: CertificateTypeDetailComponent,
        resolve: {
            certificateType: CertificateTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CertificateTypes'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'certificate-type/new',
        component: CertificateTypeUpdateComponent,
        resolve: {
            certificateType: CertificateTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CertificateTypes'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'certificate-type/:id/edit',
        component: CertificateTypeUpdateComponent,
        resolve: {
            certificateType: CertificateTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CertificateTypes'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const certificateTypePopupRoute: Routes = [
    {
        path: 'certificate-type/:id/delete',
        component: CertificateTypeDeletePopupComponent,
        resolve: {
            certificateType: CertificateTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CertificateTypes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
