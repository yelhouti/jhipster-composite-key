/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { CompositekeyTestModule } from '../../../test.module';
import { EmployeeSkillCertificateComponent } from 'app/entities/employee-skill-certificate/employee-skill-certificate.component';
import { EmployeeSkillCertificateService } from 'app/entities/employee-skill-certificate/employee-skill-certificate.service';
import { EmployeeSkillCertificate } from 'app/shared/model/employee-skill-certificate.model';

describe('Component Tests', () => {
    describe('EmployeeSkillCertificate Management Component', () => {
        let comp: EmployeeSkillCertificateComponent;
        let fixture: ComponentFixture<EmployeeSkillCertificateComponent>;
        let service: EmployeeSkillCertificateService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CompositekeyTestModule],
                declarations: [EmployeeSkillCertificateComponent],
                providers: []
            })
                .overrideTemplate(EmployeeSkillCertificateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(EmployeeSkillCertificateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EmployeeSkillCertificateService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new EmployeeSkillCertificate(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.employeeSkillCertificates[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
