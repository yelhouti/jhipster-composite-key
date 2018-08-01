import { IEmployeeSkill } from 'app/shared/model//employee-skill.model';

export interface IEmployee {
    id?: string;
    fullname?: string;
    skills?: IEmployeeSkill[];
}

export class Employee implements IEmployee {
    constructor(public id?: string, public fullname?: string, public skills?: IEmployeeSkill[]) {}
}
