package com.elhouti.compositekey.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the EmployeeSkill entity.
 */
public class EmployeeSkillDTO implements Serializable {

    @NotNull
    private String name;

    @NotNull
    private Integer level;

    @NotNull
    private String employeeId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EmployeeSkillDTO employeeSkillDTO = (EmployeeSkillDTO) o;
        if(employeeSkillDTO.getEmployeeId() == null || getEmployeeId() == null ||
            employeeSkillDTO.getName() == null || getName() == null) {
            return false;
        }
        return Objects.equals(getEmployeeId(), employeeSkillDTO.getEmployeeId()) &&
            Objects.equals(getName(), employeeSkillDTO.getName());
    }

    @Override
    public int hashCode() {

            int result = 17;
            result = 31 * result + Objects.hashCode(this.employeeId);
            result = 31 * result + Objects.hashCode(this.name);
            return result;    }

    @Override
    public String toString() {
        return "EmployeeSkillDTO{" +
            ", employee=" + getEmployeeId() +
            ", name='" + getName() + "'" +
            ", level=" + getLevel() +
            "}";
    }
}
