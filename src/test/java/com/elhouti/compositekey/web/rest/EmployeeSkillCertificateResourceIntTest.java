package com.elhouti.compositekey.web.rest;

import com.elhouti.compositekey.CompositekeyApp;

import com.elhouti.compositekey.domain.*;
import com.elhouti.compositekey.repository.EmployeeSkillCertificateRepository;
import com.elhouti.compositekey.service.EmployeeSkillCertificateService;
import com.elhouti.compositekey.service.dto.EmployeeSkillCertificateDTO;
import com.elhouti.compositekey.service.mapper.EmployeeSkillCertificateMapper;
import com.elhouti.compositekey.web.rest.errors.ExceptionTranslator;
import com.elhouti.compositekey.service.dto.EmployeeSkillCertificateCriteria;
import com.elhouti.compositekey.service.EmployeeSkillCertificateQueryService;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;


import static com.elhouti.compositekey.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the EmployeeSkillCertificateResource REST controller.
 *
 * @see EmployeeSkillCertificateResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CompositekeyApp.class)
public class EmployeeSkillCertificateResourceIntTest {

    private static final EmployeeSkillId DEFAULT_EMPLOYEE_SKILL_ID = new EmployeeSkillId("AAAAAAAAA", "AAAAAAAAA");
    private static final EmployeeSkillId OTHER_EMPLOYEE_SKILL_ID = new EmployeeSkillId("BBBBBBBBB", "BBBBBBBBB");

    private static final EmployeeSkillCertificateId DEFAULT_EMPLOYEE_SKILL_CERTIFICATE_ID =
        new EmployeeSkillCertificateId(DEFAULT_EMPLOYEE_SKILL_ID.getEmployeeId(), DEFAULT_EMPLOYEE_SKILL_ID.getName(),
            1L);
    private static final EmployeeSkillCertificateId OTHER_EMPLOYEE_SKILL_CERTIFICATE_ID =
        new EmployeeSkillCertificateId(OTHER_EMPLOYEE_SKILL_ID.getEmployeeId(), OTHER_EMPLOYEE_SKILL_ID.getName(),
            2L);

    private static final Integer DEFAULT_GRADE = 1;
    private static final Integer UPDATED_GRADE = 2;

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private EmployeeSkillCertificateRepository employeeSkillCertificateRepository;


    @Autowired
    private EmployeeSkillCertificateMapper employeeSkillCertificateMapper;


    @Autowired
    private EmployeeSkillCertificateService employeeSkillCertificateService;

    @Autowired
    private EmployeeSkillCertificateQueryService employeeSkillCertificateQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restEmployeeSkillCertificateMockMvc;

    private EmployeeSkillCertificate employeeSkillCertificate;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EmployeeSkillCertificateResource employeeSkillCertificateResource = new EmployeeSkillCertificateResource(employeeSkillCertificateService, employeeSkillCertificateQueryService);
        this.restEmployeeSkillCertificateMockMvc = MockMvcBuilders.standaloneSetup(employeeSkillCertificateResource)
            .setRemoveSemicolonContent(false)
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
    public static EmployeeSkillCertificate createEntity(EntityManager em) {
        EmployeeSkillCertificate employeeSkillCertificate = new EmployeeSkillCertificate()
            .grade(DEFAULT_GRADE)
            .date(DEFAULT_DATE);
        // Add required entity
        CertificateType certificateType = CertificateTypeResourceIntTest.createEntity(em);
        em.persist(certificateType);
        em.flush();
        employeeSkillCertificate.setCertificateType(certificateType);
        // Add required entity
        EmployeeSkill employeeSkill = EmployeeSkillResourceIntTest.createEntity(em);
        em.persist(employeeSkill);
        em.flush();
        employeeSkillCertificate.setEmployeeSkill(employeeSkill);
        employeeSkillCertificate.setId(new EmployeeSkillCertificateId(employeeSkill.getId().getEmployeeId(),
            employeeSkill.getId().getName(),certificateType.getId()));
        return employeeSkillCertificate;
    }

    @Before
    public void initTest() {
        employeeSkillCertificate = createEntity(em);
    }

