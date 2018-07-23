package com.elhouti.compositekey.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.elhouti.compositekey.service.CertificateTypeService;
import com.elhouti.compositekey.web.rest.errors.BadRequestAlertException;
import com.elhouti.compositekey.web.rest.util.HeaderUtil;
import com.elhouti.compositekey.service.dto.CertificateTypeDTO;
import com.elhouti.compositekey.service.dto.CertificateTypeCriteria;
import com.elhouti.compositekey.service.CertificateTypeQueryService;
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
 * REST controller for managing CertificateType.
 */
@RestController
@RequestMapping("/api")
public class CertificateTypeResource {

    private final Logger log = LoggerFactory.getLogger(CertificateTypeResource.class);

    private static final String ENTITY_NAME = "certificateType";

    private final CertificateTypeService certificateTypeService;

    private final CertificateTypeQueryService certificateTypeQueryService;

    public CertificateTypeResource(CertificateTypeService certificateTypeService, CertificateTypeQueryService certificateTypeQueryService) {
        this.certificateTypeService = certificateTypeService;
        this.certificateTypeQueryService = certificateTypeQueryService;
    }

    /**
     * POST  /certificate-types : Create a new certificateType.
     *
     * @param certificateTypeDTO the certificateTypeDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new certificateTypeDTO, or with status 400 (Bad Request) if the certificateType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/certificate-types")
    @Timed
    public ResponseEntity<CertificateTypeDTO> createCertificateType(@Valid @RequestBody CertificateTypeDTO certificateTypeDTO) throws URISyntaxException {
        log.debug("REST request to save CertificateType : {}", certificateTypeDTO);
        if (certificateTypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new certificateType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CertificateTypeDTO result = certificateTypeService.save(certificateTypeDTO);
        return ResponseEntity.created(new URI("/api/certificate-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /certificate-types : Updates an existing certificateType.
     *
     * @param certificateTypeDTO the certificateTypeDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated certificateTypeDTO,
     * or with status 400 (Bad Request) if the certificateTypeDTO is not valid,
     * or with status 500 (Internal Server Error) if the certificateTypeDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/certificate-types")
    @Timed
    public ResponseEntity<CertificateTypeDTO> updateCertificateType(@Valid @RequestBody CertificateTypeDTO certificateTypeDTO) throws URISyntaxException {
        log.debug("REST request to update CertificateType : {}", certificateTypeDTO);
        if (certificateTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CertificateTypeDTO result = certificateTypeService.save(certificateTypeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, certificateTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /certificate-types : get all the certificateTypes.
     *
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of certificateTypes in body
     */
    @GetMapping("/certificate-types")
    @Timed
    public ResponseEntity<List<CertificateTypeDTO>> getAllCertificateTypes(CertificateTypeCriteria criteria) {
        log.debug("REST request to get CertificateTypes by criteria: {}", criteria);
        List<CertificateTypeDTO> entityList = certificateTypeQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * GET  /certificate-types/:id : get the "id" certificateType.
     *
     * @param id the id of the certificateTypeDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the certificateTypeDTO, or with status 404 (Not Found)
     */
    @GetMapping("/certificate-types/{id}")
    @Timed
    public ResponseEntity<CertificateTypeDTO> getCertificateType(@PathVariable Long id) {
        log.debug("REST request to get CertificateType : {}", id);
        Optional<CertificateTypeDTO> certificateTypeDTO = certificateTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(certificateTypeDTO);
    }

    /**
     * DELETE  /certificate-types/:id : delete the "id" certificateType.
     *
     * @param id the id of the certificateTypeDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/certificate-types/{id}")
    @Timed
    public ResponseEntity<Void> deleteCertificateType(@PathVariable Long id) {
        log.debug("REST request to delete CertificateType : {}", id);
        certificateTypeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
