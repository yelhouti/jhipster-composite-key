import { Moment } from 'moment';

export interface IEmployeeSkillCertificate {
    id?: number;
    grade?: number;
    date?: Moment;
    certificateTypeName?: string;
    certificateTypeId?: number;
    employeeSkillId?: number;
}

export class EmployeeSkillCertificate implements IEmployeeSkillCertificate {
    constructor(
        public id?: number,
        public grade?: number,
        public date?: Moment,
        public certificateTypeName?: string,
        public certificateTypeId?: number,
        public employeeSkillId?: number
    ) {}
}
