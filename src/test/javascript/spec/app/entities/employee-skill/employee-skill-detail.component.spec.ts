/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CompositekeyTestModule } from '../../../test.module';
import { EmployeeSkillDetailComponent } from 'app/entities/employee-skill/employee-skill-detail.component';
import { EmployeeSkill } from 'app/shared/model/employee-skill.model';

describe('Component Tests', () => {
    describe('EmployeeSkill Management Detail Component', () => {
        let comp: EmployeeSkillDetailComponent;
        let fixture: ComponentFixture<EmployeeSkillDetailComponent>;
        const DEFAULT_EMPLOYEE_ID = 'AAAAAAAAAAA';
        const DEFAULT_NAME = 'AAAAAAAAAAA';
        const route = ({ data: of({ employeeSkill: new EmployeeSkill(DEFAULT_EMPLOYEE_ID, DEFAULT_NAME) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CompositekeyTestModule],
                declarations: [EmployeeSkillDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(EmployeeSkillDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(EmployeeSkillDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.employeeSkill).toEqual(jasmine.objectContaining({ employeeId: DEFAULT_EMPLOYEE_ID, name: DEFAULT_NAME }));
            });
        });
    });
});
