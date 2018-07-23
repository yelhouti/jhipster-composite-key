package com.elhouti.compositekey.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.elhouti.compositekey.service.EmployeeSkillCertificateService;
import com.elhouti.compositekey.web.rest.errors.BadRequestAlertException;
import com.elhouti.compositekey.web.rest.util.HeaderUtil;
import com.elhouti.compositekey.service.dto.EmployeeSkillCertificateDTO;
import com.elhouti.compositekey.service.dto.EmployeeSkillCertificateCriteria;
import com.elhouti.compositekey.service.EmployeeSkillCertificateQueryService;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing EmployeeSkillCertificate.
 */
@RestController
@RequestMapping("/api")
public class EmployeeSkillCertificateResource {

    private final Logger log = LoggerFactory.getLogger(EmployeeSkillCertificateResource.class);

    private static final String ENTITY_NAME = "employeeSkillCertificate";

    private final EmployeeSkillCertificateService employeeSkillCertificateService;

    private final EmployeeSkillCertificateQueryService employeeSkillCertificateQueryService;

    public EmployeeSkillCertificateResource(EmployeeSkillCertificateService employeeSkillCertificateService, EmployeeSkillCertificateQueryService employeeSkillCertificateQueryService) {
        this.employeeSkillCertificateService = employeeSkillCertificateService;
        this.employeeSkillCertificateQueryService = employeeSkillCertificateQueryService;
    }

    /**
     * POST  /employee-skill-certificates : Create a new employeeSkillCertificate.
     *
     * @param employeeSkillCertificateDTO the employeeSkillCertificateDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new employeeSkillCertificateDTO, or with status 400 (Bad Request) if the employeeSkillCertificate has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/employee-skill-certificates")
    @Timed
    public ResponseEntity<EmployeeSkillCertificateDTO> createEmployeeSkillCertificate(@Valid @RequestBody EmployeeSkillCertificateDTO employeeSkillCertificateDTO) throws URISyntaxException {
        log.debug("REST request to save EmployeeSkillCertificate : {}", employeeSkillCertificateDTO);
        if (employeeSkillCertificateDTO.getId() != null) {
            throw new BadRequestAlertException("A new employeeSkillCertificate cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EmployeeSkillCertificateDTO result = employeeSkillCertificateService.save(employeeSkillCertificateDTO);
        return ResponseEntity.created(new URI("/api/employee-skill-certificates/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /employee-skill-certificates : Updates an existing employeeSkillCertificate.
     *
     * @param employeeSkillCertificateDTO the employeeSkillCertificateDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated employeeSkillCertificateDTO,
     * or with status 400 (Bad Request) if the employeeSkillCertificateDTO is not valid,
     * or with status 500 (Internal Server Error) if the employeeSkillCertificateDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/employee-skill-certificates")
    @Timed
    public ResponseEntity<EmployeeSkillCertificateDTO> updateEmployeeSkillCertificate(@Valid @RequestBody EmployeeSkillCertificateDTO employeeSkillCertificateDTO) throws URISyntaxException {
        log.debug("REST request to update EmployeeSkillCertificate : {}", employeeSkillCertificateDTO);
        if (employeeSkillCertificateDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EmployeeSkillCertificateDTO result = employeeSkillCertificateService.save(employeeSkillCertificateDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, employeeSkillCertificateDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /employee-skill-certificates : get all the employeeSkillCertificates.
     *
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of employeeSkillCertificates in body
     */
    @GetMapping("/employee-skill-certificates")
    @Timed
    public ResponseEntity<List<EmployeeSkillCertificateDTO>> getAllEmployeeSkillCertificates(EmployeeSkillCertificateCriteria criteria) {
        log.debug("REST request to get EmployeeSkillCertificates by criteria: {}", criteria);
        List<EmployeeSkillCertificateDTO> entityList = employeeSkillCertificateQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * GET  /employee-skill-certificates/:id : get the "id" employeeSkillCertificate.
     *
     * @param id the id of the employeeSkillCertificateDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the employeeSkillCertificateDTO, or with status 404 (Not Found)
     */
    @GetMapping("/employee-skill-certificates/{id}")
    @Timed
    public ResponseEntity<EmployeeSkillCertificateDTO> getEmployeeSkillCertificate(@PathVariable Long id) {
        log.debug("REST request to get EmployeeSkillCertificate : {}", id);
        Optional<EmployeeSkillCertificateDTO> employeeSkillCertificateDTO = employeeSkillCertificateService.findOne(id);
        return ResponseUtil.wrapOrNotFound(employeeSkillCertificateDTO);
    }

    /**
     * DELETE  /employee-skill-certificates/:id : delete the "id" employeeSkillCertificate.
     *
     * @param id the id of the employeeSkillCertificateDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/employee-skill-certificates/{id}")
    @Timed
    public ResponseEntity<Void> deleteEmployeeSkillCertificate(@PathVariable Long id) {
        log.debug("REST request to delete EmployeeSkillCertificate : {}", id);
        employeeSkillCertificateService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
