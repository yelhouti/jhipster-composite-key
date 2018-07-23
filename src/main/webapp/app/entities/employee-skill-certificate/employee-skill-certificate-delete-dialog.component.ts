import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEmployeeSkillCertificate } from 'app/shared/model/employee-skill-certificate.model';
import { EmployeeSkillCertificateService } from './employee-skill-certificate.service';

@Component({
    selector: 'jhi-employee-skill-certificate-delete-dialog',
    templateUrl: './employee-skill-certificate-delete-dialog.component.html'
})
export class EmployeeSkillCertificateDeleteDialogComponent {
    employeeSkillCertificate: IEmployeeSkillCertificate;

    constructor(
        private employeeSkillCertificateService: EmployeeSkillCertificateService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.employeeSkillCertificateService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'employeeSkillCertificateListModification',
                content: 'Deleted an employeeSkillCertificate'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-employee-skill-certificate-delete-popup',
    template: ''
})
export class EmployeeSkillCertificateDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ employeeSkillCertificate }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(EmployeeSkillCertificateDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.employeeSkillCertificate = employeeSkillCertificate;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
