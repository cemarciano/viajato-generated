package br.com.viajato.web.rest;

import br.com.viajato.ViajatoApp;

import br.com.viajato.domain.Aeroporto;
import br.com.viajato.repository.AeroportoRepository;
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
 * Test class for the AeroportoResource REST controller.
 *
 * @see AeroportoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ViajatoApp.class)
public class AeroportoResourceIntTest {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_CODIGO = "AAAAAAAAAA";
    private static final String UPDATED_CODIGO = "BBBBBBBBBB";

    @Autowired
    private AeroportoRepository aeroportoRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAeroportoMockMvc;

    private Aeroporto aeroporto;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AeroportoResource aeroportoResource = new AeroportoResource(aeroportoRepository);
        this.restAeroportoMockMvc = MockMvcBuilders.standaloneSetup(aeroportoResource)
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
    public static Aeroporto createEntity(EntityManager em) {
        Aeroporto aeroporto = new Aeroporto()
            .nome(DEFAULT_NOME)
            .codigo(DEFAULT_CODIGO);
        return aeroporto;
    }

    @Before
    public void initTest() {
        aeroporto = createEntity(em);
    }

    @Test
    @Transactional
    public void createAeroporto() throws Exception {
        int databaseSizeBeforeCreate = aeroportoRepository.findAll().size();

        // Create the Aeroporto
        restAeroportoMockMvc.perform(post("/api/aeroportos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(aeroporto)))
            .andExpect(status().isCreated());

        // Validate the Aeroporto in the database
        List<Aeroporto> aeroportoList = aeroportoRepository.findAll();
        assertThat(aeroportoList).hasSize(databaseSizeBeforeCreate + 1);
        Aeroporto testAeroporto = aeroportoList.get(aeroportoList.size() - 1);
        assertThat(testAeroporto.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testAeroporto.getCodigo()).isEqualTo(DEFAULT_CODIGO);
    }

    @Test
    @Transactional
    public void createAeroportoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = aeroportoRepository.findAll().size();

        // Create the Aeroporto with an existing ID
        aeroporto.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAeroportoMockMvc.perform(post("/api/aeroportos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(aeroporto)))
            .andExpect(status().isBadRequest());

        // Validate the Aeroporto in the database
        List<Aeroporto> aeroportoList = aeroportoRepository.findAll();
        assertThat(aeroportoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = aeroportoRepository.findAll().size();
        // set the field null
        aeroporto.setNome(null);

        // Create the Aeroporto, which fails.

        restAeroportoMockMvc.perform(post("/api/aeroportos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(aeroporto)))
            .andExpect(status().isBadRequest());

        List<Aeroporto> aeroportoList = aeroportoRepository.findAll();
        assertThat(aeroportoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCodigoIsRequired() throws Exception {
        int databaseSizeBeforeTest = aeroportoRepository.findAll().size();
        // set the field null
        aeroporto.setCodigo(null);

        // Create the Aeroporto, which fails.

        restAeroportoMockMvc.perform(post("/api/aeroportos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(aeroporto)))
            .andExpect(status().isBadRequest());

        List<Aeroporto> aeroportoList = aeroportoRepository.findAll();
        assertThat(aeroportoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAeroportos() throws Exception {
        // Initialize the database
        aeroportoRepository.saveAndFlush(aeroporto);

        // Get all the aeroportoList
        restAeroportoMockMvc.perform(get("/api/aeroportos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(aeroporto.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())))
            .andExpect(jsonPath("$.[*].codigo").value(hasItem(DEFAULT_CODIGO.toString())));
    }
    
    @Test
    @Transactional
    public void getAeroporto() throws Exception {
        // Initialize the database
        aeroportoRepository.saveAndFlush(aeroporto);

        // Get the aeroporto
        restAeroportoMockMvc.perform(get("/api/aeroportos/{id}", aeroporto.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(aeroporto.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME.toString()))
            .andExpect(jsonPath("$.codigo").value(DEFAULT_CODIGO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAeroporto() throws Exception {
        // Get the aeroporto
        restAeroportoMockMvc.perform(get("/api/aeroportos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAeroporto() throws Exception {
        // Initialize the database
        aeroportoRepository.saveAndFlush(aeroporto);

        int databaseSizeBeforeUpdate = aeroportoRepository.findAll().size();

        // Update the aeroporto
        Aeroporto updatedAeroporto = aeroportoRepository.findById(aeroporto.getId()).get();
        // Disconnect from session so that the updates on updatedAeroporto are not directly saved in db
        em.detach(updatedAeroporto);
        updatedAeroporto
            .nome(UPDATED_NOME)
            .codigo(UPDATED_CODIGO);

        restAeroportoMockMvc.perform(put("/api/aeroportos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAeroporto)))
            .andExpect(status().isOk());

        // Validate the Aeroporto in the database
        List<Aeroporto> aeroportoList = aeroportoRepository.findAll();
        assertThat(aeroportoList).hasSize(databaseSizeBeforeUpdate);
        Aeroporto testAeroporto = aeroportoList.get(aeroportoList.size() - 1);
        assertThat(testAeroporto.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testAeroporto.getCodigo()).isEqualTo(UPDATED_CODIGO);
    }

    @Test
    @Transactional
    public void updateNonExistingAeroporto() throws Exception {
        int databaseSizeBeforeUpdate = aeroportoRepository.findAll().size();

        // Create the Aeroporto

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAeroportoMockMvc.perform(put("/api/aeroportos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(aeroporto)))
            .andExpect(status().isBadRequest());

        // Validate the Aeroporto in the database
        List<Aeroporto> aeroportoList = aeroportoRepository.findAll();
        assertThat(aeroportoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAeroporto() throws Exception {
        // Initialize the database
        aeroportoRepository.saveAndFlush(aeroporto);

        int databaseSizeBeforeDelete = aeroportoRepository.findAll().size();

        // Get the aeroporto
        restAeroportoMockMvc.perform(delete("/api/aeroportos/{id}", aeroporto.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Aeroporto> aeroportoList = aeroportoRepository.findAll();
        assertThat(aeroportoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Aeroporto.class);
        Aeroporto aeroporto1 = new Aeroporto();
        aeroporto1.setId(1L);
        Aeroporto aeroporto2 = new Aeroporto();
        aeroporto2.setId(aeroporto1.getId());
        assertThat(aeroporto1).isEqualTo(aeroporto2);
        aeroporto2.setId(2L);
        assertThat(aeroporto1).isNotEqualTo(aeroporto2);
        aeroporto1.setId(null);
        assertThat(aeroporto1).isNotEqualTo(aeroporto2);
    }
}
