package com.elhouti.compositekey.service.mapper;

import com.elhouti.compositekey.domain.*;
import com.elhouti.compositekey.service.dto.EmployeeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Employee and its DTO EmployeeDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface EmployeeMapper extends EntityMapper<EmployeeDTO, Employee> {


    @Mapping(target = "skills", ignore = true)
    Employee toEntity(EmployeeDTO employeeDTO);

    default Employee fromId(String id) {
        if (id == null) {
            return null;
        }
        Employee employee = new Employee();
        employee.setId(id);
        return employee;
    }
}
