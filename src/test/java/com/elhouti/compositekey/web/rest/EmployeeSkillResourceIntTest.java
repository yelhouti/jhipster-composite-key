package com.elhouti.compositekey.web.rest;

import com.elhouti.compositekey.CompositekeyApp;

import com.elhouti.compositekey.domain.EmployeeSkill;
import com.elhouti.compositekey.domain.EmployeeSkillCertificate;
import com.elhouti.compositekey.domain.Employee;
import com.elhouti.compositekey.repository.EmployeeSkillRepository;
import com.elhouti.compositekey.service.EmployeeSkillService;
import com.elhouti.compositekey.service.dto.EmployeeSkillDTO;
import com.elhouti.compositekey.service.mapper.EmployeeSkillMapper;
import com.elhouti.compositekey.web.rest.errors.ExceptionTranslator;
import com.elhouti.compositekey.service.dto.EmployeeSkillCriteria;
import com.elhouti.compositekey.service.EmployeeSkillQueryService;

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
 * Test class for the EmployeeSkillResource REST controller.
 *
 * @see EmployeeSkillResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CompositekeyApp.class)
public class EmployeeSkillResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_LEVEL = 1;
    private static final Integer UPDATED_LEVEL = 2;

    @Autowired
    private EmployeeSkillRepository employeeSkillRepository;


    @Autowired
    private EmployeeSkillMapper employeeSkillMapper;


    @Autowired
    private EmployeeSkillService employeeSkillService;

    @Autowired
    private EmployeeSkillQueryService employeeSkillQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restEmployeeSkillMockMvc;

    private EmployeeSkill employeeSkill;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EmployeeSkillResource employeeSkillResource = new EmployeeSkillResource(employeeSkillService, employeeSkillQueryService);
        this.restEmployeeSkillMockMvc = MockMvcBuilders.standaloneSetup(employeeSkillResource)
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
    public static EmployeeSkill createEntity(EntityManager em) {
        EmployeeSkill employeeSkill = new EmployeeSkill()
            .name(DEFAULT_NAME)
            .level(DEFAULT_LEVEL);
        // Add required entity
        Employee employee = EmployeeResourceIntTest.createEntity(em);
        em.persist(employee);
        em.flush();
        employeeSkill.setEmployee(employee);
        return employeeSkill;
    }

    @Before
    public void initTest() {
        employeeSkill = createEntity(em);
    }

    @Test
    @Transactional
    public void createEmployeeSkill() throws Exception {
        int databaseSizeBeforeCreate = employeeSkillRepository.findAll().size();

        // Create the EmployeeSkill
        EmployeeSkillDTO employeeSkillDTO = employeeSkillMapper.toDto(employeeSkill);
        restEmployeeSkillMockMvc.perform(post("/api/employee-skills")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(employeeSkillDTO)))
            .andExpect(status().isCreated());

        // Validate the EmployeeSkill in the database
        List<EmployeeSkill> employeeSkillList = employeeSkillRepository.findAll();
        assertThat(employeeSkillList).hasSize(databaseSizeBeforeCreate + 1);
        EmployeeSkill testEmployeeSkill = employeeSkillList.get(employeeSkillList.size() - 1);
        assertThat(testEmployeeSkill.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testEmployeeSkill.getLevel()).isEqualTo(DEFAULT_LEVEL);
    }

    @Test
    @Transactional
    public void createEmployeeSkillWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = employeeSkillRepository.findAll().size();

        // Create the EmployeeSkill with an existing ID
        employeeSkill.setId(1L);
        EmployeeSkillDTO employeeSkillDTO = employeeSkillMapper.toDto(employeeSkill);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmployeeSkillMockMvc.perform(post("/api/employee-skills")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(employeeSkillDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EmployeeSkill in the database
        List<EmployeeSkill> employeeSkillList = employeeSkillRepository.findAll();
        assertThat(employeeSkillList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeSkillRepository.findAll().size();
        // set the field null
        employeeSkill.setName(null);

        // Create the EmployeeSkill, which fails.
        EmployeeSkillDTO employeeSkillDTO = employeeSkillMapper.toDto(employeeSkill);

        restEmployeeSkillMockMvc.perform(post("/api/employee-skills")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(employeeSkillDTO)))
            .andExpect(status().isBadRequest());

        List<EmployeeSkill> employeeSkillList = employeeSkillRepository.findAll();
        assertThat(employeeSkillList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLevelIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeSkillRepository.findAll().size();
        // set the field null
        employeeSkill.setLevel(null);

        // Create the EmployeeSkill, which fails.
        EmployeeSkillDTO employeeSkillDTO = employeeSkillMapper.toDto(employeeSkill);

        restEmployeeSkillMockMvc.perform(post("/api/employee-skills")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(employeeSkillDTO)))
            .andExpect(status().isBadRequest());

        List<EmployeeSkill> employeeSkillList = employeeSkillRepository.findAll();
        assertThat(employeeSkillList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEmployeeSkills() throws Exception {
        // Initialize the database
        employeeSkillRepository.saveAndFlush(employeeSkill);

        // Get all the employeeSkillList
        restEmployeeSkillMockMvc.perform(get("/api/employee-skills?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(employeeSkill.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].level").value(hasItem(DEFAULT_LEVEL)));
    }


    @Test
    @Transactional
    public void getEmployeeSkill() throws Exception {
        // Initialize the database
        employeeSkillRepository.saveAndFlush(employeeSkill);

        // Get the employeeSkill
        restEmployeeSkillMockMvc.perform(get("/api/employee-skills/{id}", employeeSkill.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(employeeSkill.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.level").value(DEFAULT_LEVEL));
    }

    @Test
    @Transactional
    public void getAllEmployeeSkillsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeSkillRepository.saveAndFlush(employeeSkill);

        // Get all the employeeSkillList where name equals to DEFAULT_NAME
        defaultEmployeeSkillShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the employeeSkillList where name equals to UPDATED_NAME
        defaultEmployeeSkillShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllEmployeeSkillsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        employeeSkillRepository.saveAndFlush(employeeSkill);

        // Get all the employeeSkillList where name in DEFAULT_NAME or UPDATED_NAME
        defaultEmployeeSkillShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the employeeSkillList where name equals to UPDATED_NAME
        defaultEmployeeSkillShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllEmployeeSkillsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeSkillRepository.saveAndFlush(employeeSkill);

        // Get all the employeeSkillList where name is not null
        defaultEmployeeSkillShouldBeFound("name.specified=true");

        // Get all the employeeSkillList where name is null
        defaultEmployeeSkillShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmployeeSkillsByLevelIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeSkillRepository.saveAndFlush(employeeSkill);

        // Get all the employeeSkillList where level equals to DEFAULT_LEVEL
        defaultEmployeeSkillShouldBeFound("level.equals=" + DEFAULT_LEVEL);

        // Get all the employeeSkillList where level equals to UPDATED_LEVEL
        defaultEmployeeSkillShouldNotBeFound("level.equals=" + UPDATED_LEVEL);
    }

    @Test
    @Transactional
    public void getAllEmployeeSkillsByLevelIsInShouldWork() throws Exception {
        // Initialize the database
        employeeSkillRepository.saveAndFlush(employeeSkill);

        // Get all the employeeSkillList where level in DEFAULT_LEVEL or UPDATED_LEVEL
        defaultEmployeeSkillShouldBeFound("level.in=" + DEFAULT_LEVEL + "," + UPDATED_LEVEL);

        // Get all the employeeSkillList where level equals to UPDATED_LEVEL
        defaultEmployeeSkillShouldNotBeFound("level.in=" + UPDATED_LEVEL);
    }

    @Test
    @Transactional
    public void getAllEmployeeSkillsByLevelIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeSkillRepository.saveAndFlush(employeeSkill);

        // Get all the employeeSkillList where level is not null
        defaultEmployeeSkillShouldBeFound("level.specified=true");

        // Get all the employeeSkillList where level is null
        defaultEmployeeSkillShouldNotBeFound("level.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmployeeSkillsByLevelIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeSkillRepository.saveAndFlush(employeeSkill);

        // Get all the employeeSkillList where level greater than or equals to DEFAULT_LEVEL
        defaultEmployeeSkillShouldBeFound("level.greaterOrEqualThan=" + DEFAULT_LEVEL);

        // Get all the employeeSkillList where level greater than or equals to UPDATED_LEVEL
        defaultEmployeeSkillShouldNotBeFound("level.greaterOrEqualThan=" + UPDATED_LEVEL);
    }

    @Test
    @Transactional
    public void getAllEmployeeSkillsByLevelIsLessThanSomething() throws Exception {
        // Initialize the database
        employeeSkillRepository.saveAndFlush(employeeSkill);

        // Get all the employeeSkillList where level less than or equals to DEFAULT_LEVEL
        defaultEmployeeSkillShouldNotBeFound("level.lessThan=" + DEFAULT_LEVEL);

        // Get all the employeeSkillList where level less than or equals to UPDATED_LEVEL
        defaultEmployeeSkillShouldBeFound("level.lessThan=" + UPDATED_LEVEL);
    }

    @Test
    @Transactional
    public void getAllEmployeeSkillsByEmployeeIsEqualToSomething() throws Exception {
        // Initialize the database

        employeeSkillRepository.saveAndFlush(employeeSkill);
        String employeeId = employeeSkill.getEmployee().getId();

        // Get all the employeeSkillList where employee equals to employeeId
        defaultEmployeeSkillShouldBeFound("employeeId.equals=" + employeeId);

        // Get all the employeeSkillList where employee equals to employeeId + 1
        defaultEmployeeSkillShouldNotBeFound("employeeId.equals=" + (employeeId + "a"));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultEmployeeSkillShouldBeFound(String filter) throws Exception {
        restEmployeeSkillMockMvc.perform(get("/api/employee-skills?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(employeeSkill.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].level").value(hasItem(DEFAULT_LEVEL)));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultEmployeeSkillShouldNotBeFound(String filter) throws Exception {
        restEmployeeSkillMockMvc.perform(get("/api/employee-skills?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    @Transactional
    public void getNonExistingEmployeeSkill() throws Exception {
        // Get the employeeSkill
        restEmployeeSkillMockMvc.perform(get("/api/employee-skills/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEmployeeSkill() throws Exception {
        // Initialize the database
        employeeSkillRepository.saveAndFlush(employeeSkill);

        int databaseSizeBeforeUpdate = employeeSkillRepository.findAll().size();

        // Update the employeeSkill
        EmployeeSkill updatedEmployeeSkill = employeeSkillRepository.findById(employeeSkill.getId()).get();
        // Disconnect from session so that the updates on updatedEmployeeSkill are not directly saved in db
        em.detach(updatedEmployeeSkill);
        updatedEmployeeSkill
            .name(UPDATED_NAME)
            .level(UPDATED_LEVEL);
        EmployeeSkillDTO employeeSkillDTO = employeeSkillMapper.toDto(updatedEmployeeSkill);

        restEmployeeSkillMockMvc.perform(put("/api/employee-skills")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(employeeSkillDTO)))
            .andExpect(status().isOk());

        // Validate the EmployeeSkill in the database
        List<EmployeeSkill> employeeSkillList = employeeSkillRepository.findAll();
        assertThat(employeeSkillList).hasSize(databaseSizeBeforeUpdate);
        EmployeeSkill testEmployeeSkill = employeeSkillList.get(employeeSkillList.size() - 1);
        assertThat(testEmployeeSkill.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testEmployeeSkill.getLevel()).isEqualTo(UPDATED_LEVEL);
    }

    @Test
    @Transactional
    public void updateNonExistingEmployeeSkill() throws Exception {
        int databaseSizeBeforeUpdate = employeeSkillRepository.findAll().size();

        // Create the EmployeeSkill
        EmployeeSkillDTO employeeSkillDTO = employeeSkillMapper.toDto(employeeSkill);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restEmployeeSkillMockMvc.perform(put("/api/employee-skills")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(employeeSkillDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EmployeeSkill in the database
        List<EmployeeSkill> employeeSkillList = employeeSkillRepository.findAll();
        assertThat(employeeSkillList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEmployeeSkill() throws Exception {
        // Initialize the database
        employeeSkillRepository.saveAndFlush(employeeSkill);

        int databaseSizeBeforeDelete = employeeSkillRepository.findAll().size();

        // Get the employeeSkill
        restEmployeeSkillMockMvc.perform(delete("/api/employee-skills/{id}", employeeSkill.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<EmployeeSkill> employeeSkillList = employeeSkillRepository.findAll();
        assertThat(employeeSkillList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmployeeSkill.class);
        EmployeeSkill employeeSkill1 = new EmployeeSkill();
        employeeSkill1.setId(1L);
        EmployeeSkill employeeSkill2 = new EmployeeSkill();
        employeeSkill2.setId(employeeSkill1.getId());
        assertThat(employeeSkill1).isEqualTo(employeeSkill2);
        employeeSkill2.setId(2L);
        assertThat(employeeSkill1).isNotEqualTo(employeeSkill2);
        employeeSkill1.setId(null);
        assertThat(employeeSkill1).isNotEqualTo(employeeSkill2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmployeeSkillDTO.class);
        EmployeeSkillDTO employeeSkillDTO1 = new EmployeeSkillDTO();
        employeeSkillDTO1.setId(1L);
        EmployeeSkillDTO employeeSkillDTO2 = new EmployeeSkillDTO();
        assertThat(employeeSkillDTO1).isNotEqualTo(employeeSkillDTO2);
        employeeSkillDTO2.setId(employeeSkillDTO1.getId());
        assertThat(employeeSkillDTO1).isEqualTo(employeeSkillDTO2);
        employeeSkillDTO2.setId(2L);
        assertThat(employeeSkillDTO1).isNotEqualTo(employeeSkillDTO2);
        employeeSkillDTO1.setId(null);
        assertThat(employeeSkillDTO1).isNotEqualTo(employeeSkillDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(employeeSkillMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(employeeSkillMapper.fromId(null)).isNull();
    }
}
