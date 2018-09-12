package br.com.viajato.web.rest;

import br.com.viajato.ViajatoApp;

import br.com.viajato.domain.Hotel;
import br.com.viajato.repository.HotelRepository;
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
 * Test class for the HotelResource REST controller.
 *
 * @see HotelResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ViajatoApp.class)
public class HotelResourceIntTest {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final Integer DEFAULT_NOTA = 1;
    private static final Integer UPDATED_NOTA = 2;

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restHotelMockMvc;

    private Hotel hotel;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final HotelResource hotelResource = new HotelResource(hotelRepository);
        this.restHotelMockMvc = MockMvcBuilders.standaloneSetup(hotelResource)
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
    public static Hotel createEntity(EntityManager em) {
        Hotel hotel = new Hotel()
            .nome(DEFAULT_NOME)
            .nota(DEFAULT_NOTA);
        return hotel;
    }

    @Before
    public void initTest() {
        hotel = createEntity(em);
    }

    @Test
    @Transactional
    public void createHotel() throws Exception {
        int databaseSizeBeforeCreate = hotelRepository.findAll().size();

        // Create the Hotel
        restHotelMockMvc.perform(post("/api/hotels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hotel)))
            .andExpect(status().isCreated());

        // Validate the Hotel in the database
        List<Hotel> hotelList = hotelRepository.findAll();
        assertThat(hotelList).hasSize(databaseSizeBeforeCreate + 1);
        Hotel testHotel = hotelList.get(hotelList.size() - 1);
        assertThat(testHotel.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testHotel.getNota()).isEqualTo(DEFAULT_NOTA);
    }

    @Test
    @Transactional
    public void createHotelWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = hotelRepository.findAll().size();

        // Create the Hotel with an existing ID
        hotel.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restHotelMockMvc.perform(post("/api/hotels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hotel)))
            .andExpect(status().isBadRequest());

        // Validate the Hotel in the database
        List<Hotel> hotelList = hotelRepository.findAll();
        assertThat(hotelList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = hotelRepository.findAll().size();
        // set the field null
        hotel.setNome(null);

        // Create the Hotel, which fails.

        restHotelMockMvc.perform(post("/api/hotels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hotel)))
            .andExpect(status().isBadRequest());

        List<Hotel> hotelList = hotelRepository.findAll();
        assertThat(hotelList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNotaIsRequired() throws Exception {
        int databaseSizeBeforeTest = hotelRepository.findAll().size();
        // set the field null
        hotel.setNota(null);

        // Create the Hotel, which fails.

        restHotelMockMvc.perform(post("/api/hotels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hotel)))
            .andExpect(status().isBadRequest());

        List<Hotel> hotelList = hotelRepository.findAll();
        assertThat(hotelList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllHotels() throws Exception {
        // Initialize the database
        hotelRepository.saveAndFlush(hotel);

        // Get all the hotelList
        restHotelMockMvc.perform(get("/api/hotels?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(hotel.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())))
            .andExpect(jsonPath("$.[*].nota").value(hasItem(DEFAULT_NOTA)));
    }
    
    @Test
    @Transactional
    public void getHotel() throws Exception {
        // Initialize the database
        hotelRepository.saveAndFlush(hotel);

        // Get the hotel
        restHotelMockMvc.perform(get("/api/hotels/{id}", hotel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(hotel.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME.toString()))
            .andExpect(jsonPath("$.nota").value(DEFAULT_NOTA));
    }

    @Test
    @Transactional
    public void getNonExistingHotel() throws Exception {
        // Get the hotel
        restHotelMockMvc.perform(get("/api/hotels/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHotel() throws Exception {
        // Initialize the database
        hotelRepository.saveAndFlush(hotel);

        int databaseSizeBeforeUpdate = hotelRepository.findAll().size();

        // Update the hotel
        Hotel updatedHotel = hotelRepository.findById(hotel.getId()).get();
        // Disconnect from session so that the updates on updatedHotel are not directly saved in db
        em.detach(updatedHotel);
        updatedHotel
            .nome(UPDATED_NOME)
            .nota(UPDATED_NOTA);

        restHotelMockMvc.perform(put("/api/hotels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedHotel)))
            .andExpect(status().isOk());

        // Validate the Hotel in the database
        List<Hotel> hotelList = hotelRepository.findAll();
        assertThat(hotelList).hasSize(databaseSizeBeforeUpdate);
        Hotel testHotel = hotelList.get(hotelList.size() - 1);
        assertThat(testHotel.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testHotel.getNota()).isEqualTo(UPDATED_NOTA);
    }

    @Test
    @Transactional
    public void updateNonExistingHotel() throws Exception {
        int databaseSizeBeforeUpdate = hotelRepository.findAll().size();

        // Create the Hotel

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHotelMockMvc.perform(put("/api/hotels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hotel)))
            .andExpect(status().isBadRequest());

        // Validate the Hotel in the database
        List<Hotel> hotelList = hotelRepository.findAll();
        assertThat(hotelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteHotel() throws Exception {
        // Initialize the database
        hotelRepository.saveAndFlush(hotel);

        int databaseSizeBeforeDelete = hotelRepository.findAll().size();

        // Get the hotel
        restHotelMockMvc.perform(delete("/api/hotels/{id}", hotel.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Hotel> hotelList = hotelRepository.findAll();
        assertThat(hotelList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Hotel.class);
        Hotel hotel1 = new Hotel();
        hotel1.setId(1L);
        Hotel hotel2 = new Hotel();
        hotel2.setId(hotel1.getId());
        assertThat(hotel1).isEqualTo(hotel2);
        hotel2.setId(2L);
        assertThat(hotel1).isNotEqualTo(hotel2);
        hotel1.setId(null);
        assertThat(hotel1).isNotEqualTo(hotel2);
    }
}
