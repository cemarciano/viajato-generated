package br.com.viajato.web.rest;

import com.codahale.metrics.annotation.Timed;
import br.com.viajato.domain.Carro;
import br.com.viajato.repository.CarroRepository;
import br.com.viajato.web.rest.errors.BadRequestAlertException;
import br.com.viajato.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Carro.
 */
@RestController
@RequestMapping("/api")
public class CarroResource {

    private final Logger log = LoggerFactory.getLogger(CarroResource.class);

    private static final String ENTITY_NAME = "carro";

    private final CarroRepository carroRepository;

    public CarroResource(CarroRepository carroRepository) {
        this.carroRepository = carroRepository;
    }

    /**
     * POST  /carros : Create a new carro.
     *
     * @param carro the carro to create
     * @return the ResponseEntity with status 201 (Created) and with body the new carro, or with status 400 (Bad Request) if the carro has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/carros")
    @Timed
    public ResponseEntity<Carro> createCarro(@Valid @RequestBody Carro carro) throws URISyntaxException {
        log.debug("REST request to save Carro : {}", carro);
        if (carro.getId() != null) {
            throw new BadRequestAlertException("A new carro cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Carro result = carroRepository.save(carro);
        return ResponseEntity.created(new URI("/api/carros/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /carros : Updates an existing carro.
     *
     * @param carro the carro to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated carro,
     * or with status 400 (Bad Request) if the carro is not valid,
     * or with status 500 (Internal Server Error) if the carro couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/carros")
    @Timed
    public ResponseEntity<Carro> updateCarro(@Valid @RequestBody Carro carro) throws URISyntaxException {
        log.debug("REST request to update Carro : {}", carro);
        if (carro.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Carro result = carroRepository.save(carro);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, carro.getId().toString()))
            .body(result);
    }

    /**
     * GET  /carros : get all the carros.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of carros in body
     */
    @GetMapping("/carros")
    @Timed
    public List<Carro> getAllCarros() {
        log.debug("REST request to get all Carros");
        return carroRepository.findAll();
    }

    /**
     * GET  /carros/:id : get the "id" carro.
     *
     * @param id the id of the carro to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the carro, or with status 404 (Not Found)
     */
    @GetMapping("/carros/{id}")
    @Timed
    public ResponseEntity<Carro> getCarro(@PathVariable Long id) {
        log.debug("REST request to get Carro : {}", id);
        Optional<Carro> carro = carroRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(carro);
    }

    /**
     * DELETE  /carros/:id : delete the "id" carro.
     *
     * @param id the id of the carro to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/carros/{id}")
    @Timed
    public ResponseEntity<Void> deleteCarro(@PathVariable Long id) {
        log.debug("REST request to delete Carro : {}", id);

        carroRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
