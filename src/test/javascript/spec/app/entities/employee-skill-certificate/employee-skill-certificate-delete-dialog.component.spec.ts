/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { CompositekeyTestModule } from '../../../test.module';
import { EmployeeSkillCertificateDeleteDialogComponent } from 'app/entities/employee-skill-certificate/employee-skill-certificate-delete-dialog.component';
import { EmployeeSkillCertificateService } from 'app/entities/employee-skill-certificate/employee-skill-certificate.service';

describe('Component Tests', () => {
    describe('EmployeeSkillCertificate Management Delete Component', () => {
        let comp: EmployeeSkillCertificateDeleteDialogComponent;
        let fixture: ComponentFixture<EmployeeSkillCertificateDeleteDialogComponent>;
        let service: EmployeeSkillCertificateService;
        let mockEventManager: any;
        let mockActiveModal: any;
        const DEFAULT_EMPLOYEE_SKILL_EMPLOYEE_ID = 'AAAAAAAAAAA';
        const DEFAULT_EMPLOYEE_SKILL_NAME = 'AAAAAAAAAAA';
        const DEFAULT_CERTIFICATE_TYPE_ID = 1;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CompositekeyTestModule],
                declarations: [EmployeeSkillCertificateDeleteDialogComponent]
            })
                .overrideTemplate(EmployeeSkillCertificateDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(EmployeeSkillCertificateDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EmployeeSkillCertificateService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it(
                'Should call delete service on confirmDelete',
                inject(
                    [],
                    fakeAsync(() => {
                        // GIVEN
                        spyOn(service, 'delete').and.returnValue(of({}));

                        // WHEN
                        comp.confirmDelete(DEFAULT_EMPLOYEE_SKILL_EMPLOYEE_ID, DEFAULT_EMPLOYEE_SKILL_NAME, DEFAULT_CERTIFICATE_TYPE_ID);
                        tick();

                        // THEN
                        expect(service.delete).toHaveBeenCalledWith(
                            DEFAULT_EMPLOYEE_SKILL_EMPLOYEE_ID,
                            DEFAULT_EMPLOYEE_SKILL_NAME,
                            DEFAULT_CERTIFICATE_TYPE_ID
                        );
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });
});
