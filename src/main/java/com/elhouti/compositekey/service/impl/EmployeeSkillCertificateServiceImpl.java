package com.elhouti.compositekey.service.impl;

import com.elhouti.compositekey.domain.EmployeeSkillCertificateId;
import com.elhouti.compositekey.service.EmployeeSkillCertificateService;
import com.elhouti.compositekey.domain.EmployeeSkillCertificate;
import com.elhouti.compositekey.repository.EmployeeSkillCertificateRepository;
import com.elhouti.compositekey.service.dto.EmployeeSkillCertificateDTO;
import com.elhouti.compositekey.service.mapper.EmployeeSkillCertificateMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
/**
 * Service Implementation for managing EmployeeSkillCertificate.
 */
@Service
@Transactional
public class EmployeeSkillCertificateServiceImpl implements EmployeeSkillCertificateService {

    private final Logger log = LoggerFactory.getLogger(EmployeeSkillCertificateServiceImpl.class);

    private final EmployeeSkillCertificateRepository employeeSkillCertificateRepository;

    private final EmployeeSkillCertificateMapper employeeSkillCertificateMapper;

    public EmployeeSkillCertificateServiceImpl(EmployeeSkillCertificateRepository employeeSkillCertificateRepository, EmployeeSkillCertificateMapper employeeSkillCertificateMapper) {
        this.employeeSkillCertificateRepository = employeeSkillCertificateRepository;
        this.employeeSkillCertificateMapper = employeeSkillCertificateMapper;
    }

    /**
     * Save a employeeSkillCertificate.
     *
     * @param employeeSkillCertificateDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public EmployeeSkillCertificateDTO save(EmployeeSkillCertificateDTO employeeSkillCertificateDTO) {
        log.debug("Request to save EmployeeSkillCertificate : {}", employeeSkillCertificateDTO);
        EmployeeSkillCertificate employeeSkillCertificate = employeeSkillCertificateMapper.toEntity(employeeSkillCertificateDTO);
        employeeSkillCertificate = employeeSkillCertificateRepository.save(employeeSkillCertificate);
        return employeeSkillCertificateMapper.toDto(employeeSkillCertificate);
    }

    /**
     * Get all the employeeSkillCertificates.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<EmployeeSkillCertificateDTO> findAll() {
        log.debug("Request to get all EmployeeSkillCertificates");
        return employeeSkillCertificateRepository.findAll().stream()
            .map(employeeSkillCertificateMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one employeeSkillCertificate by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<EmployeeSkillCertificateDTO> findOne(EmployeeSkillCertificateId id) {
        log.debug("Request to get EmployeeSkillCertificate : {}", id);
        return employeeSkillCertificateRepository.findById(id)
            .map(employeeSkillCertificateMapper::toDto);
    }

    /**
     * Delete the employeeSkillCertificate by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(EmployeeSkillCertificateId id) {
        log.debug("Request to delete EmployeeSkillCertificate : {}", id);
        employeeSkillCertificateRepository.deleteById(id);
    }
}
