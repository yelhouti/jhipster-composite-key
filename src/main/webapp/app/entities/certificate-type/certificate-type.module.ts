import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CompositekeySharedModule } from 'app/shared';
import {
    CertificateTypeComponent,
    CertificateTypeDetailComponent,
    CertificateTypeUpdateComponent,
    CertificateTypeDeletePopupComponent,
    CertificateTypeDeleteDialogComponent,
    certificateTypeRoute,
    certificateTypePopupRoute
} from './';

const ENTITY_STATES = [...certificateTypeRoute, ...certificateTypePopupRoute];

@NgModule({
    imports: [CompositekeySharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        CertificateTypeComponent,
        CertificateTypeDetailComponent,
        CertificateTypeUpdateComponent,
        CertificateTypeDeleteDialogComponent,
        CertificateTypeDeletePopupComponent
    ],
    entryComponents: [
        CertificateTypeComponent,
        CertificateTypeUpdateComponent,
        CertificateTypeDeleteDialogComponent,
        CertificateTypeDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CompositekeyCertificateTypeModule {}
