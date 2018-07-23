import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { ICertificateType } from 'app/shared/model/certificate-type.model';
import { CertificateTypeService } from './certificate-type.service';

@Component({
    selector: 'jhi-certificate-type-update',
    templateUrl: './certificate-type-update.component.html'
})
export class CertificateTypeUpdateComponent implements OnInit {
    private _certificateType: ICertificateType;
    isSaving: boolean;

    constructor(private certificateTypeService: CertificateTypeService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ certificateType }) => {
            this.certificateType = certificateType;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.certificateType.id !== undefined) {
            this.subscribeToSaveResponse(this.certificateTypeService.update(this.certificateType));
        } else {
            this.subscribeToSaveResponse(this.certificateTypeService.create(this.certificateType));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ICertificateType>>) {
        result.subscribe((res: HttpResponse<ICertificateType>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get certificateType() {
        return this._certificateType;
    }

    set certificateType(certificateType: ICertificateType) {
        this._certificateType = certificateType;
    }
}
