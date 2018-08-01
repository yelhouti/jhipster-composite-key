/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { EmployeeSkillService } from 'app/entities/employee-skill/employee-skill.service';
import { EmployeeSkill } from 'app/shared/model/employee-skill.model';
import { SERVER_API_URL } from 'app/app.constants';

describe('Service Tests', () => {
    describe('EmployeeSkill Service', () => {
        let injector: TestBed;
        let service: EmployeeSkillService;
        let httpMock: HttpTestingController;
        const DEFAULT_EMPLOYEE_ID = 'AAAAAAAAAAA';
        const DEFAULT_NAME = 'AAAAAAAAAAA';

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(EmployeeSkillService);
            httpMock = injector.get(HttpTestingController);
        });

        describe('Service methods', () => {
            it('should call correct URL', () => {
                service.find(DEFAULT_EMPLOYEE_ID, DEFAULT_NAME).subscribe(() => {});

                const req = httpMock.expectOne({ method: 'GET' });

                const resourceUrl = SERVER_API_URL + 'api/employee-skills';
                expect(req.request.url).toEqual(resourceUrl + '/' + 'employeeId=' + DEFAULT_EMPLOYEE_ID + ';name=' + DEFAULT_NAME);
            });

            it('should create a EmployeeSkill', () => {
                service.create(new EmployeeSkill(DEFAULT_EMPLOYEE_ID, DEFAULT_NAME)).subscribe(received => {
                    expect(received.body.employeeId).toEqual(DEFAULT_EMPLOYEE_ID);
                    expect(received.body.name).toEqual(DEFAULT_NAME);
                });

                const req = httpMock.expectOne({ method: 'POST' });
                req.flush({ employeeId: DEFAULT_EMPLOYEE_ID, name: DEFAULT_NAME });
            });

            it('should update a EmployeeSkill', () => {
                service.update(new EmployeeSkill(DEFAULT_EMPLOYEE_ID, DEFAULT_NAME)).subscribe(received => {
                    expect(received.body.employeeId).toEqual(DEFAULT_EMPLOYEE_ID);
                    expect(received.body.name).toEqual(DEFAULT_NAME);
                });

                const req = httpMock.expectOne({ method: 'PUT' });
                req.flush({ employeeId: DEFAULT_EMPLOYEE_ID, name: DEFAULT_NAME });
            });

            it('should return a EmployeeSkill', () => {
                service.find(DEFAULT_EMPLOYEE_ID, DEFAULT_NAME).subscribe(received => {
                    expect(received.body.employeeId).toEqual(DEFAULT_EMPLOYEE_ID);
                    expect(received.body.name).toEqual(DEFAULT_NAME);
                });

                const req = httpMock.expectOne({ method: 'GET' });
                req.flush({ employeeId: DEFAULT_EMPLOYEE_ID, name: DEFAULT_NAME });
            });

            it('should return a list of EmployeeSkill', () => {
                service.query(null).subscribe(received => {
                    expect(received.body[0].employeeId).toEqual(DEFAULT_EMPLOYEE_ID);
                    expect(received.body[0].name).toEqual(DEFAULT_NAME);
                });

                const req = httpMock.expectOne({ method: 'GET' });
                req.flush([new EmployeeSkill(DEFAULT_EMPLOYEE_ID, DEFAULT_NAME)]);
            });

            it('should delete a EmployeeSkill', () => {
                service.delete(DEFAULT_EMPLOYEE_ID, DEFAULT_NAME).subscribe(received => {
                    expect(received.url).toContain('/' + DEFAULT_EMPLOYEE_ID + '/' + DEFAULT_NAME);
                });

                const req = httpMock.expectOne({ method: 'DELETE' });
                req.flush(null);
            });

            it('should propagate not found response', () => {
                service.find(DEFAULT_EMPLOYEE_ID, DEFAULT_NAME).subscribe(null, (_error: any) => {
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
