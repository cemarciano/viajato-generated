package br.com.viajato.web.rest;

import br.com.viajato.ViajatoApp;

import br.com.viajato.domain.Locacao;
import br.com.viajato.repository.LocacaoRepository;
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
 * Test class for the LocacaoResource REST controller.
 *
 * @see LocacaoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ViajatoApp.class)
public class LocacaoResourceIntTest {

    private static final String DEFAULT_INICIO = "AAAAAAAAAA";
    private static final String UPDATED_INICIO = "BBBBBBBBBB";

    private static final Integer DEFAULT_DURACAO = 1;
    private static final Integer UPDATED_DURACAO = 2;

    private static final Integer DEFAULT_VALOR = 1;
    private static final Integer UPDATED_VALOR = 2;

    @Autowired
    private LocacaoRepository locacaoRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restLocacaoMockMvc;

    private Locacao locacao;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final LocacaoResource locacaoResource = new LocacaoResource(locacaoRepository);
        this.restLocacaoMockMvc = MockMvcBuilders.standaloneSetup(locacaoResource)
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
    public static Locacao createEntity(EntityManager em) {
        Locacao locacao = new Locacao()
            .inicio(DEFAULT_INICIO)
            .duracao(DEFAULT_DURACAO)
            .valor(DEFAULT_VALOR);
        return locacao;
    }

    @Before
    public void initTest() {
        locacao = createEntity(em);
    }

