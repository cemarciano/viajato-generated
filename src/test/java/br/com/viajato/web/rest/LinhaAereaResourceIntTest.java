package br.com.viajato.web.rest;

import br.com.viajato.ViajatoApp;

import br.com.viajato.domain.LinhaAerea;
import br.com.viajato.repository.LinhaAereaRepository;
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
 * Test class for the LinhaAereaResource REST controller.
 *
 * @see LinhaAereaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ViajatoApp.class)
public class LinhaAereaResourceIntTest {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_CODIGO = "AAAAAAAAAA";
    private static final String UPDATED_CODIGO = "BBBBBBBBBB";

    @Autowired
    private LinhaAereaRepository linhaAereaRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restLinhaAereaMockMvc;

    private LinhaAerea linhaAerea;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final LinhaAereaResource linhaAereaResource = new LinhaAereaResource(linhaAereaRepository);
        this.restLinhaAereaMockMvc = MockMvcBuilders.standaloneSetup(linhaAereaResource)
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
    public static LinhaAerea createEntity(EntityManager em) {
        LinhaAerea linhaAerea = new LinhaAerea()
            .nome(DEFAULT_NOME)
            .codigo(DEFAULT_CODIGO);
        return linhaAerea;
    }

    @Before
    public void initTest() {
        linhaAerea = createEntity(em);
    }

    @Test
    @Transactional
    public void createLinhaAerea() throws Exception {
        int databaseSizeBeforeCreate = linhaAereaRepository.findAll().size();

        // Create the LinhaAerea
        restLinhaAereaMockMvc.perform(post("/api/linha-aereas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(linhaAerea)))
            .andExpect(status().isCreated());

        // Validate the LinhaAerea in the database
        List<LinhaAerea> linhaAereaList = linhaAereaRepository.findAll();
        assertThat(linhaAereaList).hasSize(databaseSizeBeforeCreate + 1);
        LinhaAerea testLinhaAerea = linhaAereaList.get(linhaAereaList.size() - 1);
        assertThat(testLinhaAerea.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testLinhaAerea.getCodigo()).isEqualTo(DEFAULT_CODIGO);
    }

    @Test
    @Transactional
    public void createLinhaAereaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = linhaAereaRepository.findAll().size();

        // Create the LinhaAerea with an existing ID
        linhaAerea.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLinhaAereaMockMvc.perform(post("/api/linha-aereas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(linhaAerea)))
            .andExpect(status().isBadRequest());

        // Validate the LinhaAerea in the database
        List<LinhaAerea> linhaAereaList = linhaAereaRepository.findAll();
        assertThat(linhaAereaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = linhaAereaRepository.findAll().size();
        // set the field null
        linhaAerea.setNome(null);

        // Create the LinhaAerea, which fails.

        restLinhaAereaMockMvc.perform(post("/api/linha-aereas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(linhaAerea)))
            .andExpect(status().isBadRequest());

        List<LinhaAerea> linhaAereaList = linhaAereaRepository.findAll();
        assertThat(linhaAereaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCodigoIsRequired() throws Exception {
        int databaseSizeBeforeTest = linhaAereaRepository.findAll().size();
        // set the field null
        linhaAerea.setCodigo(null);

        // Create the LinhaAerea, which fails.

        restLinhaAereaMockMvc.perform(post("/api/linha-aereas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(linhaAerea)))
            .andExpect(status().isBadRequest());

        List<LinhaAerea> linhaAereaList = linhaAereaRepository.findAll();
        assertThat(linhaAereaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllLinhaAereas() throws Exception {
        // Initialize the database
        linhaAereaRepository.saveAndFlush(linhaAerea);

        // Get all the linhaAereaList
        restLinhaAereaMockMvc.perform(get("/api/linha-aereas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(linhaAerea.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())))
            .andExpect(jsonPath("$.[*].codigo").value(hasItem(DEFAULT_CODIGO.toString())));
    }
    
    @Test
    @Transactional
    public void getLinhaAerea() throws Exception {
        // Initialize the database
        linhaAereaRepository.saveAndFlush(linhaAerea);

        // Get the linhaAerea
        restLinhaAereaMockMvc.perform(get("/api/linha-aereas/{id}", linhaAerea.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(linhaAerea.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME.toString()))
            .andExpect(jsonPath("$.codigo").value(DEFAULT_CODIGO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingLinhaAerea() throws Exception {
        // Get the linhaAerea
        restLinhaAereaMockMvc.perform(get("/api/linha-aereas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLinhaAerea() throws Exception {
        // Initialize the database
        linhaAereaRepository.saveAndFlush(linhaAerea);

        int databaseSizeBeforeUpdate = linhaAereaRepository.findAll().size();

        // Update the linhaAerea
        LinhaAerea updatedLinhaAerea = linhaAereaRepository.findById(linhaAerea.getId()).get();
        // Disconnect from session so that the updates on updatedLinhaAerea are not directly saved in db
        em.detach(updatedLinhaAerea);
        updatedLinhaAerea
            .nome(UPDATED_NOME)
            .codigo(UPDATED_CODIGO);

        restLinhaAereaMockMvc.perform(put("/api/linha-aereas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedLinhaAerea)))
            .andExpect(status().isOk());

        // Validate the LinhaAerea in the database
        List<LinhaAerea> linhaAereaList = linhaAereaRepository.findAll();
        assertThat(linhaAereaList).hasSize(databaseSizeBeforeUpdate);
        LinhaAerea testLinhaAerea = linhaAereaList.get(linhaAereaList.size() - 1);
        assertThat(testLinhaAerea.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testLinhaAerea.getCodigo()).isEqualTo(UPDATED_CODIGO);
    }

    @Test
    @Transactional
    public void updateNonExistingLinhaAerea() throws Exception {
        int databaseSizeBeforeUpdate = linhaAereaRepository.findAll().size();

        // Create the LinhaAerea

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLinhaAereaMockMvc.perform(put("/api/linha-aereas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(linhaAerea)))
            .andExpect(status().isBadRequest());

        // Validate the LinhaAerea in the database
        List<LinhaAerea> linhaAereaList = linhaAereaRepository.findAll();
        assertThat(linhaAereaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteLinhaAerea() throws Exception {
        // Initialize the database
        linhaAereaRepository.saveAndFlush(linhaAerea);

        int databaseSizeBeforeDelete = linhaAereaRepository.findAll().size();

        // Get the linhaAerea
        restLinhaAereaMockMvc.perform(delete("/api/linha-aereas/{id}", linhaAerea.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<LinhaAerea> linhaAereaList = linhaAereaRepository.findAll();
        assertThat(linhaAereaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LinhaAerea.class);
        LinhaAerea linhaAerea1 = new LinhaAerea();
        linhaAerea1.setId(1L);
        LinhaAerea linhaAerea2 = new LinhaAerea();
        linhaAerea2.setId(linhaAerea1.getId());
        assertThat(linhaAerea1).isEqualTo(linhaAerea2);
        linhaAerea2.setId(2L);
        assertThat(linhaAerea1).isNotEqualTo(linhaAerea2);
        linhaAerea1.setId(null);
        assertThat(linhaAerea1).isNotEqualTo(linhaAerea2);
    }
}
