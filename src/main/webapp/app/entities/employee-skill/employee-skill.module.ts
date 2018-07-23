import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CompositekeySharedModule } from 'app/shared';
import {
    EmployeeSkillComponent,
    EmployeeSkillDetailComponent,
    EmployeeSkillUpdateComponent,
    EmployeeSkillDeletePopupComponent,
    EmployeeSkillDeleteDialogComponent,
    employeeSkillRoute,
    employeeSkillPopupRoute
} from './';

const ENTITY_STATES = [...employeeSkillRoute, ...employeeSkillPopupRoute];

@NgModule({
    imports: [CompositekeySharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        EmployeeSkillComponent,
        EmployeeSkillDetailComponent,
        EmployeeSkillUpdateComponent,
        EmployeeSkillDeleteDialogComponent,
        EmployeeSkillDeletePopupComponent
    ],
    entryComponents: [
        EmployeeSkillComponent,
        EmployeeSkillUpdateComponent,
        EmployeeSkillDeleteDialogComponent,
        EmployeeSkillDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CompositekeyEmployeeSkillModule {}
