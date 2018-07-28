package com.elhouti.compositekey.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.elhouti.compositekey.service.EmployeeSkillService;
import com.elhouti.compositekey.web.rest.errors.BadRequestAlertException;
import com.elhouti.compositekey.web.rest.util.HeaderUtil;
import com.elhouti.compositekey.service.dto.EmployeeSkillDTO;
import com.elhouti.compositekey.service.dto.EmployeeSkillCriteria;
import com.elhouti.compositekey.service.EmployeeSkillQueryService;
import com.elhouti.compositekey.domain.EmployeeSkillId;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * REST controller for managing EmployeeSkill.
 */
@RestController
@RequestMapping("/api")
public class EmployeeSkillResource {

    private final Logger log = LoggerFactory.getLogger(EmployeeSkillResource.class);

    private static final String ENTITY_NAME = "employeeSkill";

    private final EmployeeSkillService employeeSkillService;

    private final EmployeeSkillQueryService employeeSkillQueryService;

    public EmployeeSkillResource(EmployeeSkillService employeeSkillService, EmployeeSkillQueryService employeeSkillQueryService) {
        this.employeeSkillService = employeeSkillService;
        this.employeeSkillQueryService = employeeSkillQueryService;
    }

    /**
     * POST  /employee-skills : Create a new employeeSkill.
     *
     * @param employeeSkillDTO the employeeSkillDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new employeeSkillDTO, or with status 400 (Bad Request) if the employeeSkill has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/employee-skills")
    @Timed
    public ResponseEntity<EmployeeSkillDTO> createEmployeeSkill(@Valid @RequestBody EmployeeSkillDTO employeeSkillDTO) throws URISyntaxException {
        log.debug("REST request to save EmployeeSkill : {}", employeeSkillDTO);
        if(employeeSkillService.findOne(new EmployeeSkillId(employeeSkillDTO.getEmployeeId(), employeeSkillDTO.getName())).isPresent()){
            throw Problem.builder()
                .withTitle(ENTITY_NAME+" creation failed")
                .withDetail("Conflict")
                .withStatus(Status.CONFLICT)
                .build();
        }
        EmployeeSkillDTO result = employeeSkillService.save(employeeSkillDTO);
        return ResponseEntity.created(new URI("/api/employee-skills/" + "/"+ result.getEmployeeId() +"/"+ result.getName()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, "/"+ result.getEmployeeId() +"/"+ result.getName()))
            .body(result);
    }

    /**
     * PUT  /employee-skills : Updates an existing employeeSkill.
     *
     * @param employeeSkillDTO the employeeSkillDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated employeeSkillDTO,
     * or with status 400 (Bad Request) if the employeeSkillDTO is not valid,
     * or with status 500 (Internal Server Error) if the employeeSkillDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/employee-skills")
    @Timed
    public ResponseEntity<EmployeeSkillDTO> updateEmployeeSkill(@Valid @RequestBody EmployeeSkillDTO employeeSkillDTO) throws URISyntaxException {
        log.debug("REST request to update EmployeeSkill : {}", employeeSkillDTO);
        if(!employeeSkillService.findOne(new EmployeeSkillId(employeeSkillDTO.getEmployeeId(), employeeSkillDTO.getName())).isPresent()){
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EmployeeSkillDTO result = employeeSkillService.save(employeeSkillDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, "/"+ result.getEmployeeId() +"/"+ result.getName()))
            .body(result);
    }

    /**
     * GET  /employee-skills : get all the employeeSkills.
     *
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of employeeSkills in body
     */
    @GetMapping("/employee-skills")
    @Timed
    public ResponseEntity<List<EmployeeSkillDTO>> getAllEmployeeSkills(EmployeeSkillCriteria criteria) {
        log.debug("REST request to get EmployeeSkills by criteria: {}", criteria);
        List<EmployeeSkillDTO> entityList = employeeSkillQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * GET  /employee-skills/:id : get the "id" employeeSkill.
     *
     * @param id the id of the employeeSkillDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the employeeSkillDTO, or with status 404 (Not Found)
     */
    @GetMapping("/employee-skills/{id}")
    @Timed
    public ResponseEntity<EmployeeSkillDTO> getEmployeeSkill(@MatrixVariable(pathVar = "id") Map<String, String> id) {
        final ObjectMapper mapper = new ObjectMapper(); // jackson's objectmapper
        final EmployeeSkillId employeeSkillId = mapper.convertValue(id, EmployeeSkillId.class);
        log.debug("REST request to get employeeSkill : {}", id);
        Optional<EmployeeSkillDTO> employeeSkillDTO = employeeSkillService.findOne(employeeSkillId);
        return ResponseUtil.wrapOrNotFound(employeeSkillDTO);
    }

    /**
     * DELETE  /employee-skills/:id : delete the "id" employeeSkill.
     *
     * @param id the id of the employeeSkillDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/employee-skills/{id}")
    @Timed
    public ResponseEntity<Void> deleteEmployeeSkill(@MatrixVariable(pathVar = "id") Map<String, String> id) {
        final ObjectMapper mapper = new ObjectMapper(); // jackson's objectmapper
        final EmployeeSkillId employeeSkillId = mapper.convertValue(id, EmployeeSkillId.class);
        log.debug("REST request to delete EmployeeSkill : {}", id);
        employeeSkillService.delete(employeeSkillId);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
