package com.elhouti.compositekey.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Employee.
 */
@Entity
@Table(name = "employee")
public class Employee implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "fullname", nullable = false)
    private String fullname;

    @OneToMany(mappedBy = "employee")
    private Set<EmployeeSkill> skills = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public Employee fullname(String fullname) {
        this.fullname = fullname;
        return this;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public Set<EmployeeSkill> getSkills() {
        return skills;
    }

    public Employee skills(Set<EmployeeSkill> employeeSkills) {
        this.skills = employeeSkills;
        return this;
    }

    public Employee addSkill(EmployeeSkill employeeSkill) {
        this.skills.add(employeeSkill);
        employeeSkill.setEmployee(this);
        return this;
    }

    public Employee removeSkill(EmployeeSkill employeeSkill) {
        this.skills.remove(employeeSkill);
        employeeSkill.setEmployee(null);
        return this;
    }

    public void setSkills(Set<EmployeeSkill> employeeSkills) {
        this.skills = employeeSkills;
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
        Employee employee = (Employee) o;
        if (employee.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), employee.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Employee{" +
            "id=" + getId() +
            ", fullname='" + getFullname() + "'" +
            "}";
    }
}
