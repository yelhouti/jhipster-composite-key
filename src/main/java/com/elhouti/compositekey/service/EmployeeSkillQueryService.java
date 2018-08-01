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

import com.elhouti.compositekey.domain.EmployeeSkill;
import com.elhouti.compositekey.domain.*; // for static metamodels
import com.elhouti.compositekey.repository.EmployeeSkillRepository;
import com.elhouti.compositekey.service.dto.EmployeeSkillCriteria;

import com.elhouti.compositekey.service.dto.EmployeeSkillDTO;
import com.elhouti.compositekey.service.mapper.EmployeeSkillMapper;

/**
 * Service for executing complex queries for EmployeeSkill entities in the database.
 * The main input is a {@link EmployeeSkillCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link EmployeeSkillDTO} or a {@link Page} of {@link EmployeeSkillDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class EmployeeSkillQueryService extends QueryService<EmployeeSkill> {

    private final Logger log = LoggerFactory.getLogger(EmployeeSkillQueryService.class);

    private final EmployeeSkillRepository employeeSkillRepository;

    private final EmployeeSkillMapper employeeSkillMapper;

    public EmployeeSkillQueryService(EmployeeSkillRepository employeeSkillRepository, EmployeeSkillMapper employeeSkillMapper) {
        this.employeeSkillRepository = employeeSkillRepository;
        this.employeeSkillMapper = employeeSkillMapper;
    }

    /**
     * Return a {@link List} of {@link EmployeeSkillDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<EmployeeSkillDTO> findByCriteria(EmployeeSkillCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<EmployeeSkill> specification = createSpecification(criteria);
        return employeeSkillMapper.toDto(employeeSkillRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link EmployeeSkillDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<EmployeeSkillDTO> findByCriteria(EmployeeSkillCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<EmployeeSkill> specification = createSpecification(criteria);
        return employeeSkillRepository.findAll(specification, page)
            .map(employeeSkillMapper::toDto);
    }

    /**
     * Function to convert EmployeeSkillCriteria to a {@link Specification}
     */
    private Specification<EmployeeSkill> createSpecification(EmployeeSkillCriteria criteria) {
        Specification<EmployeeSkill> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getName() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getName(), EmployeeSkill_.id, EmployeeSkillId_.name));
            }
            if (criteria.getLevel() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLevel(), EmployeeSkill_.level));
            }
            if (criteria.getEmployeeId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getEmployeeId(), EmployeeSkill_.id, EmployeeSkillId_.employeeId));
            }
        }
        return specification;
    }

}
