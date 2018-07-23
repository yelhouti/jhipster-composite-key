import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IEmployeeSkill } from 'app/shared/model/employee-skill.model';
import { Principal } from 'app/core';
import { EmployeeSkillService } from './employee-skill.service';

@Component({
    selector: 'jhi-employee-skill',
    templateUrl: './employee-skill.component.html'
})
export class EmployeeSkillComponent implements OnInit, OnDestroy {
    employeeSkills: IEmployeeSkill[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private employeeSkillService: EmployeeSkillService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.employeeSkillService.query().subscribe(
            (res: HttpResponse<IEmployeeSkill[]>) => {
                this.employeeSkills = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInEmployeeSkills();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IEmployeeSkill) {
        return item.id;
    }

    registerChangeInEmployeeSkills() {
        this.eventSubscriber = this.eventManager.subscribe('employeeSkillListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
