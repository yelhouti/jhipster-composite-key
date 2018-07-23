package com.elhouti.compositekey.service.mapper;

import com.elhouti.compositekey.domain.*;
import com.elhouti.compositekey.service.dto.EmployeeSkillDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity EmployeeSkill and its DTO EmployeeSkillDTO.
 */
@Mapper(componentModel = "spring", uses = {EmployeeMapper.class})
public interface EmployeeSkillMapper extends EntityMapper<EmployeeSkillDTO, EmployeeSkill> {

    @Mapping(source = "employee.id", target = "employeeId")
    EmployeeSkillDTO toDto(EmployeeSkill employeeSkill);

    @Mapping(target = "employeeSkillCertificates", ignore = true)
    @Mapping(source = "employeeId", target = "employee")
    EmployeeSkill toEntity(EmployeeSkillDTO employeeSkillDTO);

    default EmployeeSkill fromId(Long id) {
        if (id == null) {
            return null;
        }
        EmployeeSkill employeeSkill = new EmployeeSkill();
        employeeSkill.setId(id);
        return employeeSkill;
    }
}
