package com.elhouti.compositekey.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A EmployeeSkillCertificate.
 */
@Entity
@Table(name = "employee_skill_certificate")
public class EmployeeSkillCertificate implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private EmployeeSkillCertificateId id;

    @NotNull
    @Column(name = "grade", nullable = false)
    private Integer grade;

    @NotNull
    @Column(name = "jhi_date", nullable = false)
    private LocalDate date;

    @ManyToOne(optional = false)
    @JoinColumn(name="certificate_type_id", insertable = false, updatable = false)
    @NotNull
    @JsonIgnoreProperties("employeeSkillCertificates")
    private CertificateType certificateType;

    @ManyToOne(optional = false)
    @JoinColumns({
        @JoinColumn(name = "employee_skill_employee_id", insertable = false, updatable = false),
        @JoinColumn(name = "employee_skill_name", insertable = false, updatable = false)
    })
    @NotNull
    @JsonIgnoreProperties("employeeSkillCertificates")
    private EmployeeSkill employeeSkill;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public EmployeeSkillCertificateId getId() {
        return id;
    }

    public void setId(EmployeeSkillCertificateId id) {
        this.id = id;
    }

    public Integer getGrade() {
        return grade;
    }

    public EmployeeSkillCertificate grade(Integer grade) {
        this.grade = grade;
        return this;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public LocalDate getDate() {
        return date;
    }

    public EmployeeSkillCertificate date(LocalDate date) {
        this.date = date;
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public CertificateType getCertificateType() {
        return certificateType;
    }

    public EmployeeSkillCertificate certificateType(CertificateType certificateType) {
        this.certificateType = certificateType;
        return this;
    }

    public void setCertificateType(CertificateType certificateType) {
        this.certificateType = certificateType;
    }

    public EmployeeSkill getEmployeeSkill() {
        return employeeSkill;
    }

    public EmployeeSkillCertificate employeeSkill(EmployeeSkill employeeSkill) {
        this.employeeSkill = employeeSkill;
        return this;
    }

    public void setEmployeeSkill(EmployeeSkill employeeSkill) {
        this.employeeSkill = employeeSkill;
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
        EmployeeSkillCertificate employeeSkillCertificate = (EmployeeSkillCertificate) o;
        if (employeeSkillCertificate.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), employeeSkillCertificate.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EmployeeSkillCertificate{" +
            "id=" + getId() +
            ", grade=" + getGrade() +
            ", date='" + getDate() + "'" +
            "}";
    }
}
