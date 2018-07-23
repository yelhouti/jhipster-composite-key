import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { CompositekeyEmployeeModule } from './employee/employee.module';
import { CompositekeyEmployeeSkillModule } from './employee-skill/employee-skill.module';
import { CompositekeyCertificateTypeModule } from './certificate-type/certificate-type.module';
import { CompositekeyEmployeeSkillCertificateModule } from './employee-skill-certificate/employee-skill-certificate.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        CompositekeyEmployeeModule,
        CompositekeyEmployeeSkillModule,
        CompositekeyCertificateTypeModule,
        CompositekeyEmployeeSkillCertificateModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CompositekeyEntityModule {}
