import { IEmployeeSkill } from 'app/shared/model//employee-skill.model';

export interface IEmployee {
    id?: number;
    fullname?: string;
    skills?: IEmployeeSkill[];
}

export class Employee implements IEmployee {
    constructor(public id?: number, public fullname?: string, public skills?: IEmployeeSkill[]) {}
}
