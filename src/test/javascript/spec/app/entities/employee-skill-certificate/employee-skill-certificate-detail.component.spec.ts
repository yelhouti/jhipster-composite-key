/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CompositekeyTestModule } from '../../../test.module';
import { EmployeeSkillCertificateDetailComponent } from 'app/entities/employee-skill-certificate/employee-skill-certificate-detail.component';
import { EmployeeSkillCertificate } from 'app/shared/model/employee-skill-certificate.model';

describe('Component Tests', () => {
    describe('EmployeeSkillCertificate Management Detail Component', () => {
        let comp: EmployeeSkillCertificateDetailComponent;
        let fixture: ComponentFixture<EmployeeSkillCertificateDetailComponent>;
        const DEFAULT_EMPLOYEE_SKILL_EMPLOYEE_ID = 'AAAAAAAAAAA';
        const DEFAULT_EMPLOYEE_SKILL_NAME = 'AAAAAAAAAAA';
        const DEFAULT_CERTIFICATE_TYPE_ID = 1;
        const route = ({
            data: of({
                employeeSkillCertificate: new EmployeeSkillCertificate(
                    DEFAULT_EMPLOYEE_SKILL_EMPLOYEE_ID,
                    DEFAULT_EMPLOYEE_SKILL_NAME,
                    DEFAULT_CERTIFICATE_TYPE_ID
                )
            })
        } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CompositekeyTestModule],
                declarations: [EmployeeSkillCertificateDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(EmployeeSkillCertificateDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(EmployeeSkillCertificateDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.employeeSkillCertificate).toEqual(
                    jasmine.objectContaining({
                        employeeSkillEmployeeId: DEFAULT_EMPLOYEE_SKILL_EMPLOYEE_ID,
                        employeeSkillName: DEFAULT_EMPLOYEE_SKILL_NAME,
                        certificateTypeId: DEFAULT_CERTIFICATE_TYPE_ID
                    })
                );
            });
        });
    });
});
