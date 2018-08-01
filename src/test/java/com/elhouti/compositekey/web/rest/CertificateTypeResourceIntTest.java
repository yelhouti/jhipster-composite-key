package com.elhouti.compositekey.web.rest;

import com.elhouti.compositekey.CompositekeyApp;

import com.elhouti.compositekey.domain.CertificateType;
import com.elhouti.compositekey.domain.EmployeeSkillCertificate;
import com.elhouti.compositekey.repository.CertificateTypeRepository;
import com.elhouti.compositekey.service.CertificateTypeService;
import com.elhouti.compositekey.service.dto.CertificateTypeDTO;
import com.elhouti.compositekey.service.mapper.CertificateTypeMapper;
import com.elhouti.compositekey.web.rest.errors.ExceptionTranslator;
import com.elhouti.compositekey.service.dto.CertificateTypeCriteria;
import com.elhouti.compositekey.service.CertificateTypeQueryService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;


import static com.elhouti.compositekey.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the CertificateTypeResource REST controller.
 *
 * @see CertificateTypeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CompositekeyApp.class)
public class CertificateTypeResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private CertificateTypeRepository certificateTypeRepository;


    @Autowired
    private CertificateTypeMapper certificateTypeMapper;


    @Autowired
    private CertificateTypeService certificateTypeService;

    @Autowired
    private CertificateTypeQueryService certificateTypeQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCertificateTypeMockMvc;

    private CertificateType certificateType;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CertificateTypeResource certificateTypeResource = new CertificateTypeResource(certificateTypeService, certificateTypeQueryService);
        this.restCertificateTypeMockMvc = MockMvcBuilders.standaloneSetup(certificateTypeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CertificateType createEntity(EntityManager em) {
        CertificateType certificateType = new CertificateType()
            .name(DEFAULT_NAME);
        return certificateType;
    }

    @Before
    public void initTest() {
        certificateType = createEntity(em);
    }

    @Test
    @Transactional
    public void createCertificateType() throws Exception {
        int databaseSizeBeforeCreate = certificateTypeRepository.findAll().size();

        // Create the CertificateType
        CertificateTypeDTO certificateTypeDTO = certificateTypeMapper.toDto(certificateType);
        restCertificateTypeMockMvc.perform(post("/api/certificate-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(certificateTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the CertificateType in the database
        List<CertificateType> certificateTypeList = certificateTypeRepository.findAll();
        assertThat(certificateTypeList).hasSize(databaseSizeBeforeCreate + 1);
        CertificateType testCertificateType = certificateTypeList.get(certificateTypeList.size() - 1);
        assertThat(testCertificateType.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createCertificateTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = certificateTypeRepository.findAll().size();

        // Create the CertificateType with an existing ID
        certificateType.setId(1L);
        CertificateTypeDTO certificateTypeDTO = certificateTypeMapper.toDto(certificateType);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCertificateTypeMockMvc.perform(post("/api/certificate-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(certificateTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CertificateType in the database
        List<CertificateType> certificateTypeList = certificateTypeRepository.findAll();
        assertThat(certificateTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = certificateTypeRepository.findAll().size();
        // set the field null
        certificateType.setName(null);

        // Create the CertificateType, which fails.
        CertificateTypeDTO certificateTypeDTO = certificateTypeMapper.toDto(certificateType);

        restCertificateTypeMockMvc.perform(post("/api/certificate-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(certificateTypeDTO)))
            .andExpect(status().isBadRequest());

        List<CertificateType> certificateTypeList = certificateTypeRepository.findAll();
        assertThat(certificateTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCertificateTypes() throws Exception {
        // Initialize the database
        certificateTypeRepository.saveAndFlush(certificateType);

        // Get all the certificateTypeList
        restCertificateTypeMockMvc.perform(get("/api/certificate-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(certificateType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }


    @Test
    @Transactional
    public void getCertificateType() throws Exception {
        // Initialize the database
        certificateTypeRepository.saveAndFlush(certificateType);

        // Get the certificateType
        restCertificateTypeMockMvc.perform(get("/api/certificate-types/{id}", certificateType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(certificateType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getAllCertificateTypesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        certificateTypeRepository.saveAndFlush(certificateType);

        // Get all the certificateTypeList where name equals to DEFAULT_NAME
        defaultCertificateTypeShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the certificateTypeList where name equals to UPDATED_NAME
        defaultCertificateTypeShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCertificateTypesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        certificateTypeRepository.saveAndFlush(certificateType);

        // Get all the certificateTypeList where name in DEFAULT_NAME or UPDATED_NAME
        defaultCertificateTypeShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the certificateTypeList where name equals to UPDATED_NAME
        defaultCertificateTypeShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCertificateTypesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        certificateTypeRepository.saveAndFlush(certificateType);

        // Get all the certificateTypeList where name is not null
        defaultCertificateTypeShouldBeFound("name.specified=true");

        // Get all the certificateTypeList where name is null
        defaultCertificateTypeShouldNotBeFound("name.specified=false");
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultCertificateTypeShouldBeFound(String filter) throws Exception {
        restCertificateTypeMockMvc.perform(get("/api/certificate-types?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(certificateType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultCertificateTypeShouldNotBeFound(String filter) throws Exception {
        restCertificateTypeMockMvc.perform(get("/api/certificate-types?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    @Transactional
    public void getNonExistingCertificateType() throws Exception {
        // Get the certificateType
        restCertificateTypeMockMvc.perform(get("/api/certificate-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCertificateType() throws Exception {
        // Initialize the database
        certificateTypeRepository.saveAndFlush(certificateType);

        int databaseSizeBeforeUpdate = certificateTypeRepository.findAll().size();

        // Update the certificateType
        CertificateType updatedCertificateType = certificateTypeRepository.findById(certificateType.getId()).get();
        // Disconnect from session so that the updates on updatedCertificateType are not directly saved in db
        em.detach(updatedCertificateType);
        updatedCertificateType
            .name(UPDATED_NAME);
        CertificateTypeDTO certificateTypeDTO = certificateTypeMapper.toDto(updatedCertificateType);

        restCertificateTypeMockMvc.perform(put("/api/certificate-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(certificateTypeDTO)))
            .andExpect(status().isOk());

        // Validate the CertificateType in the database
        List<CertificateType> certificateTypeList = certificateTypeRepository.findAll();
        assertThat(certificateTypeList).hasSize(databaseSizeBeforeUpdate);
        CertificateType testCertificateType = certificateTypeList.get(certificateTypeList.size() - 1);
        assertThat(testCertificateType.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingCertificateType() throws Exception {
        int databaseSizeBeforeUpdate = certificateTypeRepository.findAll().size();

        // Create the CertificateType
        CertificateTypeDTO certificateTypeDTO = certificateTypeMapper.toDto(certificateType);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCertificateTypeMockMvc.perform(put("/api/certificate-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(certificateTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CertificateType in the database
        List<CertificateType> certificateTypeList = certificateTypeRepository.findAll();
        assertThat(certificateTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCertificateType() throws Exception {
        // Initialize the database
        certificateTypeRepository.saveAndFlush(certificateType);

        int databaseSizeBeforeDelete = certificateTypeRepository.findAll().size();

        // Get the certificateType
        restCertificateTypeMockMvc.perform(delete("/api/certificate-types/{id}", certificateType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CertificateType> certificateTypeList = certificateTypeRepository.findAll();
        assertThat(certificateTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CertificateType.class);
        CertificateType certificateType1 = new CertificateType();
        certificateType1.setId(1L);
        CertificateType certificateType2 = new CertificateType();
        certificateType2.setId(certificateType1.getId());
        assertThat(certificateType1).isEqualTo(certificateType2);
        certificateType2.setId(2L);
        assertThat(certificateType1).isNotEqualTo(certificateType2);
        certificateType1.setId(null);
        assertThat(certificateType1).isNotEqualTo(certificateType2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CertificateTypeDTO.class);
        CertificateTypeDTO certificateTypeDTO1 = new CertificateTypeDTO();
        certificateTypeDTO1.setId(1L);
        CertificateTypeDTO certificateTypeDTO2 = new CertificateTypeDTO();
        assertThat(certificateTypeDTO1).isNotEqualTo(certificateTypeDTO2);
        certificateTypeDTO2.setId(certificateTypeDTO1.getId());
        assertThat(certificateTypeDTO1).isEqualTo(certificateTypeDTO2);
        certificateTypeDTO2.setId(2L);
        assertThat(certificateTypeDTO1).isNotEqualTo(certificateTypeDTO2);
        certificateTypeDTO1.setId(null);
        assertThat(certificateTypeDTO1).isNotEqualTo(certificateTypeDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(certificateTypeMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(certificateTypeMapper.fromId(null)).isNull();
    }
}
