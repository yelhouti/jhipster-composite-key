package com.elhouti.compositekey.service.dto;

import com.elhouti.compositekey.domain.EmployeeSkillCertificate;
import com.elhouti.compositekey.domain.EmployeeSkillId;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the EmployeeSkillCertificate entity.
 */
public class EmployeeSkillCertificateDTO implements Serializable {

    @NotNull
    private Long certificateTypeId;

    @NotNull
    private String employeeSkillEmployeeId;

    @NotNull
    private String employeeSkillName;

    @NotNull
    private Integer grade;

    @NotNull
    private LocalDate date;

    private String certificateTypeName;

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

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getCertificateTypeName() {
        return certificateTypeName;
    }

    public void setCertificateTypeName(String certificateTypeName) {
        this.certificateTypeName = certificateTypeName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EmployeeSkillCertificateDTO employeeSkillCertificateDTO = (EmployeeSkillCertificateDTO) o;
        if(employeeSkillCertificateDTO.getEmployeeSkillEmployeeId() == null || getEmployeeSkillEmployeeId() == null ||
            employeeSkillCertificateDTO.getEmployeeSkillName() == null || getEmployeeSkillName() == null ||
            employeeSkillCertificateDTO.getCertificateTypeId() == null || getCertificateTypeId() == null) {
            return false;
        }
        return Objects.equals(getEmployeeSkillEmployeeId(), employeeSkillCertificateDTO.getEmployeeSkillEmployeeId()) &&
            Objects.equals(getEmployeeSkillName(), employeeSkillCertificateDTO.getEmployeeSkillName()) &&
            Objects.equals(getCertificateTypeId(), employeeSkillCertificateDTO.getCertificateTypeId());
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + Objects.hashCode(this.employeeSkillEmployeeId);
        result = 31 * result + Objects.hashCode(this.employeeSkillName);
        result = 31 * result + Objects.hashCode(this.certificateTypeId);
        return result;
    }

    @Override
    public String toString() {
        return "EmployeeSkillCertificateDTO{" +
            ", employeeSkillEmployeeId=" + getEmployeeSkillEmployeeId() +
            ", employeeSkillName=" + getEmployeeSkillName() +
            ", certificateType=" + getCertificateTypeId() +
            ", grade=" + getGrade() +
            ", date='" + getDate() + "'" +
            ", certificateType='" + getCertificateTypeName() + "'" +
            "}";
    }
}
