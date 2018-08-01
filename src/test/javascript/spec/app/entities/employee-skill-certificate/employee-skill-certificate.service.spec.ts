/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { EmployeeSkillCertificateService } from 'app/entities/employee-skill-certificate/employee-skill-certificate.service';
import { EmployeeSkillCertificate } from 'app/shared/model/employee-skill-certificate.model';
import { SERVER_API_URL } from 'app/app.constants';

describe('Service Tests', () => {
    describe('EmployeeSkillCertificate Service', () => {
        let injector: TestBed;
        let service: EmployeeSkillCertificateService;
        let httpMock: HttpTestingController;
        const DEFAULT_EMPLOYEE_SKILL_EMPLOYEE_ID = 'AAAAAAAAAAA';
        const DEFAULT_EMPLOYEE_SKILL_NAME = 'AAAAAAAAAAA';
        const DEFAULT_CERTIFICATE_TYPE_ID = 1;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(EmployeeSkillCertificateService);
            httpMock = injector.get(HttpTestingController);
        });

        describe('Service methods', () => {
            it('should call correct URL', () => {
                service
                    .find(DEFAULT_EMPLOYEE_SKILL_EMPLOYEE_ID, DEFAULT_EMPLOYEE_SKILL_NAME, DEFAULT_CERTIFICATE_TYPE_ID)
                    .subscribe(() => {});

                const req = httpMock.expectOne({ method: 'GET' });

                const resourceUrl = SERVER_API_URL + 'api/employee-skill-certificates';
                expect(req.request.url).toEqual(
                    resourceUrl +
                        '/' +
                        'employeeSkillEmployeeId=' +
                        DEFAULT_EMPLOYEE_SKILL_NAME +
                        ';employeeSkillName=' +
                        DEFAULT_EMPLOYEE_SKILL_NAME +
                        ';certificateTypeId=' +
                        DEFAULT_CERTIFICATE_TYPE_ID
                );
            });

            it('should create a EmployeeSkillCertificate', () => {
                service.create(new EmployeeSkillCertificate(null)).subscribe(received => {
                    expect(received.body.employeeSkillEmployeeId).toEqual(null);
                    expect(received.body.employeeSkillName).toEqual(null);
                    expect(received.body.certificateTypeId).toEqual(null);
                });

                const req = httpMock.expectOne({ method: 'POST' });
                req.flush({ id: null });
            });

            it('should update a EmployeeSkillCertificate', () => {
                service
                    .update(
                        new EmployeeSkillCertificate(
                            DEFAULT_EMPLOYEE_SKILL_EMPLOYEE_ID,
                            DEFAULT_EMPLOYEE_SKILL_NAME,
                            DEFAULT_CERTIFICATE_TYPE_ID
                        )
                    )
                    .subscribe(received => {
                        expect(received.body.employeeSkillEmployeeId).toEqual(DEFAULT_EMPLOYEE_SKILL_EMPLOYEE_ID);
                        expect(received.body.employeeSkillName).toEqual(DEFAULT_EMPLOYEE_SKILL_NAME);
                        expect(received.body.certificateTypeId).toEqual(DEFAULT_CERTIFICATE_TYPE_ID);
                    });

                const req = httpMock.expectOne({ method: 'PUT' });
                req.flush({
                    employeeSkillEmployeeId: DEFAULT_EMPLOYEE_SKILL_EMPLOYEE_ID,
                    employeeSkillName: DEFAULT_EMPLOYEE_SKILL_NAME,
                    certificateType: DEFAULT_CERTIFICATE_TYPE_ID
                });
            });

            it('should return a EmployeeSkillCertificate', () => {
                service
                    .find(DEFAULT_EMPLOYEE_SKILL_EMPLOYEE_ID, DEFAULT_EMPLOYEE_SKILL_NAME, DEFAULT_CERTIFICATE_TYPE_ID)
                    .subscribe(received => {
                        expect(received.body.employeeSkillEmployeeId).toEqual(DEFAULT_EMPLOYEE_SKILL_EMPLOYEE_ID);
                        expect(received.body.employeeSkillName).toEqual(DEFAULT_EMPLOYEE_SKILL_NAME);
                        expect(received.body.certificateTypeId).toEqual(DEFAULT_CERTIFICATE_TYPE_ID);
                    });

                const req = httpMock.expectOne({ method: 'GET' });
                req.flush({
                    employeeSkillEmployeeId: DEFAULT_EMPLOYEE_SKILL_EMPLOYEE_ID,
                    employeeSkillName: DEFAULT_EMPLOYEE_SKILL_NAME,
                    certificateType: DEFAULT_CERTIFICATE_TYPE_ID
                });
            });

            it('should return a list of EmployeeSkillCertificate', () => {
                service.query(null).subscribe(received => {
                    expect(received.body[0].employeeSkillEmployeeId).toEqual(DEFAULT_EMPLOYEE_SKILL_EMPLOYEE_ID);
                    expect(received.body[0].employeeSkillName).toEqual(DEFAULT_EMPLOYEE_SKILL_NAME);
                    expect(received.body[0].certificateTypeId).toEqual(DEFAULT_CERTIFICATE_TYPE_ID);
                });

                const req = httpMock.expectOne({ method: 'GET' });
                req.flush([
                    new EmployeeSkillCertificate(
                        DEFAULT_EMPLOYEE_SKILL_EMPLOYEE_ID,
                        DEFAULT_EMPLOYEE_SKILL_NAME,
                        DEFAULT_CERTIFICATE_TYPE_ID
                    )
                ]);
            });

            it('should delete a EmployeeSkillCertificate', () => {
                service
                    .delete(DEFAULT_EMPLOYEE_SKILL_EMPLOYEE_ID, DEFAULT_EMPLOYEE_SKILL_NAME, DEFAULT_CERTIFICATE_TYPE_ID)
                    .subscribe(received => {
                        expect(received.url).toContain(
                            '/' + DEFAULT_EMPLOYEE_SKILL_EMPLOYEE_ID + '/' + DEFAULT_EMPLOYEE_SKILL_NAME + '/' + DEFAULT_CERTIFICATE_TYPE_ID
                        );
                    });

                const req = httpMock.expectOne({ method: 'DELETE' });
                req.flush(null);
            });

            it('should propagate not found response', () => {
                service
                    .find(DEFAULT_EMPLOYEE_SKILL_EMPLOYEE_ID, DEFAULT_EMPLOYEE_SKILL_NAME, DEFAULT_CERTIFICATE_TYPE_ID)
                    .subscribe(null, (_error: any) => {
                        expect(_error.status).toEqual(404);
                    });

                const req = httpMock.expectOne({ method: 'GET' });
                req.flush('Invalid request parameters', {
                    status: 404,
                    statusText: 'Bad Request'
                });
            });
        });

        afterEach(() => {
            httpMock.verify();
        });
    });
});
