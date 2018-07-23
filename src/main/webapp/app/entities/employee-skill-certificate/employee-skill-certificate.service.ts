import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IEmployeeSkillCertificate } from 'app/shared/model/employee-skill-certificate.model';

type EntityResponseType = HttpResponse<IEmployeeSkillCertificate>;
type EntityArrayResponseType = HttpResponse<IEmployeeSkillCertificate[]>;

@Injectable({ providedIn: 'root' })
export class EmployeeSkillCertificateService {
    private resourceUrl = SERVER_API_URL + 'api/employee-skill-certificates';

    constructor(private http: HttpClient) {}

    create(employeeSkillCertificate: IEmployeeSkillCertificate): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(employeeSkillCertificate);
        return this.http
            .post<IEmployeeSkillCertificate>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(employeeSkillCertificate: IEmployeeSkillCertificate): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(employeeSkillCertificate);
        return this.http
            .put<IEmployeeSkillCertificate>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IEmployeeSkillCertificate>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IEmployeeSkillCertificate[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertDateFromClient(employeeSkillCertificate: IEmployeeSkillCertificate): IEmployeeSkillCertificate {
        const copy: IEmployeeSkillCertificate = Object.assign({}, employeeSkillCertificate, {
            date:
                employeeSkillCertificate.date != null && employeeSkillCertificate.date.isValid()
                    ? employeeSkillCertificate.date.format(DATE_FORMAT)
                    : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.date = res.body.date != null ? moment(res.body.date) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((employeeSkillCertificate: IEmployeeSkillCertificate) => {
            employeeSkillCertificate.date = employeeSkillCertificate.date != null ? moment(employeeSkillCertificate.date) : null;
        });
        return res;
    }
}
