package br.com.viajato.web.rest;

import br.com.viajato.ViajatoApp;

import br.com.viajato.domain.Seguradora;
import br.com.viajato.repository.SeguradoraRepository;
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
 * Test class for the SeguradoraResource REST controller.
 *
 * @see SeguradoraResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ViajatoApp.class)
public class SeguradoraResourceIntTest {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    @Autowired
    private SeguradoraRepository seguradoraRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSeguradoraMockMvc;

    private Seguradora seguradora;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SeguradoraResource seguradoraResource = new SeguradoraResource(seguradoraRepository);
        this.restSeguradoraMockMvc = MockMvcBuilders.standaloneSetup(seguradoraResource)
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
    public static Seguradora createEntity(EntityManager em) {
        Seguradora seguradora = new Seguradora()
            .nome(DEFAULT_NOME);
        return seguradora;
    }

    @Before
    public void initTest() {
        seguradora = createEntity(em);
    }

    @Test
    @Transactional
    public void createSeguradora() throws Exception {
        int databaseSizeBeforeCreate = seguradoraRepository.findAll().size();

        // Create the Seguradora
        restSeguradoraMockMvc.perform(post("/api/seguradoras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(seguradora)))
            .andExpect(status().isCreated());

        // Validate the Seguradora in the database
        List<Seguradora> seguradoraList = seguradoraRepository.findAll();
        assertThat(seguradoraList).hasSize(databaseSizeBeforeCreate + 1);
        Seguradora testSeguradora = seguradoraList.get(seguradoraList.size() - 1);
        assertThat(testSeguradora.getNome()).isEqualTo(DEFAULT_NOME);
    }

    @Test
    @Transactional
    public void createSeguradoraWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = seguradoraRepository.findAll().size();

        // Create the Seguradora with an existing ID
        seguradora.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSeguradoraMockMvc.perform(post("/api/seguradoras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(seguradora)))
            .andExpect(status().isBadRequest());

        // Validate the Seguradora in the database
        List<Seguradora> seguradoraList = seguradoraRepository.findAll();
        assertThat(seguradoraList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = seguradoraRepository.findAll().size();
        // set the field null
        seguradora.setNome(null);

        // Create the Seguradora, which fails.

        restSeguradoraMockMvc.perform(post("/api/seguradoras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(seguradora)))
            .andExpect(status().isBadRequest());

        List<Seguradora> seguradoraList = seguradoraRepository.findAll();
        assertThat(seguradoraList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSeguradoras() throws Exception {
        // Initialize the database
        seguradoraRepository.saveAndFlush(seguradora);

        // Get all the seguradoraList
        restSeguradoraMockMvc.perform(get("/api/seguradoras?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(seguradora.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())));
    }
    
    @Test
    @Transactional
    public void getSeguradora() throws Exception {
        // Initialize the database
        seguradoraRepository.saveAndFlush(seguradora);

        // Get the seguradora
        restSeguradoraMockMvc.perform(get("/api/seguradoras/{id}", seguradora.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(seguradora.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSeguradora() throws Exception {
        // Get the seguradora
        restSeguradoraMockMvc.perform(get("/api/seguradoras/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSeguradora() throws Exception {
        // Initialize the database
        seguradoraRepository.saveAndFlush(seguradora);

        int databaseSizeBeforeUpdate = seguradoraRepository.findAll().size();

        // Update the seguradora
        Seguradora updatedSeguradora = seguradoraRepository.findById(seguradora.getId()).get();
        // Disconnect from session so that the updates on updatedSeguradora are not directly saved in db
        em.detach(updatedSeguradora);
        updatedSeguradora
            .nome(UPDATED_NOME);

        restSeguradoraMockMvc.perform(put("/api/seguradoras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSeguradora)))
            .andExpect(status().isOk());

        // Validate the Seguradora in the database
        List<Seguradora> seguradoraList = seguradoraRepository.findAll();
        assertThat(seguradoraList).hasSize(databaseSizeBeforeUpdate);
        Seguradora testSeguradora = seguradoraList.get(seguradoraList.size() - 1);
        assertThat(testSeguradora.getNome()).isEqualTo(UPDATED_NOME);
    }

    @Test
    @Transactional
    public void updateNonExistingSeguradora() throws Exception {
        int databaseSizeBeforeUpdate = seguradoraRepository.findAll().size();

        // Create the Seguradora

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSeguradoraMockMvc.perform(put("/api/seguradoras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(seguradora)))
            .andExpect(status().isBadRequest());

        // Validate the Seguradora in the database
        List<Seguradora> seguradoraList = seguradoraRepository.findAll();
        assertThat(seguradoraList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSeguradora() throws Exception {
        // Initialize the database
        seguradoraRepository.saveAndFlush(seguradora);

        int databaseSizeBeforeDelete = seguradoraRepository.findAll().size();

        // Get the seguradora
        restSeguradoraMockMvc.perform(delete("/api/seguradoras/{id}", seguradora.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Seguradora> seguradoraList = seguradoraRepository.findAll();
        assertThat(seguradoraList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Seguradora.class);
        Seguradora seguradora1 = new Seguradora();
        seguradora1.setId(1L);
        Seguradora seguradora2 = new Seguradora();
        seguradora2.setId(seguradora1.getId());
        assertThat(seguradora1).isEqualTo(seguradora2);
        seguradora2.setId(2L);
        assertThat(seguradora1).isNotEqualTo(seguradora2);
        seguradora1.setId(null);
        assertThat(seguradora1).isNotEqualTo(seguradora2);
    }
}
