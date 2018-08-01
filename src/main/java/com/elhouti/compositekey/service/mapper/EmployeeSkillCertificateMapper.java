package com.elhouti.compositekey.service.mapper;

import com.elhouti.compositekey.domain.*;
import com.elhouti.compositekey.service.dto.EmployeeSkillCertificateDTO;

import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

/**
 * Mapper for the entity EmployeeSkillCertificate and its DTO EmployeeSkillCertificateDTO.
 */
@Mapper(componentModel = "spring", uses = {CertificateTypeMapper.class, EmployeeSkillMapper.class})
public abstract class EmployeeSkillCertificateMapper implements EntityMapper<EmployeeSkillCertificateDTO, EmployeeSkillCertificate> {

    EmployeeSkillMapper EMPLOYEE_SKILL_MAPPER = Mappers.getMapper(EmployeeSkillMapper.class);

    @Mapping(source = "id.employeeSkillEmployeeId", target = "employeeSkillEmployeeId")
    @Mapping(source = "id.employeeSkillName", target = "employeeSkillName")
    @Mapping(source = "id.certificateTypeId", target = "certificateTypeId")
    @Mapping(source = "certificateType.name", target = "certificateTypeName")
    public abstract EmployeeSkillCertificateDTO toDto(EmployeeSkillCertificate employeeSkillCertificate);

    @Mapping(source = "certificateTypeId", target = "id.certificateTypeId")
    @Mapping(source = "employeeSkillEmployeeId", target = "id.employeeSkillEmployeeId")
    @Mapping(source = "employeeSkillName", target = "id.employeeSkillName")
    @Mapping(ignore = true, target = "employeeSkill")
    @Mapping(source = "certificateTypeId", target = "certificateType")
    public abstract EmployeeSkillCertificate toEntity(EmployeeSkillCertificateDTO employeeSkillCertificateDTO);

    @AfterMapping
    protected void addEmployeeSkill(@MappingTarget EmployeeSkillCertificate employeeSkillCertificate, EmployeeSkillCertificateDTO employeeSkillCertificateDTO){
        employeeSkillCertificate.setEmployeeSkill(
            EMPLOYEE_SKILL_MAPPER.fromId(
                new EmployeeSkillId(employeeSkillCertificateDTO.getEmployeeSkillEmployeeId(),
                    employeeSkillCertificateDTO.getEmployeeSkillName())
            )
        );
    }

    public EmployeeSkillCertificate fromId(EmployeeSkillCertificateId id) {
        if (id == null) {
            return null;
        }
        EmployeeSkillCertificate employeeSkillCertificate = new EmployeeSkillCertificate();
        employeeSkillCertificate.setId(id);
        return employeeSkillCertificate;
    }
}
