/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { CompositekeyTestModule } from '../../../test.module';
import { EmployeeSkillDeleteDialogComponent } from 'app/entities/employee-skill/employee-skill-delete-dialog.component';
import { EmployeeSkillService } from 'app/entities/employee-skill/employee-skill.service';

describe('Component Tests', () => {
    describe('EmployeeSkill Management Delete Component', () => {
        let comp: EmployeeSkillDeleteDialogComponent;
        let fixture: ComponentFixture<EmployeeSkillDeleteDialogComponent>;
        let service: EmployeeSkillService;
        let mockEventManager: any;
        let mockActiveModal: any;
        const DEFAULT_EMPLOYEE_ID = 'AAAAAAAAAAA';
        const DEFAULT_NAME = 'AAAAAAAAAAA';

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CompositekeyTestModule],
                declarations: [EmployeeSkillDeleteDialogComponent]
            })
                .overrideTemplate(EmployeeSkillDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(EmployeeSkillDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EmployeeSkillService);
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
                        comp.confirmDelete(DEFAULT_EMPLOYEE_ID, DEFAULT_NAME);
                        tick();

                        // THEN
                        expect(service.delete).toHaveBeenCalledWith(DEFAULT_EMPLOYEE_ID, DEFAULT_NAME);
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });
});
