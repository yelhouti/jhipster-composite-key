import { IEmployeeSkillCertificate } from 'app/shared/model//employee-skill-certificate.model';

export interface IEmployeeSkill {
    employeeId?: string;
    name?: string;
    level?: number;
    employeeSkillCertificates?: IEmployeeSkillCertificate[];
}

export class EmployeeSkill implements IEmployeeSkill {
    constructor(
        public employeeId?: string,
        public name?: string,
        public level?: number,
        public employeeSkillCertificates?: IEmployeeSkillCertificate[]
    ) {}
}
