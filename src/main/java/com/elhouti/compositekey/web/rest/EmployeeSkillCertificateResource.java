package com.elhouti.compositekey.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.elhouti.compositekey.service.EmployeeSkillCertificateService;
import com.elhouti.compositekey.web.rest.errors.BadRequestAlertException;
import com.elhouti.compositekey.web.rest.util.HeaderUtil;
import com.elhouti.compositekey.domain.EmployeeSkillCertificateId;
import com.elhouti.compositekey.service.dto.EmployeeSkillCertificateDTO;
import com.elhouti.compositekey.service.dto.EmployeeSkillCertificateCriteria;
import com.elhouti.compositekey.service.EmployeeSkillCertificateQueryService;
import io.github.jhipster.web.util.ResponseUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Map;
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
        if(employeeSkillCertificateService.findOne(new EmployeeSkillCertificateId(employeeSkillCertificateDTO.getEmployeeSkillEmployeeId(),
            employeeSkillCertificateDTO.getEmployeeSkillName(), employeeSkillCertificateDTO.getCertificateTypeId())).isPresent()){
            throw Problem.builder()
                .withTitle(ENTITY_NAME+" creation failed")
                .withDetail("Conflict")
                .withStatus(Status.CONFLICT)
                .build();
        }
        EmployeeSkillCertificateDTO result = employeeSkillCertificateService.save(employeeSkillCertificateDTO);
        return ResponseEntity.created(new URI("/api/employee-skill-certificates/" + "/"+ result.getEmployeeSkillEmployeeId() +"/"+
            result.getEmployeeSkillName() +"/"+ result.getCertificateTypeId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, "/"+ result.getEmployeeSkillEmployeeId() +"/"+
                result.getEmployeeSkillName() +"/"+ result.getCertificateTypeId()))
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
        if(!employeeSkillCertificateService.findOne(new EmployeeSkillCertificateId(employeeSkillCertificateDTO.getEmployeeSkillEmployeeId(),
            employeeSkillCertificateDTO.getEmployeeSkillName(), employeeSkillCertificateDTO.getCertificateTypeId())).isPresent()){
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EmployeeSkillCertificateDTO result = employeeSkillCertificateService.save(employeeSkillCertificateDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, "/"+ result.getEmployeeSkillEmployeeId() +"/"+
                result.getEmployeeSkillName() +"/"+ result.getCertificateTypeId()))
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
    public ResponseEntity<EmployeeSkillCertificateDTO> getEmployeeSkillCertificate(@MatrixVariable(pathVar = "id") Map<String, String> id) {
        final ObjectMapper mapper = new ObjectMapper(); // jackson's objectmapper
        final EmployeeSkillCertificateId employeeSkillCertificateId = mapper.convertValue(id, EmployeeSkillCertificateId.class);
        log.debug("REST request to get employeeSkillCertificate : {}", id);
        Optional<EmployeeSkillCertificateDTO> employeeSkillCertificateDTO = employeeSkillCertificateService.findOne(employeeSkillCertificateId);
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
    public ResponseEntity<Void> deleteEmployeeSkillCertificate(@MatrixVariable(pathVar = "id") Map<String, String> id) {
        final ObjectMapper mapper = new ObjectMapper(); // jackson's objectmapper
        final EmployeeSkillCertificateId employeeSkillCertificateId = mapper.convertValue(id, EmployeeSkillCertificateId.class);
        log.debug("REST request to delete EmployeeSkillCertificate : {}", id);
        employeeSkillCertificateService.delete(employeeSkillCertificateId);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
