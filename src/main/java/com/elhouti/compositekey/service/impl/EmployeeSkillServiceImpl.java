package com.elhouti.compositekey.service.impl;

import com.elhouti.compositekey.domain.EmployeeSkillId;
import com.elhouti.compositekey.service.EmployeeSkillService;
import com.elhouti.compositekey.domain.EmployeeSkill;
import com.elhouti.compositekey.repository.EmployeeSkillRepository;
import com.elhouti.compositekey.service.dto.EmployeeSkillDTO;
import com.elhouti.compositekey.service.mapper.EmployeeSkillMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
/**
 * Service Implementation for managing EmployeeSkill.
 */
@Service
@Transactional
public class EmployeeSkillServiceImpl implements EmployeeSkillService {

    private final Logger log = LoggerFactory.getLogger(EmployeeSkillServiceImpl.class);

    private final EmployeeSkillRepository employeeSkillRepository;

    private final EmployeeSkillMapper employeeSkillMapper;

    public EmployeeSkillServiceImpl(EmployeeSkillRepository employeeSkillRepository, EmployeeSkillMapper employeeSkillMapper) {
        this.employeeSkillRepository = employeeSkillRepository;
        this.employeeSkillMapper = employeeSkillMapper;
    }

    /**
     * Save a employeeSkill.
     *
     * @param employeeSkillDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public EmployeeSkillDTO save(EmployeeSkillDTO employeeSkillDTO) {
        log.debug("Request to save EmployeeSkill : {}", employeeSkillDTO);
        EmployeeSkill employeeSkill = employeeSkillMapper.toEntity(employeeSkillDTO);
        employeeSkill = employeeSkillRepository.save(employeeSkill);
        return employeeSkillMapper.toDto(employeeSkill);
    }

    /**
     * Get all the employeeSkills.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<EmployeeSkillDTO> findAll() {
        log.debug("Request to get all EmployeeSkills");
        return employeeSkillRepository.findAll().stream()
            .map(employeeSkillMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one employeeSkill by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<EmployeeSkillDTO> findOne(EmployeeSkillId id) {
        log.debug("Request to get EmployeeSkill : {}", id);
        return employeeSkillRepository.findById(id)
            .map(employeeSkillMapper::toDto);
    }

    /**
     * Delete the employeeSkill by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(EmployeeSkillId id) {
        log.debug("Request to delete EmployeeSkill : {}", id);
        employeeSkillRepository.deleteById(id);
    }
}
