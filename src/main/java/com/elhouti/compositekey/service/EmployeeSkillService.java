package com.elhouti.compositekey.service;

import com.elhouti.compositekey.domain.EmployeeSkillId;
import com.elhouti.compositekey.service.dto.EmployeeSkillDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing EmployeeSkill.
 */
public interface EmployeeSkillService {

    /**
     * Save a employeeSkill.
     *
     * @param employeeSkillDTO the entity to save
     * @return the persisted entity
     */
    EmployeeSkillDTO save(EmployeeSkillDTO employeeSkillDTO);

    /**
     * Get all the employeeSkills.
     *
     * @return the list of entities
     */
    List<EmployeeSkillDTO> findAll();


    /**
     * Get the "id" employeeSkill.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<EmployeeSkillDTO> findOne(EmployeeSkillId id);

    /**
     * Delete the "id" employeeSkill.
     *
     * @param id the id of the entity
     */
    void delete(EmployeeSkillId id);
}
