import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IEmployeeSkillCertificate } from 'app/shared/model/employee-skill-certificate.model';
import { EmployeeSkillCertificateService } from './employee-skill-certificate.service';
import { ICertificateType } from 'app/shared/model/certificate-type.model';
import { CertificateTypeService } from 'app/entities/certificate-type';
import { IEmployeeSkill } from 'app/shared/model/employee-skill.model';
import { EmployeeSkillService } from 'app/entities/employee-skill';

@Component({
    selector: 'jhi-employee-skill-certificate-update',
    templateUrl: './employee-skill-certificate-update.component.html'
})
export class EmployeeSkillCertificateUpdateComponent implements OnInit {
    private _employeeSkillCertificate: IEmployeeSkillCertificate;
    isSaving: boolean;
    edit: boolean;
    certificateTypes: ICertificateType[];

    employeeSkills: IEmployeeSkill[];
    selectedEmployeeSkill: IEmployeeSkill;
    dateDp: any;

    constructor(
        private jhiAlertService: JhiAlertService,
        private employeeSkillCertificateService: EmployeeSkillCertificateService,
        private certificateTypeService: CertificateTypeService,
        private employeeSkillService: EmployeeSkillService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ employeeSkillCertificate }) => {
            this.employeeSkillCertificate = employeeSkillCertificate;
            this.edit =
                this.employeeSkillCertificate.employeeSkillEmployeeId !== undefined &&
                this.employeeSkillCertificate.employeeSkillName !== undefined &&
                this.employeeSkillCertificate.certificateTypeId !== undefined;
        });
        this.certificateTypeService.query().subscribe(
            (res: HttpResponse<ICertificateType[]>) => {
                this.certificateTypes = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.employeeSkillService.query().subscribe(
            (res: HttpResponse<IEmployeeSkill[]>) => {
                this.employeeSkills = res.body;
                if (
                    this.employeeSkillCertificate.employeeSkillEmployeeId !== undefined &&
                    this.employeeSkillCertificate.employeeSkillName !== undefined
                ) {
                    console.log(this.employeeSkillCertificate, this.employeeSkills);
                    this.selectedEmployeeSkill = this.employeeSkills.find(
                        employeeSkill =>
                            employeeSkill.employeeId === this.employeeSkillCertificate.employeeSkillEmployeeId &&
                            employeeSkill.name === this.employeeSkillCertificate.employeeSkillName
                    );
                }
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    onEmployeeSkillChange(event) {
        this.selectedEmployeeSkill = event;
        this.employeeSkillCertificate.employeeSkillEmployeeId = this.selectedEmployeeSkill.employeeId;
        this.employeeSkillCertificate.employeeSkillName = this.selectedEmployeeSkill.name;
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.edit) {
            this.subscribeToSaveResponse(this.employeeSkillCertificateService.update(this.employeeSkillCertificate));
        } else {
            this.subscribeToSaveResponse(this.employeeSkillCertificateService.create(this.employeeSkillCertificate));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IEmployeeSkillCertificate>>) {
        result.subscribe(
            (res: HttpResponse<IEmployeeSkillCertificate>) => this.onSaveSuccess(),
            (res: HttpErrorResponse) => this.onSaveError()
        );
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

    trackCertificateTypeById(index: number, item: ICertificateType) {
        return item.id;
    }

    trackEmployeeSkillById(index: number, item: IEmployeeSkill) {
        return item.employeeId + ',' + item.name;
    }
    get employeeSkillCertificate() {
        return this._employeeSkillCertificate;
    }

    set employeeSkillCertificate(employeeSkillCertificate: IEmployeeSkillCertificate) {
        this._employeeSkillCertificate = employeeSkillCertificate;
    }
}
