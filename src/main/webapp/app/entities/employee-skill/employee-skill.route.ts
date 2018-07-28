import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { EmployeeSkill } from 'app/shared/model/employee-skill.model';
import { EmployeeSkillService } from './employee-skill.service';
import { EmployeeSkillComponent } from './employee-skill.component';
import { EmployeeSkillDetailComponent } from './employee-skill-detail.component';
import { EmployeeSkillUpdateComponent } from './employee-skill-update.component';
import { EmployeeSkillDeletePopupComponent } from './employee-skill-delete-dialog.component';
import { IEmployeeSkill } from 'app/shared/model/employee-skill.model';

@Injectable({ providedIn: 'root' })
export class EmployeeSkillResolve implements Resolve<IEmployeeSkill> {
    constructor(private service: EmployeeSkillService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const employeeId = route.params['employeeId'] ? route.params['employeeId'] : null;
        const name = route.params['name'] ? route.params['name'] : null;
        if (employeeId && name) {
            return this.service.find(employeeId, name).pipe(map((employeeSkill: HttpResponse<EmployeeSkill>) => employeeSkill.body));
        }
        return of(new EmployeeSkill());
    }
}

export const employeeSkillRoute: Routes = [
    {
        path: 'employee-skill',
        component: EmployeeSkillComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'EmployeeSkills'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'employee-skill/:employeeId/:name/view',
        component: EmployeeSkillDetailComponent,
        resolve: {
            employeeSkill: EmployeeSkillResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'EmployeeSkills'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'employee-skill/new',
        component: EmployeeSkillUpdateComponent,
        resolve: {
            employeeSkill: EmployeeSkillResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'EmployeeSkills'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'employee-skill/:employeeId/:name/edit',
        component: EmployeeSkillUpdateComponent,
        resolve: {
            employeeSkill: EmployeeSkillResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'EmployeeSkills'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const employeeSkillPopupRoute: Routes = [
    {
        path: 'employee-skill/:employeeId/:name/delete',
        component: EmployeeSkillDeletePopupComponent,
        resolve: {
            employeeSkill: EmployeeSkillResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'EmployeeSkills'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
