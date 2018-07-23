import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICertificateType } from 'app/shared/model/certificate-type.model';
import { CertificateTypeService } from './certificate-type.service';

@Component({
    selector: 'jhi-certificate-type-delete-dialog',
    templateUrl: './certificate-type-delete-dialog.component.html'
})
export class CertificateTypeDeleteDialogComponent {
    certificateType: ICertificateType;

    constructor(
        private certificateTypeService: CertificateTypeService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.certificateTypeService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'certificateTypeListModification',
                content: 'Deleted an certificateType'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-certificate-type-delete-popup',
    template: ''
})
export class CertificateTypeDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ certificateType }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(CertificateTypeDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.certificateType = certificateType;
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
