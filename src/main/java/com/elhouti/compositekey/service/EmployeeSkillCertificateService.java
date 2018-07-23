package com.elhouti.compositekey.service;

import com.elhouti.compositekey.service.dto.EmployeeSkillCertificateDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing EmployeeSkillCertificate.
 */
public interface EmployeeSkillCertificateService {

    /**
     * Save a employeeSkillCertificate.
     *
     * @param employeeSkillCertificateDTO the entity to save
     * @return the persisted entity
     */
    EmployeeSkillCertificateDTO save(EmployeeSkillCertificateDTO employeeSkillCertificateDTO);

    /**
     * Get all the employeeSkillCertificates.
     *
     * @return the list of entities
     */
    List<EmployeeSkillCertificateDTO> findAll();


    /**
     * Get the "id" employeeSkillCertificate.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<EmployeeSkillCertificateDTO> findOne(Long id);

    /**
     * Delete the "id" employeeSkillCertificate.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
