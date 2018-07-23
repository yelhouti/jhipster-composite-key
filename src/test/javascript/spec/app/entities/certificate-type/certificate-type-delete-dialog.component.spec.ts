/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { CompositekeyTestModule } from '../../../test.module';
import { CertificateTypeDeleteDialogComponent } from 'app/entities/certificate-type/certificate-type-delete-dialog.component';
import { CertificateTypeService } from 'app/entities/certificate-type/certificate-type.service';

describe('Component Tests', () => {
    describe('CertificateType Management Delete Component', () => {
        let comp: CertificateTypeDeleteDialogComponent;
        let fixture: ComponentFixture<CertificateTypeDeleteDialogComponent>;
        let service: CertificateTypeService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CompositekeyTestModule],
                declarations: [CertificateTypeDeleteDialogComponent]
            })
                .overrideTemplate(CertificateTypeDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(CertificateTypeDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CertificateTypeService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
