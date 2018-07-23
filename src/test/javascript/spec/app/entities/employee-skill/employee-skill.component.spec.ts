/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { CompositekeyTestModule } from '../../../test.module';
import { EmployeeSkillComponent } from 'app/entities/employee-skill/employee-skill.component';
import { EmployeeSkillService } from 'app/entities/employee-skill/employee-skill.service';
import { EmployeeSkill } from 'app/shared/model/employee-skill.model';

describe('Component Tests', () => {
    describe('EmployeeSkill Management Component', () => {
        let comp: EmployeeSkillComponent;
        let fixture: ComponentFixture<EmployeeSkillComponent>;
        let service: EmployeeSkillService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CompositekeyTestModule],
                declarations: [EmployeeSkillComponent],
                providers: []
            })
                .overrideTemplate(EmployeeSkillComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(EmployeeSkillComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EmployeeSkillService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new EmployeeSkill(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.employeeSkills[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
