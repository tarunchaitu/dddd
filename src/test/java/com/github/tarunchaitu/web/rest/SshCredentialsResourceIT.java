package com.github.tarunchaitu.web.rest;

import com.github.tarunchaitu.JeevesdemoApp;
import com.github.tarunchaitu.domain.SshCredentials;
import com.github.tarunchaitu.repository.SshCredentialsRepository;
import com.github.tarunchaitu.service.SshCredentialsService;
import com.github.tarunchaitu.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static com.github.tarunchaitu.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link SshCredentialsResource} REST controller.
 */
@SpringBootTest(classes = JeevesdemoApp.class)
public class SshCredentialsResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_USERNAME = "AAAAAAAAAA";
    private static final String UPDATED_USERNAME = "BBBBBBBBBB";

    private static final String DEFAULT_PASSWORD = "AAAAAAAAAA";
    private static final String UPDATED_PASSWORD = "BBBBBBBBBB";

    private static final String DEFAULT_SSH_KEY = "AAAAAAAAAA";
    private static final String UPDATED_SSH_KEY = "BBBBBBBBBB";

    @Autowired
    private SshCredentialsRepository sshCredentialsRepository;

    @Autowired
    private SshCredentialsService sshCredentialsService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restSshCredentialsMockMvc;

    private SshCredentials sshCredentials;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SshCredentialsResource sshCredentialsResource = new SshCredentialsResource(sshCredentialsService);
        this.restSshCredentialsMockMvc = MockMvcBuilders.standaloneSetup(sshCredentialsResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SshCredentials createEntity(EntityManager em) {
        SshCredentials sshCredentials = new SshCredentials()
            .name(DEFAULT_NAME)
            .username(DEFAULT_USERNAME)
            .password(DEFAULT_PASSWORD)
            .sshKey(DEFAULT_SSH_KEY);
        return sshCredentials;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SshCredentials createUpdatedEntity(EntityManager em) {
        SshCredentials sshCredentials = new SshCredentials()
            .name(UPDATED_NAME)
            .username(UPDATED_USERNAME)
            .password(UPDATED_PASSWORD)
            .sshKey(UPDATED_SSH_KEY);
        return sshCredentials;
    }

    @BeforeEach
    public void initTest() {
        sshCredentials = createEntity(em);
    }

    @Test
    @Transactional
    public void createSshCredentials() throws Exception {
        int databaseSizeBeforeCreate = sshCredentialsRepository.findAll().size();

        // Create the SshCredentials
        restSshCredentialsMockMvc.perform(post("/api/ssh-credentials")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sshCredentials)))
            .andExpect(status().isCreated());

        // Validate the SshCredentials in the database
        List<SshCredentials> sshCredentialsList = sshCredentialsRepository.findAll();
        assertThat(sshCredentialsList).hasSize(databaseSizeBeforeCreate + 1);
        SshCredentials testSshCredentials = sshCredentialsList.get(sshCredentialsList.size() - 1);
        assertThat(testSshCredentials.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testSshCredentials.getUsername()).isEqualTo(DEFAULT_USERNAME);
        assertThat(testSshCredentials.getPassword()).isEqualTo(DEFAULT_PASSWORD);
        assertThat(testSshCredentials.getSshKey()).isEqualTo(DEFAULT_SSH_KEY);
    }

    @Test
    @Transactional
    public void createSshCredentialsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = sshCredentialsRepository.findAll().size();

        // Create the SshCredentials with an existing ID
        sshCredentials.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSshCredentialsMockMvc.perform(post("/api/ssh-credentials")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sshCredentials)))
            .andExpect(status().isBadRequest());

        // Validate the SshCredentials in the database
        List<SshCredentials> sshCredentialsList = sshCredentialsRepository.findAll();
        assertThat(sshCredentialsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = sshCredentialsRepository.findAll().size();
        // set the field null
        sshCredentials.setName(null);

        // Create the SshCredentials, which fails.

        restSshCredentialsMockMvc.perform(post("/api/ssh-credentials")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sshCredentials)))
            .andExpect(status().isBadRequest());

        List<SshCredentials> sshCredentialsList = sshCredentialsRepository.findAll();
        assertThat(sshCredentialsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUsernameIsRequired() throws Exception {
        int databaseSizeBeforeTest = sshCredentialsRepository.findAll().size();
        // set the field null
        sshCredentials.setUsername(null);

        // Create the SshCredentials, which fails.

        restSshCredentialsMockMvc.perform(post("/api/ssh-credentials")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sshCredentials)))
            .andExpect(status().isBadRequest());

        List<SshCredentials> sshCredentialsList = sshCredentialsRepository.findAll();
        assertThat(sshCredentialsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSshCredentials() throws Exception {
        // Initialize the database
        sshCredentialsRepository.saveAndFlush(sshCredentials);

        // Get all the sshCredentialsList
        restSshCredentialsMockMvc.perform(get("/api/ssh-credentials?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sshCredentials.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].username").value(hasItem(DEFAULT_USERNAME)))
            .andExpect(jsonPath("$.[*].password").value(hasItem(DEFAULT_PASSWORD)))
            .andExpect(jsonPath("$.[*].sshKey").value(hasItem(DEFAULT_SSH_KEY)));
    }
    
    @Test
    @Transactional
    public void getSshCredentials() throws Exception {
        // Initialize the database
        sshCredentialsRepository.saveAndFlush(sshCredentials);

        // Get the sshCredentials
        restSshCredentialsMockMvc.perform(get("/api/ssh-credentials/{id}", sshCredentials.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(sshCredentials.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.username").value(DEFAULT_USERNAME))
            .andExpect(jsonPath("$.password").value(DEFAULT_PASSWORD))
            .andExpect(jsonPath("$.sshKey").value(DEFAULT_SSH_KEY));
    }

    @Test
    @Transactional
    public void getNonExistingSshCredentials() throws Exception {
        // Get the sshCredentials
        restSshCredentialsMockMvc.perform(get("/api/ssh-credentials/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSshCredentials() throws Exception {
        // Initialize the database
        sshCredentialsService.save(sshCredentials);

        int databaseSizeBeforeUpdate = sshCredentialsRepository.findAll().size();

        // Update the sshCredentials
        SshCredentials updatedSshCredentials = sshCredentialsRepository.findById(sshCredentials.getId()).get();
        // Disconnect from session so that the updates on updatedSshCredentials are not directly saved in db
        em.detach(updatedSshCredentials);
        updatedSshCredentials
            .name(UPDATED_NAME)
            .username(UPDATED_USERNAME)
            .password(UPDATED_PASSWORD)
            .sshKey(UPDATED_SSH_KEY);

        restSshCredentialsMockMvc.perform(put("/api/ssh-credentials")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSshCredentials)))
            .andExpect(status().isOk());

        // Validate the SshCredentials in the database
        List<SshCredentials> sshCredentialsList = sshCredentialsRepository.findAll();
        assertThat(sshCredentialsList).hasSize(databaseSizeBeforeUpdate);
        SshCredentials testSshCredentials = sshCredentialsList.get(sshCredentialsList.size() - 1);
        assertThat(testSshCredentials.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testSshCredentials.getUsername()).isEqualTo(UPDATED_USERNAME);
        assertThat(testSshCredentials.getPassword()).isEqualTo(UPDATED_PASSWORD);
        assertThat(testSshCredentials.getSshKey()).isEqualTo(UPDATED_SSH_KEY);
    }

    @Test
    @Transactional
    public void updateNonExistingSshCredentials() throws Exception {
        int databaseSizeBeforeUpdate = sshCredentialsRepository.findAll().size();

        // Create the SshCredentials

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSshCredentialsMockMvc.perform(put("/api/ssh-credentials")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sshCredentials)))
            .andExpect(status().isBadRequest());

        // Validate the SshCredentials in the database
        List<SshCredentials> sshCredentialsList = sshCredentialsRepository.findAll();
        assertThat(sshCredentialsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSshCredentials() throws Exception {
        // Initialize the database
        sshCredentialsService.save(sshCredentials);

        int databaseSizeBeforeDelete = sshCredentialsRepository.findAll().size();

        // Delete the sshCredentials
        restSshCredentialsMockMvc.perform(delete("/api/ssh-credentials/{id}", sshCredentials.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SshCredentials> sshCredentialsList = sshCredentialsRepository.findAll();
        assertThat(sshCredentialsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
