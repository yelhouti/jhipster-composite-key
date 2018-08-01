import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IEmployeeSkill } from 'app/shared/model/employee-skill.model';
import { EmployeeSkillService } from './employee-skill.service';
import { IEmployee } from 'app/shared/model/employee.model';
import { EmployeeService } from 'app/entities/employee';

@Component({
    selector: 'jhi-employee-skill-update',
    templateUrl: './employee-skill-update.component.html'
})
export class EmployeeSkillUpdateComponent implements OnInit {
    private _employeeSkill: IEmployeeSkill;
    isSaving: boolean;
    edit: boolean;

    employees: IEmployee[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private employeeSkillService: EmployeeSkillService,
        private employeeService: EmployeeService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ employeeSkill }) => {
            this.employeeSkill = employeeSkill;
            this.edit = this.employeeSkill.employeeId !== undefined && this.employeeSkill.name !== undefined;
        });
        this.employeeService.query().subscribe(
            (res: HttpResponse<IEmployee[]>) => {
                this.employees = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.edit) {
            this.subscribeToSaveResponse(this.employeeSkillService.update(this.employeeSkill));
        } else {
            this.subscribeToSaveResponse(this.employeeSkillService.create(this.employeeSkill));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IEmployeeSkill>>) {
        result.subscribe((res: HttpResponse<IEmployeeSkill>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackEmployeeById(index: number, item: IEmployee) {
        return item.id;
    }
    get employeeSkill() {
        return this._employeeSkill;
    }

    set employeeSkill(employeeSkill: IEmployeeSkill) {
        this._employeeSkill = employeeSkill;
    }
}
