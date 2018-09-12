package br.com.viajato.web.rest;

import br.com.viajato.ViajatoApp;

import br.com.viajato.domain.Veiculo;
import br.com.viajato.repository.VeiculoRepository;
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
 * Test class for the VeiculoResource REST controller.
 *
 * @see VeiculoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ViajatoApp.class)
public class VeiculoResourceIntTest {

    private static final String DEFAULT_TIPO = "AAAAAAAAAA";
    private static final String UPDATED_TIPO = "BBBBBBBBBB";

    private static final String DEFAULT_FABRICANTE = "AAAAAAAAAA";
    private static final String UPDATED_FABRICANTE = "BBBBBBBBBB";

    private static final String DEFAULT_MODELO = "AAAAAAAAAA";
    private static final String UPDATED_MODELO = "BBBBBBBBBB";

    private static final Integer DEFAULT_ANO_MODELO = 1;
    private static final Integer UPDATED_ANO_MODELO = 2;

    private static final Integer DEFAULT_ANO_FABRICACAO = 1;
    private static final Integer UPDATED_ANO_FABRICACAO = 2;

    private static final String DEFAULT_COR = "AAAAAAAAAA";
    private static final String UPDATED_COR = "BBBBBBBBBB";

    private static final Integer DEFAULT_NUM_PASSAGEIROS = 1;
    private static final Integer UPDATED_NUM_PASSAGEIROS = 2;

    @Autowired
    private VeiculoRepository veiculoRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restVeiculoMockMvc;

    private Veiculo veiculo;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final VeiculoResource veiculoResource = new VeiculoResource(veiculoRepository);
        this.restVeiculoMockMvc = MockMvcBuilders.standaloneSetup(veiculoResource)
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
    public static Veiculo createEntity(EntityManager em) {
        Veiculo veiculo = new Veiculo()
            .tipo(DEFAULT_TIPO)
            .fabricante(DEFAULT_FABRICANTE)
            .modelo(DEFAULT_MODELO)
            .anoModelo(DEFAULT_ANO_MODELO)
            .anoFabricacao(DEFAULT_ANO_FABRICACAO)
            .cor(DEFAULT_COR)
            .numPassageiros(DEFAULT_NUM_PASSAGEIROS);
        return veiculo;
    }

    @Before
    public void initTest() {
        veiculo = createEntity(em);
    }