    @Test
    @Transactional
    public void createLocacao() throws Exception {
        int databaseSizeBeforeCreate = locacaoRepository.findAll().size();

        // Create the Locacao
        restLocacaoMockMvc.perform(post("/api/locacaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(locacao)))
            .andExpect(status().isCreated());

        // Validate the Locacao in the database
        List<Locacao> locacaoList = locacaoRepository.findAll();
        assertThat(locacaoList).hasSize(databaseSizeBeforeCreate + 1);
        Locacao testLocacao = locacaoList.get(locacaoList.size() - 1);
        assertThat(testLocacao.getInicio()).isEqualTo(DEFAULT_INICIO);
        assertThat(testLocacao.getDuracao()).isEqualTo(DEFAULT_DURACAO);
        assertThat(testLocacao.getValor()).isEqualTo(DEFAULT_VALOR);
    }

    @Test
    @Transactional
    public void createLocacaoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = locacaoRepository.findAll().size();

        // Create the Locacao with an existing ID
        locacao.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLocacaoMockMvc.perform(post("/api/locacaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(locacao)))
            .andExpect(status().isBadRequest());

        // Validate the Locacao in the database
        List<Locacao> locacaoList = locacaoRepository.findAll();
        assertThat(locacaoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkInicioIsRequired() throws Exception {
        int databaseSizeBeforeTest = locacaoRepository.findAll().size();
        // set the field null
        locacao.setInicio(null);

        // Create the Locacao, which fails.

        restLocacaoMockMvc.perform(post("/api/locacaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(locacao)))
            .andExpect(status().isBadRequest());

        List<Locacao> locacaoList = locacaoRepository.findAll();
        assertThat(locacaoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDuracaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = locacaoRepository.findAll().size();
        // set the field null
        locacao.setDuracao(null);

        // Create the Locacao, which fails.

        restLocacaoMockMvc.perform(post("/api/locacaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(locacao)))
            .andExpect(status().isBadRequest());

        List<Locacao> locacaoList = locacaoRepository.findAll();
        assertThat(locacaoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValorIsRequired() throws Exception {
        int databaseSizeBeforeTest = locacaoRepository.findAll().size();
        // set the field null
        locacao.setValor(null);

        // Create the Locacao, which fails.

        restLocacaoMockMvc.perform(post("/api/locacaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(locacao)))
            .andExpect(status().isBadRequest());

        List<Locacao> locacaoList = locacaoRepository.findAll();
        assertThat(locacaoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllLocacaos() throws Exception {
        // Initialize the database
        locacaoRepository.saveAndFlush(locacao);

        // Get all the locacaoList
        restLocacaoMockMvc.perform(get("/api/locacaos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(locacao.getId().intValue())))
            .andExpect(jsonPath("$.[*].inicio").value(hasItem(DEFAULT_INICIO.toString())))
            .andExpect(jsonPath("$.[*].duracao").value(hasItem(DEFAULT_DURACAO)))
            .andExpect(jsonPath("$.[*].valor").value(hasItem(DEFAULT_VALOR)));
    }
    
    @Test
    @Transactional
    public void getLocacao() throws Exception {
        // Initialize the database
        locacaoRepository.saveAndFlush(locacao);

        // Get the locacao
        restLocacaoMockMvc.perform(get("/api/locacaos/{id}", locacao.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(locacao.getId().intValue()))
            .andExpect(jsonPath("$.inicio").value(DEFAULT_INICIO.toString()))
            .andExpect(jsonPath("$.duracao").value(DEFAULT_DURACAO))
            .andExpect(jsonPath("$.valor").value(DEFAULT_VALOR));
    }

    @Test
    @Transactional
    public void getNonExistingLocacao() throws Exception {
        // Get the locacao
        restLocacaoMockMvc.perform(get("/api/locacaos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLocacao() throws Exception {
        // Initialize the database
        locacaoRepository.saveAndFlush(locacao);

        int databaseSizeBeforeUpdate = locacaoRepository.findAll().size();

        // Update the locacao
        Locacao updatedLocacao = locacaoRepository.findById(locacao.getId()).get();
        // Disconnect from session so that the updates on updatedLocacao are not directly saved in db
        em.detach(updatedLocacao);
        updatedLocacao
            .inicio(UPDATED_INICIO)
            .duracao(UPDATED_DURACAO)
            .valor(UPDATED_VALOR);

        restLocacaoMockMvc.perform(put("/api/locacaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedLocacao)))
            .andExpect(status().isOk());

        // Validate the Locacao in the database
        List<Locacao> locacaoList = locacaoRepository.findAll();
        assertThat(locacaoList).hasSize(databaseSizeBeforeUpdate);
        Locacao testLocacao = locacaoList.get(locacaoList.size() - 1);
        assertThat(testLocacao.getInicio()).isEqualTo(UPDATED_INICIO);
        assertThat(testLocacao.getDuracao()).isEqualTo(UPDATED_DURACAO);
        assertThat(testLocacao.getValor()).isEqualTo(UPDATED_VALOR);
    }

    @Test
    @Transactional
    public void updateNonExistingLocacao() throws Exception {
        int databaseSizeBeforeUpdate = locacaoRepository.findAll().size();

        // Create the Locacao

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLocacaoMockMvc.perform(put("/api/locacaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(locacao)))
            .andExpect(status().isBadRequest());

        // Validate the Locacao in the database
        List<Locacao> locacaoList = locacaoRepository.findAll();
        assertThat(locacaoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteLocacao() throws Exception {
        // Initialize the database
        locacaoRepository.saveAndFlush(locacao);

        int databaseSizeBeforeDelete = locacaoRepository.findAll().size();

        // Get the locacao
        restLocacaoMockMvc.perform(delete("/api/locacaos/{id}", locacao.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Locacao> locacaoList = locacaoRepository.findAll();
        assertThat(locacaoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Locacao.class);
        Locacao locacao1 = new Locacao();
        locacao1.setId(1L);
        Locacao locacao2 = new Locacao();
        locacao2.setId(locacao1.getId());
        assertThat(locacao1).isEqualTo(locacao2);
        locacao2.setId(2L);
        assertThat(locacao1).isNotEqualTo(locacao2);
        locacao1.setId(null);
        assertThat(locacao1).isNotEqualTo(locacao2);
    }
}
