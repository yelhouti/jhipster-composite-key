import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IEmployeeSkill } from 'app/shared/model/employee-skill.model';

type EntityResponseType = HttpResponse<IEmployeeSkill>;
type EntityArrayResponseType = HttpResponse<IEmployeeSkill[]>;

@Injectable({ providedIn: 'root' })
export class EmployeeSkillService {
    private resourceUrl = SERVER_API_URL + 'api/employee-skills';

    constructor(private http: HttpClient) {}

    create(employeeSkill: IEmployeeSkill): Observable<EntityResponseType> {
        return this.http.post<IEmployeeSkill>(this.resourceUrl, employeeSkill, { observe: 'response' });
    }

    update(employeeSkill: IEmployeeSkill): Observable<EntityResponseType> {
        return this.http.put<IEmployeeSkill>(this.resourceUrl, employeeSkill, { observe: 'response' });
    }

    find(employeeId: string, name: string): Observable<EntityResponseType> {
        return this.http.get<IEmployeeSkill>(`${this.resourceUrl}/employeeId=${employeeId};name=${name}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IEmployeeSkill[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(employeeId: string, name: string): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/employeeId=${employeeId};name=${name}`, { observe: 'response' });
    }
}
