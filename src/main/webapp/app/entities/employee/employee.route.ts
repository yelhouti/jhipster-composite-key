import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Employee } from 'app/shared/model/employee.model';
import { EmployeeService } from './employee.service';
import { EmployeeComponent } from './employee.component';
import { EmployeeDetailComponent } from './employee-detail.component';
import { EmployeeUpdateComponent } from './employee-update.component';
import { EmployeeDeletePopupComponent } from './employee-delete-dialog.component';
import { IEmployee } from 'app/shared/model/employee.model';

@Injectable({ providedIn: 'root' })
export class EmployeeResolve implements Resolve<IEmployee> {
    constructor(private service: EmployeeService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((employee: HttpResponse<Employee>) => employee.body));
        }
        return of(new Employee());
    }
}

export const employeeRoute: Routes = [
    {
        path: 'employee',
        component: EmployeeComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Employees'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'employee/:id/view',
        component: EmployeeDetailComponent,
        resolve: {
            employee: EmployeeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Employees'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'employee/new',
        component: EmployeeUpdateComponent,
        resolve: {
            employee: EmployeeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Employees'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'employee/:id/edit',
        component: EmployeeUpdateComponent,
        resolve: {
            employee: EmployeeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Employees'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const employeePopupRoute: Routes = [
    {
        path: 'employee/:id/delete',
        component: EmployeeDeletePopupComponent,
        resolve: {
            employee: EmployeeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Employees'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