    @Test
    @Transactional
    public void createEmployeeSkillCertificate() throws Exception {
        int databaseSizeBeforeCreate = employeeSkillCertificateRepository.findAll().size();
        // Create the EmployeeSkillCertificate
        EmployeeSkillCertificateDTO employeeSkillCertificateDTO = employeeSkillCertificateMapper.toDto(employeeSkillCertificate);
        restEmployeeSkillCertificateMockMvc.perform(post("/api/employee-skill-certificates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(employeeSkillCertificateDTO)))
            .andExpect(status().isCreated());

        // Validate the EmployeeSkillCertificate in the database
        List<EmployeeSkillCertificate> employeeSkillCertificateList = employeeSkillCertificateRepository.findAll();
        assertThat(employeeSkillCertificateList).hasSize(databaseSizeBeforeCreate + 1);
        EmployeeSkillCertificate testEmployeeSkillCertificate = employeeSkillCertificateList.get(employeeSkillCertificateList.size() - 1);
        assertThat(testEmployeeSkillCertificate.getGrade()).isEqualTo(DEFAULT_GRADE);
        assertThat(testEmployeeSkillCertificate.getDate()).isEqualTo(DEFAULT_DATE);
    }

    @Test
    @Transactional
    public void createEmployeeSkillCertificateWithExistingId() throws Exception {
        employeeSkillCertificateRepository.saveAndFlush(employeeSkillCertificate);
        int databaseSizeBeforeCreate = employeeSkillCertificateRepository.findAll().size();

        // Create the EmployeeSkillCertificate with an existing ID
        EmployeeSkillCertificateDTO employeeSkillCertificateDTO = employeeSkillCertificateMapper.toDto(employeeSkillCertificate);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmployeeSkillCertificateMockMvc.perform(post("/api/employee-skill-certificates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(employeeSkillCertificateDTO)))
            .andExpect(status().isConflict());

        // Validate the EmployeeSkillCertificate in the database
        List<EmployeeSkillCertificate> employeeSkillCertificateList = employeeSkillCertificateRepository.findAll();
        assertThat(employeeSkillCertificateList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkGradeIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeSkillCertificateRepository.findAll().size();
        // set the field null
        employeeSkillCertificate.setGrade(null);

        // Create the EmployeeSkillCertificate, which fails.
        EmployeeSkillCertificateDTO employeeSkillCertificateDTO = employeeSkillCertificateMapper.toDto(employeeSkillCertificate);

        restEmployeeSkillCertificateMockMvc.perform(post("/api/employee-skill-certificates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(employeeSkillCertificateDTO)))
            .andExpect(status().isBadRequest());

        List<EmployeeSkillCertificate> employeeSkillCertificateList = employeeSkillCertificateRepository.findAll();
        assertThat(employeeSkillCertificateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeSkillCertificateRepository.findAll().size();
        // set the field null
        employeeSkillCertificate.setDate(null);

        // Create the EmployeeSkillCertificate, which fails.
        EmployeeSkillCertificateDTO employeeSkillCertificateDTO = employeeSkillCertificateMapper.toDto(employeeSkillCertificate);

        restEmployeeSkillCertificateMockMvc.perform(post("/api/employee-skill-certificates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(employeeSkillCertificateDTO)))
            .andExpect(status().isBadRequest());

        List<EmployeeSkillCertificate> employeeSkillCertificateList = employeeSkillCertificateRepository.findAll();
        assertThat(employeeSkillCertificateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEmployeeSkillCertificates() throws Exception {
        // Initialize the database
        employeeSkillCertificateRepository.saveAndFlush(employeeSkillCertificate);

        // Get all the employeeSkillCertificateList
        restEmployeeSkillCertificateMockMvc.perform(get("/api/employee-skill-certificates?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].employeeSkillEmployeeId").value(hasItem(employeeSkillCertificate.getId()
                .getEmployeeSkillEmployeeId())))
            .andExpect(jsonPath("$.[*].employeeSkillName").value(hasItem(employeeSkillCertificate.getId()
                .getEmployeeSkillName())))
            .andExpect(jsonPath("$.[*].certificateTypeId").value(hasItem(employeeSkillCertificate.getId()
                .getCertificateTypeId().intValue())))
            .andExpect(jsonPath("$.[*].grade").value(hasItem(DEFAULT_GRADE)))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())));
    }


    @Test
    @Transactional
    public void getEmployeeSkillCertificate() throws Exception {
        // Initialize the database
        employeeSkillCertificateRepository.saveAndFlush(employeeSkillCertificate);

        // Get the employeeSkillCertificate
        restEmployeeSkillCertificateMockMvc.perform(get("/api/employee-skill-certificates/{id}", employeeSkillCertificate.getId().toMatrixVariableString()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.employeeSkillEmployeeId").value(employeeSkillCertificate.getId()
                .getEmployeeSkillEmployeeId()))
            .andExpect(jsonPath("$.employeeSkillName").value(employeeSkillCertificate.getId()
                .getEmployeeSkillName()))
            .andExpect(jsonPath("$.certificateTypeId").value(employeeSkillCertificate.getId()
                .getCertificateTypeId().intValue()))
            .andExpect(jsonPath("$.grade").value(DEFAULT_GRADE))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()));
    }

    @Test
    @Transactional
    public void getAllEmployeeSkillCertificatesByGradeIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeSkillCertificateRepository.saveAndFlush(employeeSkillCertificate);

        // Get all the employeeSkillCertificateList where grade equals to DEFAULT_GRADE
        defaultEmployeeSkillCertificateShouldBeFound("grade.equals=" + DEFAULT_GRADE);

        // Get all the employeeSkillCertificateList where grade equals to UPDATED_GRADE
        defaultEmployeeSkillCertificateShouldNotBeFound("grade.equals=" + UPDATED_GRADE);
    }

    @Test
    @Transactional
    public void getAllEmployeeSkillCertificatesByGradeIsInShouldWork() throws Exception {
        // Initialize the database
        employeeSkillCertificateRepository.saveAndFlush(employeeSkillCertificate);

        // Get all the employeeSkillCertificateList where grade in DEFAULT_GRADE or UPDATED_GRADE
        defaultEmployeeSkillCertificateShouldBeFound("grade.in=" + DEFAULT_GRADE + "," + UPDATED_GRADE);

        // Get all the employeeSkillCertificateList where grade equals to UPDATED_GRADE
        defaultEmployeeSkillCertificateShouldNotBeFound("grade.in=" + UPDATED_GRADE);
    }

    @Test
    @Transactional
    public void getAllEmployeeSkillCertificatesByGradeIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeSkillCertificateRepository.saveAndFlush(employeeSkillCertificate);

        // Get all the employeeSkillCertificateList where grade is not null
        defaultEmployeeSkillCertificateShouldBeFound("grade.specified=true");

        // Get all the employeeSkillCertificateList where grade is null
        defaultEmployeeSkillCertificateShouldNotBeFound("grade.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmployeeSkillCertificatesByGradeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeSkillCertificateRepository.saveAndFlush(employeeSkillCertificate);

        // Get all the employeeSkillCertificateList where grade greater than or equals to DEFAULT_GRADE
        defaultEmployeeSkillCertificateShouldBeFound("grade.greaterOrEqualThan=" + DEFAULT_GRADE);

        // Get all the employeeSkillCertificateList where grade greater than or equals to UPDATED_GRADE
        defaultEmployeeSkillCertificateShouldNotBeFound("grade.greaterOrEqualThan=" + UPDATED_GRADE);
    }

    @Test
    @Transactional
    public void getAllEmployeeSkillCertificatesByGradeIsLessThanSomething() throws Exception {
        // Initialize the database
        employeeSkillCertificateRepository.saveAndFlush(employeeSkillCertificate);

        // Get all the employeeSkillCertificateList where grade less than or equals to DEFAULT_GRADE
        defaultEmployeeSkillCertificateShouldNotBeFound("grade.lessThan=" + DEFAULT_GRADE);

        // Get all the employeeSkillCertificateList where grade less than or equals to UPDATED_GRADE
        defaultEmployeeSkillCertificateShouldBeFound("grade.lessThan=" + UPDATED_GRADE);
    }


    @Test
    @Transactional
    public void getAllEmployeeSkillCertificatesByDateIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeSkillCertificateRepository.saveAndFlush(employeeSkillCertificate);

        // Get all the employeeSkillCertificateList where date equals to DEFAULT_DATE
        defaultEmployeeSkillCertificateShouldBeFound("date.equals=" + DEFAULT_DATE);

        // Get all the employeeSkillCertificateList where date equals to UPDATED_DATE
        defaultEmployeeSkillCertificateShouldNotBeFound("date.equals=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllEmployeeSkillCertificatesByDateIsInShouldWork() throws Exception {
        // Initialize the database
        employeeSkillCertificateRepository.saveAndFlush(employeeSkillCertificate);

        // Get all the employeeSkillCertificateList where date in DEFAULT_DATE or UPDATED_DATE
        defaultEmployeeSkillCertificateShouldBeFound("date.in=" + DEFAULT_DATE + "," + UPDATED_DATE);

        // Get all the employeeSkillCertificateList where date equals to UPDATED_DATE
        defaultEmployeeSkillCertificateShouldNotBeFound("date.in=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllEmployeeSkillCertificatesByDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeSkillCertificateRepository.saveAndFlush(employeeSkillCertificate);

        // Get all the employeeSkillCertificateList where date is not null
        defaultEmployeeSkillCertificateShouldBeFound("date.specified=true");

        // Get all the employeeSkillCertificateList where date is null
        defaultEmployeeSkillCertificateShouldNotBeFound("date.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmployeeSkillCertificatesByDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeSkillCertificateRepository.saveAndFlush(employeeSkillCertificate);

        // Get all the employeeSkillCertificateList where date greater than or equals to DEFAULT_DATE
        defaultEmployeeSkillCertificateShouldBeFound("date.greaterOrEqualThan=" + DEFAULT_DATE);

        // Get all the employeeSkillCertificateList where date greater than or equals to UPDATED_DATE
        defaultEmployeeSkillCertificateShouldNotBeFound("date.greaterOrEqualThan=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllEmployeeSkillCertificatesByDateIsLessThanSomething() throws Exception {
        // Initialize the database
        employeeSkillCertificateRepository.saveAndFlush(employeeSkillCertificate);

        // Get all the employeeSkillCertificateList where date less than or equals to DEFAULT_DATE
        defaultEmployeeSkillCertificateShouldNotBeFound("date.lessThan=" + DEFAULT_DATE);

        // Get all the employeeSkillCertificateList where date less than or equals to UPDATED_DATE
        defaultEmployeeSkillCertificateShouldBeFound("date.lessThan=" + UPDATED_DATE);
    }


    @Test
    @Transactional
    public void getAllEmployeeSkillCertificatesByCertificateTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeSkillCertificateRepository.saveAndFlush(employeeSkillCertificate);
        Long certificateTypeId = employeeSkillCertificate.getCertificateType().getId();

        // Get all the employeeSkillCertificateList where certificateType equals to certificateTypeId
        defaultEmployeeSkillCertificateShouldBeFound("certificateTypeId.equals=" + certificateTypeId);

        // Get all the employeeSkillCertificateList where certificateType equals to certificateTypeId + 1
        defaultEmployeeSkillCertificateShouldNotBeFound("certificateTypeId.equals=" + (certificateTypeId + 1L));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultEmployeeSkillCertificateShouldBeFound(String filter) throws Exception {
        restEmployeeSkillCertificateMockMvc.perform(get("/api/employee-skill-certificates?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].employeeSkillEmployeeId").value(hasItem(employeeSkillCertificate.getId()
                .getEmployeeSkillEmployeeId())))
            .andExpect(jsonPath("$.[*].employeeSkillName").value(hasItem(employeeSkillCertificate.getId()
                .getEmployeeSkillName())))
            .andExpect(jsonPath("$.[*].certificateTypeId").value(hasItem(employeeSkillCertificate.getId()
                .getCertificateTypeId().intValue())))
            .andExpect(jsonPath("$.[*].grade").value(hasItem(employeeSkillCertificate.getGrade())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(employeeSkillCertificate.getDate().toString())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultEmployeeSkillCertificateShouldNotBeFound(String filter) throws Exception {
        restEmployeeSkillCertificateMockMvc.perform(get("/api/employee-skill-certificates?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    @Transactional
    public void getNonExistingEmployeeSkillCertificate() throws Exception {
        // Get the employeeSkillCertificate
        restEmployeeSkillCertificateMockMvc.perform(get("/api/employee-skill-certificates/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEmployeeSkillCertificate() throws Exception {
        // Initialize the database
        employeeSkillCertificateRepository.saveAndFlush(employeeSkillCertificate);

        int databaseSizeBeforeUpdate = employeeSkillCertificateRepository.findAll().size();

        // Update the employeeSkillCertificate
        EmployeeSkillCertificate updatedEmployeeSkillCertificate = employeeSkillCertificateRepository.findById(employeeSkillCertificate.getId()).get();
        // Disconnect from session so that the updates on updatedEmployeeSkillCertificate are not directly saved in db
        em.detach(updatedEmployeeSkillCertificate);
        updatedEmployeeSkillCertificate
            .grade(UPDATED_GRADE)
            .date(UPDATED_DATE);
        EmployeeSkillCertificateDTO employeeSkillCertificateDTO = employeeSkillCertificateMapper.toDto(updatedEmployeeSkillCertificate);

        restEmployeeSkillCertificateMockMvc.perform(put("/api/employee-skill-certificates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(employeeSkillCertificateDTO)))
            .andExpect(status().isOk());

        // Validate the EmployeeSkillCertificate in the database
        List<EmployeeSkillCertificate> employeeSkillCertificateList = employeeSkillCertificateRepository.findAll();
        assertThat(employeeSkillCertificateList).hasSize(databaseSizeBeforeUpdate);
        EmployeeSkillCertificate testEmployeeSkillCertificate = employeeSkillCertificateList.get(employeeSkillCertificateList.size() - 1);
        assertThat(testEmployeeSkillCertificate.getGrade()).isEqualTo(UPDATED_GRADE);
        assertThat(testEmployeeSkillCertificate.getDate()).isEqualTo(UPDATED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingEmployeeSkillCertificate() throws Exception {
        int databaseSizeBeforeUpdate = employeeSkillCertificateRepository.findAll().size();

        // Create the EmployeeSkillCertificate
        EmployeeSkillCertificateDTO employeeSkillCertificateDTO = employeeSkillCertificateMapper.toDto(employeeSkillCertificate);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restEmployeeSkillCertificateMockMvc.perform(put("/api/employee-skill-certificates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(employeeSkillCertificateDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EmployeeSkillCertificate in the database
        List<EmployeeSkillCertificate> employeeSkillCertificateList = employeeSkillCertificateRepository.findAll();
        assertThat(employeeSkillCertificateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEmployeeSkillCertificate() throws Exception {
        // Initialize the database
        employeeSkillCertificateRepository.saveAndFlush(employeeSkillCertificate);

        int databaseSizeBeforeDelete = employeeSkillCertificateRepository.findAll().size();

        // Get the employeeSkillCertificate
        restEmployeeSkillCertificateMockMvc.perform(delete("/api/employee-skill-certificates/{id}", employeeSkillCertificate.getId().toMatrixVariableString())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<EmployeeSkillCertificate> employeeSkillCertificateList = employeeSkillCertificateRepository.findAll();
        assertThat(employeeSkillCertificateList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmployeeSkillCertificate.class);
        EmployeeSkillCertificate employeeSkillCertificate1 = new EmployeeSkillCertificate();
        employeeSkillCertificate1.setId(DEFAULT_EMPLOYEE_SKILL_CERTIFICATE_ID);
        EmployeeSkillCertificate employeeSkillCertificate2 = new EmployeeSkillCertificate();
        employeeSkillCertificate2.setId(employeeSkillCertificate1.getId());
        assertThat(employeeSkillCertificate1).isEqualTo(employeeSkillCertificate2);
        employeeSkillCertificate2.setId(OTHER_EMPLOYEE_SKILL_CERTIFICATE_ID);
        assertThat(employeeSkillCertificate1).isNotEqualTo(employeeSkillCertificate2);
        employeeSkillCertificate1.setId(null);
        assertThat(employeeSkillCertificate1).isNotEqualTo(employeeSkillCertificate2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmployeeSkillCertificateDTO.class);
        EmployeeSkillCertificateDTO employeeSkillCertificateDTO1 = new EmployeeSkillCertificateDTO();
        employeeSkillCertificateDTO1.setEmployeeSkillEmployeeId(DEFAULT_EMPLOYEE_SKILL_CERTIFICATE_ID.getEmployeeSkillEmployeeId());
        employeeSkillCertificateDTO1.setEmployeeSkillName(DEFAULT_EMPLOYEE_SKILL_CERTIFICATE_ID.getEmployeeSkillName());
        employeeSkillCertificateDTO1.setCertificateTypeId(DEFAULT_EMPLOYEE_SKILL_CERTIFICATE_ID.getCertificateTypeId());
        EmployeeSkillCertificateDTO employeeSkillCertificateDTO2 = new EmployeeSkillCertificateDTO();
        employeeSkillCertificateDTO2.setEmployeeSkillEmployeeId(OTHER_EMPLOYEE_SKILL_CERTIFICATE_ID.getEmployeeSkillEmployeeId());
        employeeSkillCertificateDTO2.setEmployeeSkillName(OTHER_EMPLOYEE_SKILL_CERTIFICATE_ID.getEmployeeSkillName());
        employeeSkillCertificateDTO2.setCertificateTypeId(OTHER_EMPLOYEE_SKILL_CERTIFICATE_ID.getCertificateTypeId());
        assertThat(employeeSkillCertificateDTO1).isNotEqualTo(employeeSkillCertificateDTO2);
        employeeSkillCertificateDTO2.setEmployeeSkillEmployeeId(employeeSkillCertificateDTO1.getEmployeeSkillEmployeeId());
        employeeSkillCertificateDTO2.setEmployeeSkillName(employeeSkillCertificateDTO1.getEmployeeSkillName());
        employeeSkillCertificateDTO2.setCertificateTypeId(employeeSkillCertificateDTO1.getCertificateTypeId());
        assertThat(employeeSkillCertificateDTO1).isEqualTo(employeeSkillCertificateDTO2);
        employeeSkillCertificateDTO2.setEmployeeSkillEmployeeId(OTHER_EMPLOYEE_SKILL_CERTIFICATE_ID.getEmployeeSkillEmployeeId());
        assertThat(employeeSkillCertificateDTO1).isNotEqualTo(employeeSkillCertificateDTO2);
        employeeSkillCertificateDTO2.setEmployeeSkillEmployeeId(employeeSkillCertificateDTO1.getEmployeeSkillEmployeeId());
        employeeSkillCertificateDTO2.setEmployeeSkillName(OTHER_EMPLOYEE_SKILL_CERTIFICATE_ID.getEmployeeSkillName());
        assertThat(employeeSkillCertificateDTO1).isNotEqualTo(employeeSkillCertificateDTO2);
        employeeSkillCertificateDTO2.setEmployeeSkillName(employeeSkillCertificateDTO1.getEmployeeSkillName());
        employeeSkillCertificateDTO2.setCertificateTypeId(OTHER_EMPLOYEE_SKILL_CERTIFICATE_ID.getCertificateTypeId());
        assertThat(employeeSkillCertificateDTO1).isNotEqualTo(employeeSkillCertificateDTO2);
        employeeSkillCertificateDTO1.setCertificateTypeId(null);
        assertThat(employeeSkillCertificateDTO1).isNotEqualTo(employeeSkillCertificateDTO2);
        employeeSkillCertificateDTO1.setCertificateTypeId(employeeSkillCertificateDTO2.getCertificateTypeId());
        employeeSkillCertificateDTO1.setEmployeeSkillEmployeeId(null);
        assertThat(employeeSkillCertificateDTO1).isNotEqualTo(employeeSkillCertificateDTO2);
        employeeSkillCertificateDTO1.setEmployeeSkillName(null);
        assertThat(employeeSkillCertificateDTO1).isNotEqualTo(employeeSkillCertificateDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(employeeSkillCertificateMapper.fromId(DEFAULT_EMPLOYEE_SKILL_CERTIFICATE_ID).getId()).isEqualTo(DEFAULT_EMPLOYEE_SKILL_CERTIFICATE_ID);
        assertThat(employeeSkillCertificateMapper.fromId(null)).isNull();
    }
}
