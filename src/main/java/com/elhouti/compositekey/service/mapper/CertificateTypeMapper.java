package com.elhouti.compositekey.service.mapper;

import com.elhouti.compositekey.domain.*;
import com.elhouti.compositekey.service.dto.CertificateTypeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity CertificateType and its DTO CertificateTypeDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CertificateTypeMapper extends EntityMapper<CertificateTypeDTO, CertificateType> {


    @Mapping(target = "employeeSkillCertificates", ignore = true)
    CertificateType toEntity(CertificateTypeDTO certificateTypeDTO);

    default CertificateType fromId(Long id) {
        if (id == null) {
            return null;
        }
        CertificateType certificateType = new CertificateType();
        certificateType.setId(id);
        return certificateType;
    }
}
