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

import com.elhouti.compositekey.domain.EmployeeSkillCertificate;
import com.elhouti.compositekey.domain.*; // for static metamodels
import com.elhouti.compositekey.repository.EmployeeSkillCertificateRepository;
import com.elhouti.compositekey.service.dto.EmployeeSkillCertificateCriteria;

import com.elhouti.compositekey.service.dto.EmployeeSkillCertificateDTO;
import com.elhouti.compositekey.service.mapper.EmployeeSkillCertificateMapper;

/**
 * Service for executing complex queries for EmployeeSkillCertificate entities in the database.
 * The main input is a {@link EmployeeSkillCertificateCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link EmployeeSkillCertificateDTO} or a {@link Page} of {@link EmployeeSkillCertificateDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class EmployeeSkillCertificateQueryService extends QueryService<EmployeeSkillCertificate> {

    private final Logger log = LoggerFactory.getLogger(EmployeeSkillCertificateQueryService.class);

    private final EmployeeSkillCertificateRepository employeeSkillCertificateRepository;

    private final EmployeeSkillCertificateMapper employeeSkillCertificateMapper;

    public EmployeeSkillCertificateQueryService(EmployeeSkillCertificateRepository employeeSkillCertificateRepository, EmployeeSkillCertificateMapper employeeSkillCertificateMapper) {
        this.employeeSkillCertificateRepository = employeeSkillCertificateRepository;
        this.employeeSkillCertificateMapper = employeeSkillCertificateMapper;
    }

    /**
     * Return a {@link List} of {@link EmployeeSkillCertificateDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<EmployeeSkillCertificateDTO> findByCriteria(EmployeeSkillCertificateCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<EmployeeSkillCertificate> specification = createSpecification(criteria);
        return employeeSkillCertificateMapper.toDto(employeeSkillCertificateRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link EmployeeSkillCertificateDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<EmployeeSkillCertificateDTO> findByCriteria(EmployeeSkillCertificateCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<EmployeeSkillCertificate> specification = createSpecification(criteria);
        return employeeSkillCertificateRepository.findAll(specification, page)
            .map(employeeSkillCertificateMapper::toDto);
    }

    /**
     * Function to convert EmployeeSkillCertificateCriteria to a {@link Specification}
     */
    private Specification<EmployeeSkillCertificate> createSpecification(EmployeeSkillCertificateCriteria criteria) {
        Specification<EmployeeSkillCertificate> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), EmployeeSkillCertificate_.id));
            }
            if (criteria.getGrade() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getGrade(), EmployeeSkillCertificate_.grade));
            }
            if (criteria.getDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDate(), EmployeeSkillCertificate_.date));
            }
            if (criteria.getCertificateTypeId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getCertificateTypeId(), EmployeeSkillCertificate_.certificateType, CertificateType_.id));
            }
            if (criteria.getEmployeeSkillId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getEmployeeSkillId(), EmployeeSkillCertificate_.employeeSkill, EmployeeSkill_.id));
            }
        }
        return specification;
    }

}
