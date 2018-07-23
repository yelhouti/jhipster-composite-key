package com.elhouti.compositekey.service;

import com.elhouti.compositekey.service.dto.CertificateTypeDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing CertificateType.
 */
public interface CertificateTypeService {

    /**
     * Save a certificateType.
     *
     * @param certificateTypeDTO the entity to save
     * @return the persisted entity
     */
    CertificateTypeDTO save(CertificateTypeDTO certificateTypeDTO);

    /**
     * Get all the certificateTypes.
     *
     * @return the list of entities
     */
    List<CertificateTypeDTO> findAll();


    /**
     * Get the "id" certificateType.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<CertificateTypeDTO> findOne(Long id);

    /**
     * Delete the "id" certificateType.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
