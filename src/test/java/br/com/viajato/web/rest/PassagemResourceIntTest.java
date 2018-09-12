package br.com.viajato.web.rest;

import br.com.viajato.ViajatoApp;

import br.com.viajato.domain.Passagem;
import br.com.viajato.repository.PassagemRepository;
import br.com.viajato.web.rest.errors.ExceptionTranslator;

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


import static br.com.viajato.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the PassagemResource REST controller.
 *
 * @see PassagemResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ViajatoApp.class)
public class PassagemResourceIntTest {

    private static final String DEFAULT_CLASSE = "AAAAAAAAAA";
    private static final String UPDATED_CLASSE = "BBBBBBBBBB";

    private static final Integer DEFAULT_VALOR = 1;
    private static final Integer UPDATED_VALOR = 2;

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final Integer DEFAULT_CPF = 1;
    private static final Integer UPDATED_CPF = 2;

    @Autowired
    private PassagemRepository passagemRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPassagemMockMvc;

    private Passagem passagem;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PassagemResource passagemResource = new PassagemResource(passagemRepository);
        this.restPassagemMockMvc = MockMvcBuilders.standaloneSetup(passagemResource)
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
    public static Passagem createEntity(EntityManager em) {
        Passagem passagem = new Passagem()
            .classe(DEFAULT_CLASSE)
            .valor(DEFAULT_VALOR)
            .nome(DEFAULT_NOME)
            .cpf(DEFAULT_CPF);
        return passagem;
    }

    @Before
    public void initTest() {
        passagem = createEntity(em);
    }

    @Test
    @Transactional
    public void createPassagem() throws Exception {
        int databaseSizeBeforeCreate = passagemRepository.findAll().size();

        // Create the Passagem
        restPassagemMockMvc.perform(post("/api/passagems")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(passagem)))
            .andExpect(status().isCreated());

