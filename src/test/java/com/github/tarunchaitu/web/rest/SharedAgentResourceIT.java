package com.github.tarunchaitu.web.rest;

import com.github.tarunchaitu.JeevesdemoApp;
import com.github.tarunchaitu.domain.SharedAgent;
import com.github.tarunchaitu.repository.SharedAgentRepository;
import com.github.tarunchaitu.service.SharedAgentService;
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

import com.github.tarunchaitu.domain.enumeration.AgentState;
import com.github.tarunchaitu.domain.enumeration.BootstrapMethod;
import com.github.tarunchaitu.domain.enumeration.Os;
/**
 * Integration tests for the {@link SharedAgentResource} REST controller.
 */
@SpringBootTest(classes = JeevesdemoApp.class)
public class SharedAgentResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Integer DEFAULT_NO_OF_EXECUTORS = 1;
    private static final Integer UPDATED_NO_OF_EXECUTORS = 2;

    private static final String DEFAULT_LABEL = "AAAAAAAAAA";
    private static final String UPDATED_LABEL = "BBBBBBBBBB";

    private static final String DEFAULT_WORKSPACE = "AAAAAAAAAA";
    private static final String UPDATED_WORKSPACE = "BBBBBBBBBB";

    private static final String DEFAULT_DNS_NAME = "AAAAAAAAAA";
    private static final String UPDATED_DNS_NAME = "BBBBBBBBBB";

    private static final AgentState DEFAULT_AGENT_STATE = AgentState.ONLINE;
    private static final AgentState UPDATED_AGENT_STATE = AgentState.OFFLINE;

    private static final BootstrapMethod DEFAULT_BOOTSTRAP_METOD = BootstrapMethod.SSH;
    private static final BootstrapMethod UPDATED_BOOTSTRAP_METOD = BootstrapMethod.JNLP;

    private static final Os DEFAULT_OS = Os.WINDOWS;
    private static final Os UPDATED_OS = Os.MAC;

    private static final Integer DEFAULT_SSH_PORT = 1;
    private static final Integer UPDATED_SSH_PORT = 2;

    private static final String DEFAULT_JVM_OPTIONS = "AAAAAAAAAA";
    private static final String UPDATED_JVM_OPTIONS = "BBBBBBBBBB";

    @Autowired
    private SharedAgentRepository sharedAgentRepository;

    @Autowired
    private SharedAgentService sharedAgentService;

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

    private MockMvc restSharedAgentMockMvc;

    private SharedAgent sharedAgent;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SharedAgentResource sharedAgentResource = new SharedAgentResource(sharedAgentService);
        this.restSharedAgentMockMvc = MockMvcBuilders.standaloneSetup(sharedAgentResource)
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
    public static SharedAgent createEntity(EntityManager em) {
        SharedAgent sharedAgent = new SharedAgent()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .noOfExecutors(DEFAULT_NO_OF_EXECUTORS)
            .label(DEFAULT_LABEL)
            .workspace(DEFAULT_WORKSPACE)
            .dnsName(DEFAULT_DNS_NAME)
            .agentState(DEFAULT_AGENT_STATE)
            .bootstrapMetod(DEFAULT_BOOTSTRAP_METOD)
            .os(DEFAULT_OS)
            .sshPort(DEFAULT_SSH_PORT)
            .jvmOptions(DEFAULT_JVM_OPTIONS);
        return sharedAgent;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SharedAgent createUpdatedEntity(EntityManager em) {
        SharedAgent sharedAgent = new SharedAgent()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .noOfExecutors(UPDATED_NO_OF_EXECUTORS)
            .label(UPDATED_LABEL)
            .workspace(UPDATED_WORKSPACE)
            .dnsName(UPDATED_DNS_NAME)
            .agentState(UPDATED_AGENT_STATE)
            .bootstrapMetod(UPDATED_BOOTSTRAP_METOD)
            .os(UPDATED_OS)
            .sshPort(UPDATED_SSH_PORT)
            .jvmOptions(UPDATED_JVM_OPTIONS);
        return sharedAgent;
    }

    @BeforeEach
    public void initTest() {
        sharedAgent = createEntity(em);
    }

    @Test
    @Transactional
    public void createSharedAgent() throws Exception {
        int databaseSizeBeforeCreate = sharedAgentRepository.findAll().size();

        // Create the SharedAgent
        restSharedAgentMockMvc.perform(post("/api/shared-agents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sharedAgent)))
            .andExpect(status().isCreated());

        // Validate the SharedAgent in the database
        List<SharedAgent> sharedAgentList = sharedAgentRepository.findAll();
        assertThat(sharedAgentList).hasSize(databaseSizeBeforeCreate + 1);
        SharedAgent testSharedAgent = sharedAgentList.get(sharedAgentList.size() - 1);
        assertThat(testSharedAgent.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testSharedAgent.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testSharedAgent.getNoOfExecutors()).isEqualTo(DEFAULT_NO_OF_EXECUTORS);
        assertThat(testSharedAgent.getLabel()).isEqualTo(DEFAULT_LABEL);
        assertThat(testSharedAgent.getWorkspace()).isEqualTo(DEFAULT_WORKSPACE);
        assertThat(testSharedAgent.getDnsName()).isEqualTo(DEFAULT_DNS_NAME);
        assertThat(testSharedAgent.getAgentState()).isEqualTo(DEFAULT_AGENT_STATE);
        assertThat(testSharedAgent.getBootstrapMetod()).isEqualTo(DEFAULT_BOOTSTRAP_METOD);
        assertThat(testSharedAgent.getOs()).isEqualTo(DEFAULT_OS);
        assertThat(testSharedAgent.getSshPort()).isEqualTo(DEFAULT_SSH_PORT);
        assertThat(testSharedAgent.getJvmOptions()).isEqualTo(DEFAULT_JVM_OPTIONS);
    }

    @Test
    @Transactional
    public void createSharedAgentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = sharedAgentRepository.findAll().size();

        // Create the SharedAgent with an existing ID
        sharedAgent.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSharedAgentMockMvc.perform(post("/api/shared-agents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sharedAgent)))
            .andExpect(status().isBadRequest());

        // Validate the SharedAgent in the database
        List<SharedAgent> sharedAgentList = sharedAgentRepository.findAll();
        assertThat(sharedAgentList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = sharedAgentRepository.findAll().size();
        // set the field null
        sharedAgent.setName(null);

        // Create the SharedAgent, which fails.

        restSharedAgentMockMvc.perform(post("/api/shared-agents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sharedAgent)))
            .andExpect(status().isBadRequest());

        List<SharedAgent> sharedAgentList = sharedAgentRepository.findAll();
        assertThat(sharedAgentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNoOfExecutorsIsRequired() throws Exception {
        int databaseSizeBeforeTest = sharedAgentRepository.findAll().size();
        // set the field null
        sharedAgent.setNoOfExecutors(null);

        // Create the SharedAgent, which fails.

        restSharedAgentMockMvc.perform(post("/api/shared-agents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sharedAgent)))
            .andExpect(status().isBadRequest());

        List<SharedAgent> sharedAgentList = sharedAgentRepository.findAll();
        assertThat(sharedAgentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLabelIsRequired() throws Exception {
        int databaseSizeBeforeTest = sharedAgentRepository.findAll().size();
        // set the field null
        sharedAgent.setLabel(null);

        // Create the SharedAgent, which fails.

        restSharedAgentMockMvc.perform(post("/api/shared-agents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sharedAgent)))
            .andExpect(status().isBadRequest());

        List<SharedAgent> sharedAgentList = sharedAgentRepository.findAll();
        assertThat(sharedAgentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkWorkspaceIsRequired() throws Exception {
        int databaseSizeBeforeTest = sharedAgentRepository.findAll().size();
        // set the field null
        sharedAgent.setWorkspace(null);

        // Create the SharedAgent, which fails.

        restSharedAgentMockMvc.perform(post("/api/shared-agents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sharedAgent)))
            .andExpect(status().isBadRequest());

        List<SharedAgent> sharedAgentList = sharedAgentRepository.findAll();
        assertThat(sharedAgentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDnsNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = sharedAgentRepository.findAll().size();
        // set the field null
        sharedAgent.setDnsName(null);

        // Create the SharedAgent, which fails.

        restSharedAgentMockMvc.perform(post("/api/shared-agents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sharedAgent)))
            .andExpect(status().isBadRequest());

        List<SharedAgent> sharedAgentList = sharedAgentRepository.findAll();
        assertThat(sharedAgentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAgentStateIsRequired() throws Exception {
        int databaseSizeBeforeTest = sharedAgentRepository.findAll().size();
        // set the field null
        sharedAgent.setAgentState(null);

        // Create the SharedAgent, which fails.

        restSharedAgentMockMvc.perform(post("/api/shared-agents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sharedAgent)))
            .andExpect(status().isBadRequest());

        List<SharedAgent> sharedAgentList = sharedAgentRepository.findAll();
        assertThat(sharedAgentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBootstrapMetodIsRequired() throws Exception {
        int databaseSizeBeforeTest = sharedAgentRepository.findAll().size();
        // set the field null
        sharedAgent.setBootstrapMetod(null);

        // Create the SharedAgent, which fails.

        restSharedAgentMockMvc.perform(post("/api/shared-agents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sharedAgent)))
            .andExpect(status().isBadRequest());

        List<SharedAgent> sharedAgentList = sharedAgentRepository.findAll();
        assertThat(sharedAgentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkOsIsRequired() throws Exception {
        int databaseSizeBeforeTest = sharedAgentRepository.findAll().size();
        // set the field null
        sharedAgent.setOs(null);

        // Create the SharedAgent, which fails.

        restSharedAgentMockMvc.perform(post("/api/shared-agents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sharedAgent)))
            .andExpect(status().isBadRequest());

        List<SharedAgent> sharedAgentList = sharedAgentRepository.findAll();
        assertThat(sharedAgentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSharedAgents() throws Exception {
        // Initialize the database
        sharedAgentRepository.saveAndFlush(sharedAgent);

        // Get all the sharedAgentList
        restSharedAgentMockMvc.perform(get("/api/shared-agents?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sharedAgent.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].noOfExecutors").value(hasItem(DEFAULT_NO_OF_EXECUTORS)))
            .andExpect(jsonPath("$.[*].label").value(hasItem(DEFAULT_LABEL)))
            .andExpect(jsonPath("$.[*].workspace").value(hasItem(DEFAULT_WORKSPACE)))
            .andExpect(jsonPath("$.[*].dnsName").value(hasItem(DEFAULT_DNS_NAME)))
            .andExpect(jsonPath("$.[*].agentState").value(hasItem(DEFAULT_AGENT_STATE.toString())))
            .andExpect(jsonPath("$.[*].bootstrapMetod").value(hasItem(DEFAULT_BOOTSTRAP_METOD.toString())))
            .andExpect(jsonPath("$.[*].os").value(hasItem(DEFAULT_OS.toString())))
            .andExpect(jsonPath("$.[*].sshPort").value(hasItem(DEFAULT_SSH_PORT)))
            .andExpect(jsonPath("$.[*].jvmOptions").value(hasItem(DEFAULT_JVM_OPTIONS)));
    }
    
    @Test
    @Transactional
    public void getSharedAgent() throws Exception {
        // Initialize the database
        sharedAgentRepository.saveAndFlush(sharedAgent);

        // Get the sharedAgent
        restSharedAgentMockMvc.perform(get("/api/shared-agents/{id}", sharedAgent.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(sharedAgent.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.noOfExecutors").value(DEFAULT_NO_OF_EXECUTORS))
            .andExpect(jsonPath("$.label").value(DEFAULT_LABEL))
            .andExpect(jsonPath("$.workspace").value(DEFAULT_WORKSPACE))
            .andExpect(jsonPath("$.dnsName").value(DEFAULT_DNS_NAME))
            .andExpect(jsonPath("$.agentState").value(DEFAULT_AGENT_STATE.toString()))
            .andExpect(jsonPath("$.bootstrapMetod").value(DEFAULT_BOOTSTRAP_METOD.toString()))
            .andExpect(jsonPath("$.os").value(DEFAULT_OS.toString()))
            .andExpect(jsonPath("$.sshPort").value(DEFAULT_SSH_PORT))
            .andExpect(jsonPath("$.jvmOptions").value(DEFAULT_JVM_OPTIONS));
    }

    @Test
    @Transactional
    public void getNonExistingSharedAgent() throws Exception {
        // Get the sharedAgent
        restSharedAgentMockMvc.perform(get("/api/shared-agents/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSharedAgent() throws Exception {
        // Initialize the database
        sharedAgentService.save(sharedAgent);

        int databaseSizeBeforeUpdate = sharedAgentRepository.findAll().size();

        // Update the sharedAgent
        SharedAgent updatedSharedAgent = sharedAgentRepository.findById(sharedAgent.getId()).get();
        // Disconnect from session so that the updates on updatedSharedAgent are not directly saved in db
        em.detach(updatedSharedAgent);
        updatedSharedAgent
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .noOfExecutors(UPDATED_NO_OF_EXECUTORS)
            .label(UPDATED_LABEL)
            .workspace(UPDATED_WORKSPACE)
            .dnsName(UPDATED_DNS_NAME)
            .agentState(UPDATED_AGENT_STATE)
            .bootstrapMetod(UPDATED_BOOTSTRAP_METOD)
            .os(UPDATED_OS)
            .sshPort(UPDATED_SSH_PORT)
            .jvmOptions(UPDATED_JVM_OPTIONS);

        restSharedAgentMockMvc.perform(put("/api/shared-agents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSharedAgent)))
            .andExpect(status().isOk());

        // Validate the SharedAgent in the database
        List<SharedAgent> sharedAgentList = sharedAgentRepository.findAll();
        assertThat(sharedAgentList).hasSize(databaseSizeBeforeUpdate);
        SharedAgent testSharedAgent = sharedAgentList.get(sharedAgentList.size() - 1);
        assertThat(testSharedAgent.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testSharedAgent.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testSharedAgent.getNoOfExecutors()).isEqualTo(UPDATED_NO_OF_EXECUTORS);
        assertThat(testSharedAgent.getLabel()).isEqualTo(UPDATED_LABEL);
        assertThat(testSharedAgent.getWorkspace()).isEqualTo(UPDATED_WORKSPACE);
        assertThat(testSharedAgent.getDnsName()).isEqualTo(UPDATED_DNS_NAME);
        assertThat(testSharedAgent.getAgentState()).isEqualTo(UPDATED_AGENT_STATE);
        assertThat(testSharedAgent.getBootstrapMetod()).isEqualTo(UPDATED_BOOTSTRAP_METOD);
        assertThat(testSharedAgent.getOs()).isEqualTo(UPDATED_OS);
        assertThat(testSharedAgent.getSshPort()).isEqualTo(UPDATED_SSH_PORT);
        assertThat(testSharedAgent.getJvmOptions()).isEqualTo(UPDATED_JVM_OPTIONS);
    }

    @Test
    @Transactional
    public void updateNonExistingSharedAgent() throws Exception {
        int databaseSizeBeforeUpdate = sharedAgentRepository.findAll().size();

        // Create the SharedAgent

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSharedAgentMockMvc.perform(put("/api/shared-agents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sharedAgent)))
            .andExpect(status().isBadRequest());

        // Validate the SharedAgent in the database
        List<SharedAgent> sharedAgentList = sharedAgentRepository.findAll();
        assertThat(sharedAgentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSharedAgent() throws Exception {
        // Initialize the database
        sharedAgentService.save(sharedAgent);

        int databaseSizeBeforeDelete = sharedAgentRepository.findAll().size();

        // Delete the sharedAgent
        restSharedAgentMockMvc.perform(delete("/api/shared-agents/{id}", sharedAgent.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SharedAgent> sharedAgentList = sharedAgentRepository.findAll();
        assertThat(sharedAgentList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
