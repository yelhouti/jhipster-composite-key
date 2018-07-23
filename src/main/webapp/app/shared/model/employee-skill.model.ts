import { IEmployeeSkillCertificate } from 'app/shared/model//employee-skill-certificate.model';

export interface IEmployeeSkill {
    id?: number;
    name?: string;
    level?: number;
    employeeSkillCertificates?: IEmployeeSkillCertificate[];
    employeeId?: number;
}

export class EmployeeSkill implements IEmployeeSkill {
    constructor(
        public id?: number,
        public name?: string,
        public level?: number,
        public employeeSkillCertificates?: IEmployeeSkillCertificate[],
        public employeeId?: number
    ) {}
}
