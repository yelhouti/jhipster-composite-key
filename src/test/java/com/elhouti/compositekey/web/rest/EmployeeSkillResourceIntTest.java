package com.elhouti.compositekey.web.rest;

import com.elhouti.compositekey.CompositekeyApp;

import com.elhouti.compositekey.domain.EmployeeSkill;
import com.elhouti.compositekey.domain.EmployeeSkillId;
import com.elhouti.compositekey.domain.Employee;
import com.elhouti.compositekey.repository.EmployeeSkillRepository;
import com.elhouti.compositekey.service.EmployeeSkillService;
import com.elhouti.compositekey.service.dto.EmployeeSkillDTO;
import com.elhouti.compositekey.service.mapper.EmployeeSkillMapper;
import com.elhouti.compositekey.web.rest.errors.ExceptionTranslator;
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
    private static final String OTHER_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_EMPLOYEE_ID = "AAAAAAAAAA";
    private static final String OTHER_EMPLOYEE_ID = "BBBBBBBBBB";

    private static final EmployeeSkillId DEFAULT_EMPLOYEE_SKILL_ID = new EmployeeSkillId(DEFAULT_EMPLOYEE_ID, DEFAULT_NAME);
    private static final EmployeeSkillId OTHER_EMPLOYEE_SKILL_ID = new EmployeeSkillId(OTHER_EMPLOYEE_ID, OTHER_NAME);

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
    public static EmployeeSkill createEntity(EntityManager em) {
        EmployeeSkill employeeSkill = new EmployeeSkill()
            .level(DEFAULT_LEVEL);
        // Add required entity
        Employee employee = EmployeeResourceIntTest.createEntity(em);
        em.persist(employee);
        em.flush();
        employeeSkill.setEmployee(employee);
        employeeSkill.setId(new EmployeeSkillId(employee.getId(),DEFAULT_NAME));
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
        employeeSkill.setId(new EmployeeSkillId(employeeSkill.getId().getEmployeeId(),DEFAULT_NAME));
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
        assertThat(testEmployeeSkill.getLevel()).isEqualTo(DEFAULT_LEVEL);
    }

    @Test
    @Transactional
    public void createEmployeeSkillWithExistingId() throws Exception {
        employeeSkillRepository.saveAndFlush(employeeSkill);
        int databaseSizeBeforeCreate = employeeSkillRepository.findAll().size();

        // Create the EmployeeSkill with an existing ID
        EmployeeSkillDTO employeeSkillDTO = employeeSkillMapper.toDto(employeeSkill);

        // An entity with an existing ID cannot be created, so this API call must show a conflict
        restEmployeeSkillMockMvc.perform(post("/api/employee-skills")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(employeeSkillDTO)))
            .andExpect(status().isConflict());

        // Validate the EmployeeSkill in the database
        List<EmployeeSkill> employeeSkillList = employeeSkillRepository.findAll();
        assertThat(employeeSkillList).hasSize(databaseSizeBeforeCreate);
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
            .andExpect(jsonPath("$.[*].employeeId").value(hasItem(employeeSkill.getId().getEmployeeId())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(employeeSkill.getId().getName())))
            .andExpect(jsonPath("$.[*].level").value(hasItem(employeeSkill.getLevel())));
    }



   @Test
    @Transactional
    public void getEmployeeSkill() throws Exception {
        // Initialize the database
        employeeSkillRepository.saveAndFlush(employeeSkill);

        // Get the employeeSkill
        restEmployeeSkillMockMvc.perform(get("/api/employee-skills/{id}", employeeSkill.getId().toMatrixVariableString()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.employeeId").value(employeeSkill.getId().getEmployeeId()))
            .andExpect(jsonPath("$.name").value(employeeSkill.getId().getName()))
            .andExpect(jsonPath("$.level").value(employeeSkill.getLevel()));
    }

    @Test
    @Transactional
    public void getAllEmployeeSkillsByEmployeeIdIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeSkillRepository.saveAndFlush(employeeSkill);

        // Get all the employeeSkillList where name equals to DEFAULT_EMPLOYEE_ID
        defaultEmployeeSkillShouldBeFound("employeeId.equals=" + employeeSkill.getId().getEmployeeId());

        // Get all the employeeSkillList where name equals to OTHER_EMPLOYEE_ID
        defaultEmployeeSkillShouldNotBeFound("employeeId.equals=" + OTHER_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    public void getAllEmployeeSkillsByEmployeeIdIsInShouldWork() throws Exception {
        // Initialize the database
        employeeSkillRepository.saveAndFlush(employeeSkill);

        // Get all the employeeSkillList where name in DEFAULT_EMPLOYEE_ID or OTHER_EMPLOYEE_ID
        defaultEmployeeSkillShouldBeFound("employeeId.in=" + employeeSkill.getId().getEmployeeId() + "," + OTHER_EMPLOYEE_ID);

        // Get all the employeeSkillList where name equals to OTHER_EMPLOYEE_ID
        defaultEmployeeSkillShouldNotBeFound("employeeId.in=" + OTHER_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    public void getAllEmployeeSkillsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeSkillRepository.saveAndFlush(employeeSkill);

        // Get all the employeeSkillList where name equals to DEFAULT_NAME
        defaultEmployeeSkillShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the employeeSkillList where name equals to OTHER_NAME
        defaultEmployeeSkillShouldNotBeFound("name.equals=" + OTHER_NAME);
    }

    @Test
    @Transactional
    public void getAllEmployeeSkillsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        employeeSkillRepository.saveAndFlush(employeeSkill);

        // Get all the employeeSkillList where name in DEFAULT_NAME or OTHER_NAME
        defaultEmployeeSkillShouldBeFound("name.in=" + DEFAULT_NAME + "," + OTHER_NAME);

        // Get all the employeeSkillList where name equals to OTHER_NAME
        defaultEmployeeSkillShouldNotBeFound("name.in=" + OTHER_NAME);
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
        String employeeId = employeeSkill.getId().getEmployeeId();

        // Get all the employeeSkillList where employee equals to employeeId
        defaultEmployeeSkillShouldBeFound("employeeId.equals=" + employeeId);

        // Get all the employeeSkillList where employee does not equal employeeId
        defaultEmployeeSkillShouldNotBeFound("employeeId.equals=" + (OTHER_EMPLOYEE_SKILL_ID.getEmployeeId()));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultEmployeeSkillShouldBeFound(String filter) throws Exception {
        restEmployeeSkillMockMvc.perform(get("/api/employee-skills?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].employeeId").value(hasItem(employeeSkill.getId().getEmployeeId())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(employeeSkill.getId().getName())))
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
        assertThat(testEmployeeSkill.getId()).isEqualTo(new EmployeeSkillId(employeeSkill.getId().getEmployeeId(), employeeSkill.getId().getName()));
        assertThat(testEmployeeSkill.getLevel()).isEqualTo(UPDATED_LEVEL);
    }

    @Test
    @Transactional
    public void updateNonExistingEmployeeSkill() throws Exception {
        int databaseSizeBeforeUpdate = employeeSkillRepository.findAll().size();
        // Create the EmployeeSkill
        EmployeeSkillDTO employeeSkillDTO = employeeSkillMapper.toDto(employeeSkill);

        // If the entity doesn't have an ID, it will show an error
        restEmployeeSkillMockMvc.perform(put("/api/employee-skills")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(employeeSkillDTO)))
            .andExpect(status().isBadRequest());

        // Validate that there is no new EmployeeSkill in the database
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
        restEmployeeSkillMockMvc.perform(delete("/api/employee-skills/{id}", employeeSkill.getId().toMatrixVariableString())
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
        employeeSkill1.setId(DEFAULT_EMPLOYEE_SKILL_ID);
        EmployeeSkill employeeSkill2 = new EmployeeSkill();
        employeeSkill2.setId(employeeSkill1.getId());
        assertThat(employeeSkill1).isEqualTo(employeeSkill2);
        employeeSkill2.setId(OTHER_EMPLOYEE_SKILL_ID);
        assertThat(employeeSkill1).isNotEqualTo(employeeSkill2);
        employeeSkill1.setId(null);
        assertThat(employeeSkill1).isNotEqualTo(employeeSkill2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmployeeSkillDTO.class);
        EmployeeSkillDTO employeeSkillDTO1 = new EmployeeSkillDTO();
        employeeSkillDTO1.setEmployeeId(DEFAULT_EMPLOYEE_ID);
        employeeSkillDTO1.setName(DEFAULT_NAME);
        EmployeeSkillDTO employeeSkillDTO2 = new EmployeeSkillDTO();
        employeeSkillDTO2.setEmployeeId(OTHER_EMPLOYEE_ID);
        employeeSkillDTO2.setName(OTHER_NAME);
        assertThat(employeeSkillDTO1).isNotEqualTo(employeeSkillDTO2);
        employeeSkillDTO2.setEmployeeId(employeeSkillDTO1.getEmployeeId());
        employeeSkillDTO2.setName(employeeSkillDTO1.getName());
        assertThat(employeeSkillDTO1).isEqualTo(employeeSkillDTO2);
        employeeSkillDTO2.setEmployeeId(OTHER_EMPLOYEE_ID);
        assertThat(employeeSkillDTO1).isNotEqualTo(employeeSkillDTO2);
        employeeSkillDTO2.setEmployeeId(employeeSkillDTO1.getEmployeeId());
        employeeSkillDTO2.setName(OTHER_NAME);
        assertThat(employeeSkillDTO1).isNotEqualTo(employeeSkillDTO2);
        employeeSkillDTO1.setName(null);
        assertThat(employeeSkillDTO1).isNotEqualTo(employeeSkillDTO2);
        employeeSkillDTO1.setName(employeeSkillDTO2.getName());
        employeeSkillDTO1.setEmployeeId(null);
        assertThat(employeeSkillDTO1).isNotEqualTo(employeeSkillDTO2);
        employeeSkillDTO1.setName(null);
        assertThat(employeeSkillDTO1).isNotEqualTo(employeeSkillDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(employeeSkillMapper.fromId(DEFAULT_EMPLOYEE_SKILL_ID).getId()).isEqualTo(DEFAULT_EMPLOYEE_SKILL_ID);
        assertThat(employeeSkillMapper.fromId(null)).isNull();
    }
}
