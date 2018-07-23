/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CompositekeyTestModule } from '../../../test.module';
import { EmployeeDetailComponent } from 'app/entities/employee/employee-detail.component';
import { Employee } from 'app/shared/model/employee.model';

describe('Component Tests', () => {
    describe('Employee Management Detail Component', () => {
        let comp: EmployeeDetailComponent;
        let fixture: ComponentFixture<EmployeeDetailComponent>;
        const DEFAULT_EMPLOYEE_ID = 'AAAAAAA';
        const route = ({ data: of({ employee: new Employee(DEFAULT_EMPLOYEE_ID) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CompositekeyTestModule],
                declarations: [EmployeeDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(EmployeeDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(EmployeeDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.employee).toEqual(jasmine.objectContaining({ id: DEFAULT_EMPLOYEE_ID }));
            });
        });
    });
});
