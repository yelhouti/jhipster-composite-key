package com.elhouti.compositekey.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the EmployeeSkillCertificate entity.
 */
public class EmployeeSkillCertificateDTO implements Serializable {

    private Long id;

    @NotNull
    private Integer grade;

    @NotNull
    private LocalDate date;

    private Long certificateTypeId;

    private String certificateTypeName;

    private Long employeeSkillId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Long getCertificateTypeId() {
        return certificateTypeId;
    }

    public void setCertificateTypeId(Long certificateTypeId) {
        this.certificateTypeId = certificateTypeId;
    }

    public String getCertificateTypeName() {
        return certificateTypeName;
    }

    public void setCertificateTypeName(String certificateTypeName) {
        this.certificateTypeName = certificateTypeName;
    }

    public Long getEmployeeSkillId() {
        return employeeSkillId;
    }

    public void setEmployeeSkillId(Long employeeSkillId) {
        this.employeeSkillId = employeeSkillId;
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
        if (employeeSkillCertificateDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), employeeSkillCertificateDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EmployeeSkillCertificateDTO{" +
            "id=" + getId() +
            ", grade=" + getGrade() +
            ", date='" + getDate() + "'" +
            ", certificateType=" + getCertificateTypeId() +
            ", certificateType='" + getCertificateTypeName() + "'" +
            ", employeeSkill=" + getEmployeeSkillId() +
            "}";
    }
}
