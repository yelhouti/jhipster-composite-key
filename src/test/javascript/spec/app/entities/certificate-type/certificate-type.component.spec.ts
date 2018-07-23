/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { CompositekeyTestModule } from '../../../test.module';
import { CertificateTypeComponent } from 'app/entities/certificate-type/certificate-type.component';
import { CertificateTypeService } from 'app/entities/certificate-type/certificate-type.service';
import { CertificateType } from 'app/shared/model/certificate-type.model';

describe('Component Tests', () => {
    describe('CertificateType Management Component', () => {
        let comp: CertificateTypeComponent;
        let fixture: ComponentFixture<CertificateTypeComponent>;
        let service: CertificateTypeService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CompositekeyTestModule],
                declarations: [CertificateTypeComponent],
                providers: []
            })
                .overrideTemplate(CertificateTypeComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(CertificateTypeComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CertificateTypeService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new CertificateType(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.certificateTypes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
