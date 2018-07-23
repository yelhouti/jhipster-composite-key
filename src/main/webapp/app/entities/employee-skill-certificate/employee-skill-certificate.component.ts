import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IEmployeeSkillCertificate } from 'app/shared/model/employee-skill-certificate.model';
import { Principal } from 'app/core';
import { EmployeeSkillCertificateService } from './employee-skill-certificate.service';

@Component({
    selector: 'jhi-employee-skill-certificate',
    templateUrl: './employee-skill-certificate.component.html'
})
export class EmployeeSkillCertificateComponent implements OnInit, OnDestroy {
    employeeSkillCertificates: IEmployeeSkillCertificate[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private employeeSkillCertificateService: EmployeeSkillCertificateService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.employeeSkillCertificateService.query().subscribe(
            (res: HttpResponse<IEmployeeSkillCertificate[]>) => {
                this.employeeSkillCertificates = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInEmployeeSkillCertificates();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IEmployeeSkillCertificate) {
        return item.id;
    }

    registerChangeInEmployeeSkillCertificates() {
        this.eventSubscriber = this.eventManager.subscribe('employeeSkillCertificateListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
