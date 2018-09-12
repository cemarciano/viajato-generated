package br.com.viajato.web.rest;

import br.com.viajato.ViajatoApp;

import br.com.viajato.domain.Carro;
import br.com.viajato.repository.CarroRepository;
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
 * Test class for the CarroResource REST controller.
 *
 * @see CarroResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ViajatoApp.class)
public class CarroResourceIntTest {

    private static final String DEFAULT_FABRICANTE = "AAAAAAAAAA";
    private static final String UPDATED_FABRICANTE = "BBBBBBBBBB";

    private static final String DEFAULT_MODELO = "AAAAAAAAAA";
    private static final String UPDATED_MODELO = "BBBBBBBBBB";

    private static final String DEFAULT_ANO = "AAAAAAAAAA";
    private static final String UPDATED_ANO = "BBBBBBBBBB";

    private static final String DEFAULT_COR = "AAAAAAAAAA";
    private static final String UPDATED_COR = "BBBBBBBBBB";

    @Autowired
    private CarroRepository carroRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCarroMockMvc;

    private Carro carro;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CarroResource carroResource = new CarroResource(carroRepository);
        this.restCarroMockMvc = MockMvcBuilders.standaloneSetup(carroResource)
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
    public static Carro createEntity(EntityManager em) {
        Carro carro = new Carro()
            .fabricante(DEFAULT_FABRICANTE)
            .modelo(DEFAULT_MODELO)
            .ano(DEFAULT_ANO)
            .cor(DEFAULT_COR);
        return carro;
    }

    @Before
    public void initTest() {
        carro = createEntity(em);
    }

    @Test
    @Transactional
    public void createCarro() throws Exception {
        int databaseSizeBeforeCreate = carroRepository.findAll().size();

        // Create the Carro
        restCarroMockMvc.perform(post("/api/carros")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(carro)))
            .andExpect(status().isCreated());

