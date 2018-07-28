/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { CompositekeyTestModule } from '../../../test.module';
import { EmployeeSkillUpdateComponent } from 'app/entities/employee-skill/employee-skill-update.component';
import { EmployeeSkillService } from 'app/entities/employee-skill/employee-skill.service';
import { EmployeeSkill } from 'app/shared/model/employee-skill.model';

describe('Component Tests', () => {
    describe('EmployeeSkill Management Update Component', () => {
        let comp: EmployeeSkillUpdateComponent;
        let fixture: ComponentFixture<EmployeeSkillUpdateComponent>;
        let service: EmployeeSkillService;
        const DEFAULT_EMPLOYEE_ID = 'AAAAAAAAAAA';
        const DEFAULT_NAME = 'AAAAAAAAAAA';

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CompositekeyTestModule],
                declarations: [EmployeeSkillUpdateComponent]
            })
                .overrideTemplate(EmployeeSkillUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(EmployeeSkillUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EmployeeSkillService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new EmployeeSkill(DEFAULT_EMPLOYEE_ID, DEFAULT_NAME);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.employeeSkill = entity;
                    // WHEN
                    comp.edit = true;
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new EmployeeSkill();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.employeeSkill = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
