package com.elhouti.compositekey.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class EmployeeSkillId implements java.io.Serializable {

    @Column(name = "employee_id", nullable = false)
    private String employeeId;

    @Column(name = "name", nullable = false)
    private String name;

    public EmployeeSkillId(){}
    public EmployeeSkillId(String employeeId, String name){
        this.employeeId = employeeId;
        this.name = name;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        EmployeeSkillId employeeSkillId = (EmployeeSkillId) o;
        return Objects.equals(employeeId, employeeSkillId.employeeId)
            && Objects.equals(name, employeeSkillId.name)
            ;
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + employeeId.hashCode();
        result = 31 * result + name.hashCode();
        return result;
    }

    public String toMatrixVariableString() {
        return "employeeId="+getEmployeeId()+";"+"name="+getName();
    }
}
