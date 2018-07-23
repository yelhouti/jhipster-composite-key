import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CompositekeySharedModule } from 'app/shared';
import {
    EmployeeSkillCertificateComponent,
    EmployeeSkillCertificateDetailComponent,
    EmployeeSkillCertificateUpdateComponent,
    EmployeeSkillCertificateDeletePopupComponent,
    EmployeeSkillCertificateDeleteDialogComponent,
    employeeSkillCertificateRoute,
    employeeSkillCertificatePopupRoute
} from './';

const ENTITY_STATES = [...employeeSkillCertificateRoute, ...employeeSkillCertificatePopupRoute];

@NgModule({
    imports: [CompositekeySharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        EmployeeSkillCertificateComponent,
        EmployeeSkillCertificateDetailComponent,
        EmployeeSkillCertificateUpdateComponent,
        EmployeeSkillCertificateDeleteDialogComponent,
        EmployeeSkillCertificateDeletePopupComponent
    ],
    entryComponents: [
        EmployeeSkillCertificateComponent,
        EmployeeSkillCertificateUpdateComponent,
        EmployeeSkillCertificateDeleteDialogComponent,
        EmployeeSkillCertificateDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CompositekeyEmployeeSkillCertificateModule {}
