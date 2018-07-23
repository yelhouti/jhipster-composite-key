package com.elhouti.compositekey.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.elhouti.compositekey.domain.CertificateType;
import com.elhouti.compositekey.domain.*; // for static metamodels
import com.elhouti.compositekey.repository.CertificateTypeRepository;
import com.elhouti.compositekey.service.dto.CertificateTypeCriteria;

import com.elhouti.compositekey.service.dto.CertificateTypeDTO;
import com.elhouti.compositekey.service.mapper.CertificateTypeMapper;

/**
 * Service for executing complex queries for CertificateType entities in the database.
 * The main input is a {@link CertificateTypeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CertificateTypeDTO} or a {@link Page} of {@link CertificateTypeDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CertificateTypeQueryService extends QueryService<CertificateType> {

    private final Logger log = LoggerFactory.getLogger(CertificateTypeQueryService.class);

    private final CertificateTypeRepository certificateTypeRepository;

    private final CertificateTypeMapper certificateTypeMapper;

    public CertificateTypeQueryService(CertificateTypeRepository certificateTypeRepository, CertificateTypeMapper certificateTypeMapper) {
        this.certificateTypeRepository = certificateTypeRepository;
        this.certificateTypeMapper = certificateTypeMapper;
    }

    /**
     * Return a {@link List} of {@link CertificateTypeDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CertificateTypeDTO> findByCriteria(CertificateTypeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CertificateType> specification = createSpecification(criteria);
        return certificateTypeMapper.toDto(certificateTypeRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CertificateTypeDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CertificateTypeDTO> findByCriteria(CertificateTypeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CertificateType> specification = createSpecification(criteria);
        return certificateTypeRepository.findAll(specification, page)
            .map(certificateTypeMapper::toDto);
    }

    /**
     * Function to convert CertificateTypeCriteria to a {@link Specification}
     */
    private Specification<CertificateType> createSpecification(CertificateTypeCriteria criteria) {
        Specification<CertificateType> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), CertificateType_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), CertificateType_.name));
            }
            if (criteria.getEmployeeSkillCertificateId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getEmployeeSkillCertificateId(), CertificateType_.employeeSkillCertificates, EmployeeSkillCertificate_.id));
            }
        }
        return specification;
    }

}
