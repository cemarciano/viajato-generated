package br.com.viajato.web.rest;

import br.com.viajato.ViajatoApp;

import br.com.viajato.domain.Voo;
import br.com.viajato.repository.VooRepository;
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
 * Test class for the VooResource REST controller.
 *
 * @see VooResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ViajatoApp.class)
public class VooResourceIntTest {

    private static final Integer DEFAULT_NUMERO = 1;
    private static final Integer UPDATED_NUMERO = 2;

    private static final String DEFAULT_PARTIDA = "AAAAAAAAAA";
    private static final String UPDATED_PARTIDA = "BBBBBBBBBB";

    private static final String DEFAULT_CHEGADA = "AAAAAAAAAA";
    private static final String UPDATED_CHEGADA = "BBBBBBBBBB";

    @Autowired
    private VooRepository vooRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restVooMockMvc;

    private Voo voo;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final VooResource vooResource = new VooResource(vooRepository);
        this.restVooMockMvc = MockMvcBuilders.standaloneSetup(vooResource)
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
    public static Voo createEntity(EntityManager em) {
        Voo voo = new Voo()
            .numero(DEFAULT_NUMERO)
            .partida(DEFAULT_PARTIDA)
            .chegada(DEFAULT_CHEGADA);
        return voo;
    }

    @Before
    public void initTest() {
        voo = createEntity(em);
    }

    @Test
    @Transactional
    public void createVoo() throws Exception {
        int databaseSizeBeforeCreate = vooRepository.findAll().size();

        // Create the Voo
        restVooMockMvc.perform(post("/api/voos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(voo)))
            .andExpect(status().isCreated());

        // Validate the Voo in the database
        List<Voo> vooList = vooRepository.findAll();
        assertThat(vooList).hasSize(databaseSizeBeforeCreate + 1);
        Voo testVoo = vooList.get(vooList.size() - 1);
        assertThat(testVoo.getNumero()).isEqualTo(DEFAULT_NUMERO);
        assertThat(testVoo.getPartida()).isEqualTo(DEFAULT_PARTIDA);
        assertThat(testVoo.getChegada()).isEqualTo(DEFAULT_CHEGADA);
    }

    @Test
    @Transactional
    public void createVooWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = vooRepository.findAll().size();

        // Create the Voo with an existing ID
        voo.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVooMockMvc.perform(post("/api/voos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(voo)))
            .andExpect(status().isBadRequest());

        // Validate the Voo in the database
        List<Voo> vooList = vooRepository.findAll();
        assertThat(vooList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNumeroIsRequired() throws Exception {
        int databaseSizeBeforeTest = vooRepository.findAll().size();
        // set the field null
        voo.setNumero(null);

        // Create the Voo, which fails.

        restVooMockMvc.perform(post("/api/voos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(voo)))
            .andExpect(status().isBadRequest());

        List<Voo> vooList = vooRepository.findAll();
        assertThat(vooList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPartidaIsRequired() throws Exception {
        int databaseSizeBeforeTest = vooRepository.findAll().size();
        // set the field null
        voo.setPartida(null);

        // Create the Voo, which fails.

        restVooMockMvc.perform(post("/api/voos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(voo)))
            .andExpect(status().isBadRequest());

        List<Voo> vooList = vooRepository.findAll();
        assertThat(vooList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkChegadaIsRequired() throws Exception {
        int databaseSizeBeforeTest = vooRepository.findAll().size();
        // set the field null
        voo.setChegada(null);

        // Create the Voo, which fails.

        restVooMockMvc.perform(post("/api/voos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(voo)))
            .andExpect(status().isBadRequest());

        List<Voo> vooList = vooRepository.findAll();
        assertThat(vooList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllVoos() throws Exception {
        // Initialize the database
        vooRepository.saveAndFlush(voo);

        // Get all the vooList
        restVooMockMvc.perform(get("/api/voos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(voo.getId().intValue())))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO)))
            .andExpect(jsonPath("$.[*].partida").value(hasItem(DEFAULT_PARTIDA.toString())))
            .andExpect(jsonPath("$.[*].chegada").value(hasItem(DEFAULT_CHEGADA.toString())));
    }
    
    @Test
    @Transactional
    public void getVoo() throws Exception {
        // Initialize the database
        vooRepository.saveAndFlush(voo);

        // Get the voo
        restVooMockMvc.perform(get("/api/voos/{id}", voo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(voo.getId().intValue()))
            .andExpect(jsonPath("$.numero").value(DEFAULT_NUMERO))
            .andExpect(jsonPath("$.partida").value(DEFAULT_PARTIDA.toString()))
            .andExpect(jsonPath("$.chegada").value(DEFAULT_CHEGADA.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingVoo() throws Exception {
        // Get the voo
        restVooMockMvc.perform(get("/api/voos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVoo() throws Exception {
        // Initialize the database
        vooRepository.saveAndFlush(voo);

        int databaseSizeBeforeUpdate = vooRepository.findAll().size();

        // Update the voo
        Voo updatedVoo = vooRepository.findById(voo.getId()).get();
        // Disconnect from session so that the updates on updatedVoo are not directly saved in db
        em.detach(updatedVoo);
        updatedVoo
            .numero(UPDATED_NUMERO)
            .partida(UPDATED_PARTIDA)
            .chegada(UPDATED_CHEGADA);

        restVooMockMvc.perform(put("/api/voos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedVoo)))
            .andExpect(status().isOk());

        // Validate the Voo in the database
        List<Voo> vooList = vooRepository.findAll();
        assertThat(vooList).hasSize(databaseSizeBeforeUpdate);
        Voo testVoo = vooList.get(vooList.size() - 1);
        assertThat(testVoo.getNumero()).isEqualTo(UPDATED_NUMERO);
        assertThat(testVoo.getPartida()).isEqualTo(UPDATED_PARTIDA);
        assertThat(testVoo.getChegada()).isEqualTo(UPDATED_CHEGADA);
    }

    @Test
    @Transactional
    public void updateNonExistingVoo() throws Exception {
        int databaseSizeBeforeUpdate = vooRepository.findAll().size();

        // Create the Voo

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVooMockMvc.perform(put("/api/voos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(voo)))
            .andExpect(status().isBadRequest());

        // Validate the Voo in the database
        List<Voo> vooList = vooRepository.findAll();
        assertThat(vooList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteVoo() throws Exception {
        // Initialize the database
        vooRepository.saveAndFlush(voo);

        int databaseSizeBeforeDelete = vooRepository.findAll().size();

        // Get the voo
        restVooMockMvc.perform(delete("/api/voos/{id}", voo.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Voo> vooList = vooRepository.findAll();
        assertThat(vooList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Voo.class);
        Voo voo1 = new Voo();
        voo1.setId(1L);
        Voo voo2 = new Voo();
        voo2.setId(voo1.getId());
        assertThat(voo1).isEqualTo(voo2);
        voo2.setId(2L);
        assertThat(voo1).isNotEqualTo(voo2);
        voo1.setId(null);
        assertThat(voo1).isNotEqualTo(voo2);
    }
}
