package com.elhouti.compositekey.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class EmployeeSkillCertificateId implements java.io.Serializable {
    @Column(name = "employee_skill_employee_id", nullable = false)
    private String employeeSkillEmployeeId;

    @Column(name = "employee_skill_name", nullable = false)
    private String employeeSkillName;

    @Column(name = "certificate_type_id", nullable = false)
    private Long certificateTypeId;

    public EmployeeSkillCertificateId(){}
    public EmployeeSkillCertificateId(String employeeSkillEmployeeId, String employeeSkillName, Long certificateTypeId){
        this.employeeSkillEmployeeId = employeeSkillEmployeeId;
        this.employeeSkillName = employeeSkillName;
        this.certificateTypeId = certificateTypeId;
    }

    public String getEmployeeSkillEmployeeId() {
        return employeeSkillEmployeeId;
    }

    public void setEmployeeSkillEmployeeId(String employeeSkillEmployeeId) {
        this.employeeSkillEmployeeId = employeeSkillEmployeeId;
    }

    public String getEmployeeSkillName() {
        return employeeSkillName;
    }

    public void setEmployeeSkillName(String employeeSkillName) {
        this.employeeSkillName = employeeSkillName;
    }

    public Long getCertificateTypeId() {
        return certificateTypeId;
    }

    public void setCertificateTypeId(Long certificateTypeId) {
        this.certificateTypeId = certificateTypeId;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        EmployeeSkillCertificateId employeeSkillCertificateId = (EmployeeSkillCertificateId) o;
        return Objects.equals(employeeSkillEmployeeId, employeeSkillCertificateId.employeeSkillEmployeeId)
            && Objects.equals(employeeSkillName, employeeSkillCertificateId.employeeSkillName)
            && Objects.equals(certificateTypeId, employeeSkillCertificateId.certificateTypeId)
            ;
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + employeeSkillEmployeeId.hashCode();
        result = 31 * result + employeeSkillName.hashCode();
        result = 31 * result + certificateTypeId.hashCode();
        return result;
    }

    public String toMatrixVariableString() {
        return "employeeSkillEmployeeId="+getEmployeeSkillEmployeeId()+";"+"employeeSkillName="+getEmployeeSkillName()
            +";"+"certificateTypeId="+getCertificateTypeId();
    }
}
