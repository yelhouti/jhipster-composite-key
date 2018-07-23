import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ICertificateType } from 'app/shared/model/certificate-type.model';
import { Principal } from 'app/core';
import { CertificateTypeService } from './certificate-type.service';

@Component({
    selector: 'jhi-certificate-type',
    templateUrl: './certificate-type.component.html'
})
export class CertificateTypeComponent implements OnInit, OnDestroy {
    certificateTypes: ICertificateType[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private certificateTypeService: CertificateTypeService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.certificateTypeService.query().subscribe(
            (res: HttpResponse<ICertificateType[]>) => {
                this.certificateTypes = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInCertificateTypes();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ICertificateType) {
        return item.id;
    }

    registerChangeInCertificateTypes() {
        this.eventSubscriber = this.eventManager.subscribe('certificateTypeListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