    @Test
    @Transactional
    public void createVeiculo() throws Exception {
        int databaseSizeBeforeCreate = veiculoRepository.findAll().size();

        // Create the Veiculo
        restVeiculoMockMvc.perform(post("/api/veiculos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(veiculo)))
            .andExpect(status().isCreated());

        // Validate the Veiculo in the database
        List<Veiculo> veiculoList = veiculoRepository.findAll();
        assertThat(veiculoList).hasSize(databaseSizeBeforeCreate + 1);
        Veiculo testVeiculo = veiculoList.get(veiculoList.size() - 1);
        assertThat(testVeiculo.getTipo()).isEqualTo(DEFAULT_TIPO);
        assertThat(testVeiculo.getFabricante()).isEqualTo(DEFAULT_FABRICANTE);
        assertThat(testVeiculo.getModelo()).isEqualTo(DEFAULT_MODELO);
        assertThat(testVeiculo.getAnoModelo()).isEqualTo(DEFAULT_ANO_MODELO);
        assertThat(testVeiculo.getAnoFabricacao()).isEqualTo(DEFAULT_ANO_FABRICACAO);
        assertThat(testVeiculo.getCor()).isEqualTo(DEFAULT_COR);
        assertThat(testVeiculo.getNumPassageiros()).isEqualTo(DEFAULT_NUM_PASSAGEIROS);
    }

    @Test
    @Transactional
    public void createVeiculoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = veiculoRepository.findAll().size();

        // Create the Veiculo with an existing ID
        veiculo.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVeiculoMockMvc.perform(post("/api/veiculos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(veiculo)))
            .andExpect(status().isBadRequest());

        // Validate the Veiculo in the database
        List<Veiculo> veiculoList = veiculoRepository.findAll();
        assertThat(veiculoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkTipoIsRequired() throws Exception {
        int databaseSizeBeforeTest = veiculoRepository.findAll().size();
        // set the field null
        veiculo.setTipo(null);

        // Create the Veiculo, which fails.

        restVeiculoMockMvc.perform(post("/api/veiculos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(veiculo)))
            .andExpect(status().isBadRequest());

        List<Veiculo> veiculoList = veiculoRepository.findAll();
        assertThat(veiculoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFabricanteIsRequired() throws Exception {
        int databaseSizeBeforeTest = veiculoRepository.findAll().size();
        // set the field null
        veiculo.setFabricante(null);

        // Create the Veiculo, which fails.

        restVeiculoMockMvc.perform(post("/api/veiculos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(veiculo)))
            .andExpect(status().isBadRequest());

        List<Veiculo> veiculoList = veiculoRepository.findAll();
        assertThat(veiculoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkModeloIsRequired() throws Exception {
        int databaseSizeBeforeTest = veiculoRepository.findAll().size();
        // set the field null
        veiculo.setModelo(null);

        // Create the Veiculo, which fails.

        restVeiculoMockMvc.perform(post("/api/veiculos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(veiculo)))
            .andExpect(status().isBadRequest());

        List<Veiculo> veiculoList = veiculoRepository.findAll();
        assertThat(veiculoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAnoModeloIsRequired() throws Exception {
        int databaseSizeBeforeTest = veiculoRepository.findAll().size();
        // set the field null
        veiculo.setAnoModelo(null);

        // Create the Veiculo, which fails.

        restVeiculoMockMvc.perform(post("/api/veiculos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(veiculo)))
            .andExpect(status().isBadRequest());

        List<Veiculo> veiculoList = veiculoRepository.findAll();
        assertThat(veiculoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAnoFabricacaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = veiculoRepository.findAll().size();
        // set the field null
        veiculo.setAnoFabricacao(null);

        // Create the Veiculo, which fails.

        restVeiculoMockMvc.perform(post("/api/veiculos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(veiculo)))
            .andExpect(status().isBadRequest());

        List<Veiculo> veiculoList = veiculoRepository.findAll();
        assertThat(veiculoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCorIsRequired() throws Exception {
        int databaseSizeBeforeTest = veiculoRepository.findAll().size();
        // set the field null
        veiculo.setCor(null);

        // Create the Veiculo, which fails.

        restVeiculoMockMvc.perform(post("/api/veiculos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(veiculo)))
            .andExpect(status().isBadRequest());

        List<Veiculo> veiculoList = veiculoRepository.findAll();
        assertThat(veiculoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNumPassageirosIsRequired() throws Exception {
        int databaseSizeBeforeTest = veiculoRepository.findAll().size();
        // set the field null
        veiculo.setNumPassageiros(null);

        // Create the Veiculo, which fails.

        restVeiculoMockMvc.perform(post("/api/veiculos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(veiculo)))
            .andExpect(status().isBadRequest());

        List<Veiculo> veiculoList = veiculoRepository.findAll();
        assertThat(veiculoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllVeiculos() throws Exception {
        // Initialize the database
        veiculoRepository.saveAndFlush(veiculo);

        // Get all the veiculoList
        restVeiculoMockMvc.perform(get("/api/veiculos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(veiculo.getId().intValue())))
            .andExpect(jsonPath("$.[*].tipo").value(hasItem(DEFAULT_TIPO.toString())))
            .andExpect(jsonPath("$.[*].fabricante").value(hasItem(DEFAULT_FABRICANTE.toString())))
            .andExpect(jsonPath("$.[*].modelo").value(hasItem(DEFAULT_MODELO.toString())))
            .andExpect(jsonPath("$.[*].anoModelo").value(hasItem(DEFAULT_ANO_MODELO)))
            .andExpect(jsonPath("$.[*].anoFabricacao").value(hasItem(DEFAULT_ANO_FABRICACAO)))
            .andExpect(jsonPath("$.[*].cor").value(hasItem(DEFAULT_COR.toString())))
            .andExpect(jsonPath("$.[*].numPassageiros").value(hasItem(DEFAULT_NUM_PASSAGEIROS)));
    }
    
    @Test
    @Transactional
    public void getVeiculo() throws Exception {
        // Initialize the database
        veiculoRepository.saveAndFlush(veiculo);

        // Get the veiculo
        restVeiculoMockMvc.perform(get("/api/veiculos/{id}", veiculo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(veiculo.getId().intValue()))
            .andExpect(jsonPath("$.tipo").value(DEFAULT_TIPO.toString()))
            .andExpect(jsonPath("$.fabricante").value(DEFAULT_FABRICANTE.toString()))
            .andExpect(jsonPath("$.modelo").value(DEFAULT_MODELO.toString()))
            .andExpect(jsonPath("$.anoModelo").value(DEFAULT_ANO_MODELO))
            .andExpect(jsonPath("$.anoFabricacao").value(DEFAULT_ANO_FABRICACAO))
            .andExpect(jsonPath("$.cor").value(DEFAULT_COR.toString()))
            .andExpect(jsonPath("$.numPassageiros").value(DEFAULT_NUM_PASSAGEIROS));
    }

    @Test
    @Transactional
    public void getNonExistingVeiculo() throws Exception {
        // Get the veiculo
        restVeiculoMockMvc.perform(get("/api/veiculos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVeiculo() throws Exception {
        // Initialize the database
        veiculoRepository.saveAndFlush(veiculo);

        int databaseSizeBeforeUpdate = veiculoRepository.findAll().size();

        // Update the veiculo
        Veiculo updatedVeiculo = veiculoRepository.findById(veiculo.getId()).get();
        // Disconnect from session so that the updates on updatedVeiculo are not directly saved in db
        em.detach(updatedVeiculo);
        updatedVeiculo
            .tipo(UPDATED_TIPO)
            .fabricante(UPDATED_FABRICANTE)
            .modelo(UPDATED_MODELO)
            .anoModelo(UPDATED_ANO_MODELO)
            .anoFabricacao(UPDATED_ANO_FABRICACAO)
            .cor(UPDATED_COR)
            .numPassageiros(UPDATED_NUM_PASSAGEIROS);

        restVeiculoMockMvc.perform(put("/api/veiculos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedVeiculo)))
            .andExpect(status().isOk());

        // Validate the Veiculo in the database
        List<Veiculo> veiculoList = veiculoRepository.findAll();
        assertThat(veiculoList).hasSize(databaseSizeBeforeUpdate);
        Veiculo testVeiculo = veiculoList.get(veiculoList.size() - 1);
        assertThat(testVeiculo.getTipo()).isEqualTo(UPDATED_TIPO);
        assertThat(testVeiculo.getFabricante()).isEqualTo(UPDATED_FABRICANTE);
        assertThat(testVeiculo.getModelo()).isEqualTo(UPDATED_MODELO);
        assertThat(testVeiculo.getAnoModelo()).isEqualTo(UPDATED_ANO_MODELO);
        assertThat(testVeiculo.getAnoFabricacao()).isEqualTo(UPDATED_ANO_FABRICACAO);
        assertThat(testVeiculo.getCor()).isEqualTo(UPDATED_COR);
        assertThat(testVeiculo.getNumPassageiros()).isEqualTo(UPDATED_NUM_PASSAGEIROS);
    }

    @Test
    @Transactional
    public void updateNonExistingVeiculo() throws Exception {
        int databaseSizeBeforeUpdate = veiculoRepository.findAll().size();

        // Create the Veiculo

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVeiculoMockMvc.perform(put("/api/veiculos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(veiculo)))
            .andExpect(status().isBadRequest());

        // Validate the Veiculo in the database
        List<Veiculo> veiculoList = veiculoRepository.findAll();
        assertThat(veiculoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteVeiculo() throws Exception {
        // Initialize the database
        veiculoRepository.saveAndFlush(veiculo);

        int databaseSizeBeforeDelete = veiculoRepository.findAll().size();

        // Get the veiculo
        restVeiculoMockMvc.perform(delete("/api/veiculos/{id}", veiculo.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Veiculo> veiculoList = veiculoRepository.findAll();
        assertThat(veiculoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Veiculo.class);
        Veiculo veiculo1 = new Veiculo();
        veiculo1.setId(1L);
        Veiculo veiculo2 = new Veiculo();
        veiculo2.setId(veiculo1.getId());
        assertThat(veiculo1).isEqualTo(veiculo2);
        veiculo2.setId(2L);
        assertThat(veiculo1).isNotEqualTo(veiculo2);
        veiculo1.setId(null);
        assertThat(veiculo1).isNotEqualTo(veiculo2);
    }
}