        // Validate the Passagem in the database
        List<Passagem> passagemList = passagemRepository.findAll();
        assertThat(passagemList).hasSize(databaseSizeBeforeCreate + 1);
        Passagem testPassagem = passagemList.get(passagemList.size() - 1);
        assertThat(testPassagem.getClasse()).isEqualTo(DEFAULT_CLASSE);
        assertThat(testPassagem.getValor()).isEqualTo(DEFAULT_VALOR);
        assertThat(testPassagem.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testPassagem.getCpf()).isEqualTo(DEFAULT_CPF);
    }

    @Test
    @Transactional
    public void createPassagemWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = passagemRepository.findAll().size();

        // Create the Passagem with an existing ID
        passagem.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPassagemMockMvc.perform(post("/api/passagems")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(passagem)))
            .andExpect(status().isBadRequest());

        // Validate the Passagem in the database
        List<Passagem> passagemList = passagemRepository.findAll();
        assertThat(passagemList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkClasseIsRequired() throws Exception {
        int databaseSizeBeforeTest = passagemRepository.findAll().size();
        // set the field null
        passagem.setClasse(null);

        // Create the Passagem, which fails.

        restPassagemMockMvc.perform(post("/api/passagems")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(passagem)))
            .andExpect(status().isBadRequest());

        List<Passagem> passagemList = passagemRepository.findAll();
        assertThat(passagemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValorIsRequired() throws Exception {
        int databaseSizeBeforeTest = passagemRepository.findAll().size();
        // set the field null
        passagem.setValor(null);

        // Create the Passagem, which fails.

        restPassagemMockMvc.perform(post("/api/passagems")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(passagem)))
            .andExpect(status().isBadRequest());

        List<Passagem> passagemList = passagemRepository.findAll();
        assertThat(passagemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = passagemRepository.findAll().size();
        // set the field null
        passagem.setNome(null);

        // Create the Passagem, which fails.

        restPassagemMockMvc.perform(post("/api/passagems")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(passagem)))
            .andExpect(status().isBadRequest());

        List<Passagem> passagemList = passagemRepository.findAll();
        assertThat(passagemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCpfIsRequired() throws Exception {
        int databaseSizeBeforeTest = passagemRepository.findAll().size();
        // set the field null
        passagem.setCpf(null);

        // Create the Passagem, which fails.

        restPassagemMockMvc.perform(post("/api/passagems")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(passagem)))
            .andExpect(status().isBadRequest());

        List<Passagem> passagemList = passagemRepository.findAll();
        assertThat(passagemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPassagems() throws Exception {
        // Initialize the database
        passagemRepository.saveAndFlush(passagem);

        // Get all the passagemList
        restPassagemMockMvc.perform(get("/api/passagems?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(passagem.getId().intValue())))
            .andExpect(jsonPath("$.[*].classe").value(hasItem(DEFAULT_CLASSE.toString())))
            .andExpect(jsonPath("$.[*].valor").value(hasItem(DEFAULT_VALOR)))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())))
            .andExpect(jsonPath("$.[*].cpf").value(hasItem(DEFAULT_CPF)));
    }
    
    @Test
    @Transactional
    public void getPassagem() throws Exception {
        // Initialize the database
        passagemRepository.saveAndFlush(passagem);

        // Get the passagem
        restPassagemMockMvc.perform(get("/api/passagems/{id}", passagem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(passagem.getId().intValue()))
            .andExpect(jsonPath("$.classe").value(DEFAULT_CLASSE.toString()))
            .andExpect(jsonPath("$.valor").value(DEFAULT_VALOR))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME.toString()))
            .andExpect(jsonPath("$.cpf").value(DEFAULT_CPF));
    }

    @Test
    @Transactional
    public void getNonExistingPassagem() throws Exception {
        // Get the passagem
        restPassagemMockMvc.perform(get("/api/passagems/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePassagem() throws Exception {
        // Initialize the database
        passagemRepository.saveAndFlush(passagem);

        int databaseSizeBeforeUpdate = passagemRepository.findAll().size();

        // Update the passagem
        Passagem updatedPassagem = passagemRepository.findById(passagem.getId()).get();
        // Disconnect from session so that the updates on updatedPassagem are not directly saved in db
        em.detach(updatedPassagem);
        updatedPassagem
            .classe(UPDATED_CLASSE)
            .valor(UPDATED_VALOR)
            .nome(UPDATED_NOME)
            .cpf(UPDATED_CPF);

        restPassagemMockMvc.perform(put("/api/passagems")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPassagem)))
            .andExpect(status().isOk());

        // Validate the Passagem in the database
        List<Passagem> passagemList = passagemRepository.findAll();
        assertThat(passagemList).hasSize(databaseSizeBeforeUpdate);
        Passagem testPassagem = passagemList.get(passagemList.size() - 1);
        assertThat(testPassagem.getClasse()).isEqualTo(UPDATED_CLASSE);
        assertThat(testPassagem.getValor()).isEqualTo(UPDATED_VALOR);
        assertThat(testPassagem.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testPassagem.getCpf()).isEqualTo(UPDATED_CPF);
    }

    @Test
    @Transactional
    public void updateNonExistingPassagem() throws Exception {
        int databaseSizeBeforeUpdate = passagemRepository.findAll().size();

        // Create the Passagem

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPassagemMockMvc.perform(put("/api/passagems")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(passagem)))
            .andExpect(status().isBadRequest());

        // Validate the Passagem in the database
        List<Passagem> passagemList = passagemRepository.findAll();
        assertThat(passagemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePassagem() throws Exception {
        // Initialize the database
        passagemRepository.saveAndFlush(passagem);

        int databaseSizeBeforeDelete = passagemRepository.findAll().size();

        // Get the passagem
        restPassagemMockMvc.perform(delete("/api/passagems/{id}", passagem.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Passagem> passagemList = passagemRepository.findAll();
        assertThat(passagemList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Passagem.class);
        Passagem passagem1 = new Passagem();
        passagem1.setId(1L);
        Passagem passagem2 = new Passagem();
        passagem2.setId(passagem1.getId());
        assertThat(passagem1).isEqualTo(passagem2);
        passagem2.setId(2L);
        assertThat(passagem1).isNotEqualTo(passagem2);
        passagem1.setId(null);
        assertThat(passagem1).isNotEqualTo(passagem2);
    }
}
