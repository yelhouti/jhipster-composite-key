package com.elhouti.compositekey.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A EmployeeSkill.
 */
@Entity
@Table(name = "employee_skill")
public class EmployeeSkill implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "jhi_level", nullable = false)
    private Integer level;

    @OneToMany(mappedBy = "employeeSkill")
    private Set<EmployeeSkillCertificate> employeeSkillCertificates = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("skills")
    private Employee employee;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public EmployeeSkill name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getLevel() {
        return level;
    }

    public EmployeeSkill level(Integer level) {
        this.level = level;
        return this;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Set<EmployeeSkillCertificate> getEmployeeSkillCertificates() {
        return employeeSkillCertificates;
    }

    public EmployeeSkill employeeSkillCertificates(Set<EmployeeSkillCertificate> employeeSkillCertificates) {
        this.employeeSkillCertificates = employeeSkillCertificates;
        return this;
    }

    public EmployeeSkill addEmployeeSkillCertificate(EmployeeSkillCertificate employeeSkillCertificate) {
        this.employeeSkillCertificates.add(employeeSkillCertificate);
        employeeSkillCertificate.setEmployeeSkill(this);
        return this;
    }

    public EmployeeSkill removeEmployeeSkillCertificate(EmployeeSkillCertificate employeeSkillCertificate) {
        this.employeeSkillCertificates.remove(employeeSkillCertificate);
        employeeSkillCertificate.setEmployeeSkill(null);
        return this;
    }

    public void setEmployeeSkillCertificates(Set<EmployeeSkillCertificate> employeeSkillCertificates) {
        this.employeeSkillCertificates = employeeSkillCertificates;
    }

    public Employee getEmployee() {
        return employee;
    }

    public EmployeeSkill employee(Employee employee) {
        this.employee = employee;
        return this;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
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
        EmployeeSkill employeeSkill = (EmployeeSkill) o;
        if (employeeSkill.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), employeeSkill.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EmployeeSkill{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", level=" + getLevel() +
            "}";
    }
}
