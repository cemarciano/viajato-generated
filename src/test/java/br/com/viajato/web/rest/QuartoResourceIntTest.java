package br.com.viajato.web.rest;

import br.com.viajato.ViajatoApp;

import br.com.viajato.domain.Quarto;
import br.com.viajato.repository.QuartoRepository;
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
 * Test class for the QuartoResource REST controller.
 *
 * @see QuartoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ViajatoApp.class)
public class QuartoResourceIntTest {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final Long DEFAULT_CAPACIDADE = 1L;
    private static final Long UPDATED_CAPACIDADE = 2L;

    private static final Long DEFAULT_PRECO = 1L;
    private static final Long UPDATED_PRECO = 2L;

    @Autowired
    private QuartoRepository quartoRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restQuartoMockMvc;

    private Quarto quarto;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final QuartoResource quartoResource = new QuartoResource(quartoRepository);
        this.restQuartoMockMvc = MockMvcBuilders.standaloneSetup(quartoResource)
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
    public static Quarto createEntity(EntityManager em) {
        Quarto quarto = new Quarto()
            .nome(DEFAULT_NOME)
            .capacidade(DEFAULT_CAPACIDADE)
            .preco(DEFAULT_PRECO);
        return quarto;
    }

    @Before
    public void initTest() {
        quarto = createEntity(em);
    }

    @Test
    @Transactional
    public void createQuarto() throws Exception {
        int databaseSizeBeforeCreate = quartoRepository.findAll().size();

        // Create the Quarto
        restQuartoMockMvc.perform(post("/api/quartos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(quarto)))
            .andExpect(status().isCreated());

        // Validate the Quarto in the database
        List<Quarto> quartoList = quartoRepository.findAll();
        assertThat(quartoList).hasSize(databaseSizeBeforeCreate + 1);
        Quarto testQuarto = quartoList.get(quartoList.size() - 1);
        assertThat(testQuarto.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testQuarto.getCapacidade()).isEqualTo(DEFAULT_CAPACIDADE);
        assertThat(testQuarto.getPreco()).isEqualTo(DEFAULT_PRECO);
    }

    @Test
    @Transactional
    public void createQuartoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = quartoRepository.findAll().size();

        // Create the Quarto with an existing ID
        quarto.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restQuartoMockMvc.perform(post("/api/quartos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(quarto)))
            .andExpect(status().isBadRequest());

        // Validate the Quarto in the database
        List<Quarto> quartoList = quartoRepository.findAll();
        assertThat(quartoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = quartoRepository.findAll().size();
        // set the field null
        quarto.setNome(null);

        // Create the Quarto, which fails.

        restQuartoMockMvc.perform(post("/api/quartos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(quarto)))
            .andExpect(status().isBadRequest());

        List<Quarto> quartoList = quartoRepository.findAll();
        assertThat(quartoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCapacidadeIsRequired() throws Exception {
        int databaseSizeBeforeTest = quartoRepository.findAll().size();
        // set the field null
        quarto.setCapacidade(null);

        // Create the Quarto, which fails.

        restQuartoMockMvc.perform(post("/api/quartos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(quarto)))
            .andExpect(status().isBadRequest());

        List<Quarto> quartoList = quartoRepository.findAll();
        assertThat(quartoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPrecoIsRequired() throws Exception {
        int databaseSizeBeforeTest = quartoRepository.findAll().size();
        // set the field null
        quarto.setPreco(null);

        // Create the Quarto, which fails.

        restQuartoMockMvc.perform(post("/api/quartos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(quarto)))
            .andExpect(status().isBadRequest());

        List<Quarto> quartoList = quartoRepository.findAll();
        assertThat(quartoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllQuartos() throws Exception {
        // Initialize the database
        quartoRepository.saveAndFlush(quarto);

        // Get all the quartoList
        restQuartoMockMvc.perform(get("/api/quartos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(quarto.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())))
            .andExpect(jsonPath("$.[*].capacidade").value(hasItem(DEFAULT_CAPACIDADE.intValue())))
            .andExpect(jsonPath("$.[*].preco").value(hasItem(DEFAULT_PRECO.intValue())));
    }
    
    @Test
    @Transactional
    public void getQuarto() throws Exception {
        // Initialize the database
        quartoRepository.saveAndFlush(quarto);

        // Get the quarto
        restQuartoMockMvc.perform(get("/api/quartos/{id}", quarto.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(quarto.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME.toString()))
            .andExpect(jsonPath("$.capacidade").value(DEFAULT_CAPACIDADE.intValue()))
            .andExpect(jsonPath("$.preco").value(DEFAULT_PRECO.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingQuarto() throws Exception {
        // Get the quarto
        restQuartoMockMvc.perform(get("/api/quartos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateQuarto() throws Exception {
        // Initialize the database
        quartoRepository.saveAndFlush(quarto);

        int databaseSizeBeforeUpdate = quartoRepository.findAll().size();

        // Update the quarto
        Quarto updatedQuarto = quartoRepository.findById(quarto.getId()).get();
        // Disconnect from session so that the updates on updatedQuarto are not directly saved in db
        em.detach(updatedQuarto);
        updatedQuarto
            .nome(UPDATED_NOME)
            .capacidade(UPDATED_CAPACIDADE)
            .preco(UPDATED_PRECO);

        restQuartoMockMvc.perform(put("/api/quartos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedQuarto)))
            .andExpect(status().isOk());

        // Validate the Quarto in the database
        List<Quarto> quartoList = quartoRepository.findAll();
        assertThat(quartoList).hasSize(databaseSizeBeforeUpdate);
        Quarto testQuarto = quartoList.get(quartoList.size() - 1);
        assertThat(testQuarto.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testQuarto.getCapacidade()).isEqualTo(UPDATED_CAPACIDADE);
        assertThat(testQuarto.getPreco()).isEqualTo(UPDATED_PRECO);
    }

    @Test
    @Transactional
    public void updateNonExistingQuarto() throws Exception {
        int databaseSizeBeforeUpdate = quartoRepository.findAll().size();

        // Create the Quarto

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restQuartoMockMvc.perform(put("/api/quartos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(quarto)))
            .andExpect(status().isBadRequest());

        // Validate the Quarto in the database
        List<Quarto> quartoList = quartoRepository.findAll();
        assertThat(quartoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteQuarto() throws Exception {
        // Initialize the database
        quartoRepository.saveAndFlush(quarto);

        int databaseSizeBeforeDelete = quartoRepository.findAll().size();

        // Get the quarto
        restQuartoMockMvc.perform(delete("/api/quartos/{id}", quarto.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Quarto> quartoList = quartoRepository.findAll();
        assertThat(quartoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Quarto.class);
        Quarto quarto1 = new Quarto();
        quarto1.setId(1L);
        Quarto quarto2 = new Quarto();
        quarto2.setId(quarto1.getId());
        assertThat(quarto1).isEqualTo(quarto2);
        quarto2.setId(2L);
        assertThat(quarto1).isNotEqualTo(quarto2);
        quarto1.setId(null);
        assertThat(quarto1).isNotEqualTo(quarto2);
    }
}