        // Validate the Carro in the database
        List<Carro> carroList = carroRepository.findAll();
        assertThat(carroList).hasSize(databaseSizeBeforeCreate + 1);
        Carro testCarro = carroList.get(carroList.size() - 1);
        assertThat(testCarro.getFabricante()).isEqualTo(DEFAULT_FABRICANTE);
        assertThat(testCarro.getModelo()).isEqualTo(DEFAULT_MODELO);
        assertThat(testCarro.getAno()).isEqualTo(DEFAULT_ANO);
        assertThat(testCarro.getCor()).isEqualTo(DEFAULT_COR);
    }

    @Test
    @Transactional
    public void createCarroWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = carroRepository.findAll().size();

        // Create the Carro with an existing ID
        carro.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCarroMockMvc.perform(post("/api/carros")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(carro)))
            .andExpect(status().isBadRequest());

        // Validate the Carro in the database
        List<Carro> carroList = carroRepository.findAll();
        assertThat(carroList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkFabricanteIsRequired() throws Exception {
        int databaseSizeBeforeTest = carroRepository.findAll().size();
        // set the field null
        carro.setFabricante(null);

        // Create the Carro, which fails.

        restCarroMockMvc.perform(post("/api/carros")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(carro)))
            .andExpect(status().isBadRequest());

        List<Carro> carroList = carroRepository.findAll();
        assertThat(carroList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkModeloIsRequired() throws Exception {
        int databaseSizeBeforeTest = carroRepository.findAll().size();
        // set the field null
        carro.setModelo(null);

        // Create the Carro, which fails.

        restCarroMockMvc.perform(post("/api/carros")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(carro)))
            .andExpect(status().isBadRequest());

        List<Carro> carroList = carroRepository.findAll();
        assertThat(carroList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAnoIsRequired() throws Exception {
        int databaseSizeBeforeTest = carroRepository.findAll().size();
        // set the field null
        carro.setAno(null);

        // Create the Carro, which fails.

        restCarroMockMvc.perform(post("/api/carros")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(carro)))
            .andExpect(status().isBadRequest());

        List<Carro> carroList = carroRepository.findAll();
        assertThat(carroList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCorIsRequired() throws Exception {
        int databaseSizeBeforeTest = carroRepository.findAll().size();
        // set the field null
        carro.setCor(null);

        // Create the Carro, which fails.

        restCarroMockMvc.perform(post("/api/carros")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(carro)))
            .andExpect(status().isBadRequest());

        List<Carro> carroList = carroRepository.findAll();
        assertThat(carroList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCarros() throws Exception {
        // Initialize the database
        carroRepository.saveAndFlush(carro);

        // Get all the carroList
        restCarroMockMvc.perform(get("/api/carros?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(carro.getId().intValue())))
            .andExpect(jsonPath("$.[*].fabricante").value(hasItem(DEFAULT_FABRICANTE.toString())))
            .andExpect(jsonPath("$.[*].modelo").value(hasItem(DEFAULT_MODELO.toString())))
            .andExpect(jsonPath("$.[*].ano").value(hasItem(DEFAULT_ANO.toString())))
            .andExpect(jsonPath("$.[*].cor").value(hasItem(DEFAULT_COR.toString())));
    }
    
    @Test
    @Transactional
    public void getCarro() throws Exception {
        // Initialize the database
        carroRepository.saveAndFlush(carro);

        // Get the carro
        restCarroMockMvc.perform(get("/api/carros/{id}", carro.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(carro.getId().intValue()))
            .andExpect(jsonPath("$.fabricante").value(DEFAULT_FABRICANTE.toString()))
            .andExpect(jsonPath("$.modelo").value(DEFAULT_MODELO.toString()))
            .andExpect(jsonPath("$.ano").value(DEFAULT_ANO.toString()))
            .andExpect(jsonPath("$.cor").value(DEFAULT_COR.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCarro() throws Exception {
        // Get the carro
        restCarroMockMvc.perform(get("/api/carros/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCarro() throws Exception {
        // Initialize the database
        carroRepository.saveAndFlush(carro);

        int databaseSizeBeforeUpdate = carroRepository.findAll().size();

        // Update the carro
        Carro updatedCarro = carroRepository.findById(carro.getId()).get();
        // Disconnect from session so that the updates on updatedCarro are not directly saved in db
        em.detach(updatedCarro);
        updatedCarro
            .fabricante(UPDATED_FABRICANTE)
            .modelo(UPDATED_MODELO)
            .ano(UPDATED_ANO)
            .cor(UPDATED_COR);

        restCarroMockMvc.perform(put("/api/carros")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCarro)))
            .andExpect(status().isOk());

        // Validate the Carro in the database
        List<Carro> carroList = carroRepository.findAll();
        assertThat(carroList).hasSize(databaseSizeBeforeUpdate);
        Carro testCarro = carroList.get(carroList.size() - 1);
        assertThat(testCarro.getFabricante()).isEqualTo(UPDATED_FABRICANTE);
        assertThat(testCarro.getModelo()).isEqualTo(UPDATED_MODELO);
        assertThat(testCarro.getAno()).isEqualTo(UPDATED_ANO);
        assertThat(testCarro.getCor()).isEqualTo(UPDATED_COR);
    }

    @Test
    @Transactional
    public void updateNonExistingCarro() throws Exception {
        int databaseSizeBeforeUpdate = carroRepository.findAll().size();

        // Create the Carro

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCarroMockMvc.perform(put("/api/carros")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(carro)))
            .andExpect(status().isBadRequest());

        // Validate the Carro in the database
        List<Carro> carroList = carroRepository.findAll();
        assertThat(carroList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCarro() throws Exception {
        // Initialize the database
        carroRepository.saveAndFlush(carro);

        int databaseSizeBeforeDelete = carroRepository.findAll().size();

        // Get the carro
        restCarroMockMvc.perform(delete("/api/carros/{id}", carro.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Carro> carroList = carroRepository.findAll();
        assertThat(carroList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Carro.class);
        Carro carro1 = new Carro();
        carro1.setId(1L);
        Carro carro2 = new Carro();
        carro2.setId(carro1.getId());
        assertThat(carro1).isEqualTo(carro2);
        carro2.setId(2L);
        assertThat(carro1).isNotEqualTo(carro2);
        carro1.setId(null);
        assertThat(carro1).isNotEqualTo(carro2);
    }
}
