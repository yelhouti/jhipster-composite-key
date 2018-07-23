package com.elhouti.compositekey.service.mapper;

import com.elhouti.compositekey.domain.*;
import com.elhouti.compositekey.service.dto.EmployeeSkillCertificateDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity EmployeeSkillCertificate and its DTO EmployeeSkillCertificateDTO.
 */
@Mapper(componentModel = "spring", uses = {CertificateTypeMapper.class, EmployeeSkillMapper.class})
public interface EmployeeSkillCertificateMapper extends EntityMapper<EmployeeSkillCertificateDTO, EmployeeSkillCertificate> {

    @Mapping(source = "certificateType.id", target = "certificateTypeId")
    @Mapping(source = "certificateType.name", target = "certificateTypeName")
    @Mapping(source = "employeeSkill.id", target = "employeeSkillId")
    EmployeeSkillCertificateDTO toDto(EmployeeSkillCertificate employeeSkillCertificate);

    @Mapping(source = "certificateTypeId", target = "certificateType")
    @Mapping(source = "employeeSkillId", target = "employeeSkill")
    EmployeeSkillCertificate toEntity(EmployeeSkillCertificateDTO employeeSkillCertificateDTO);

    default EmployeeSkillCertificate fromId(Long id) {
        if (id == null) {
            return null;
        }
        EmployeeSkillCertificate employeeSkillCertificate = new EmployeeSkillCertificate();
        employeeSkillCertificate.setId(id);
        return employeeSkillCertificate;
    }
}
