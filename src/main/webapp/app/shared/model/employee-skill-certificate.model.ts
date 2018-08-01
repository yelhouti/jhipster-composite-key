import { Moment } from 'moment';

export interface IEmployeeSkillCertificate {
    employeeSkillEmployeeId?: string;
    employeeSkillName?: string;
    certificateTypeId?: number;
    grade?: number;
    date?: Moment;
    certificateTypeName?: string;
}

export class EmployeeSkillCertificate implements IEmployeeSkillCertificate {
    constructor(
        public employeeSkillEmployeeId?: string,
        public employeeSkillName?: string,
        public certificateTypeId?: number,
        public grade?: number,
        public date?: Moment,
        public certificateTypeName?: string
    ) {}
}
