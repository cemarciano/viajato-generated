package br.com.viajato.web.rest;

import com.codahale.metrics.annotation.Timed;
import br.com.viajato.domain.Quarto;
import br.com.viajato.repository.QuartoRepository;
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
 * REST controller for managing Quarto.
 */
@RestController
@RequestMapping("/api")
public class QuartoResource {

    private final Logger log = LoggerFactory.getLogger(QuartoResource.class);

    private static final String ENTITY_NAME = "quarto";

    private final QuartoRepository quartoRepository;

    public QuartoResource(QuartoRepository quartoRepository) {
        this.quartoRepository = quartoRepository;
    }

    /**
     * POST  /quartos : Create a new quarto.
     *
     * @param quarto the quarto to create
     * @return the ResponseEntity with status 201 (Created) and with body the new quarto, or with status 400 (Bad Request) if the quarto has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/quartos")
    @Timed
    public ResponseEntity<Quarto> createQuarto(@Valid @RequestBody Quarto quarto) throws URISyntaxException {
        log.debug("REST request to save Quarto : {}", quarto);
        if (quarto.getId() != null) {
            throw new BadRequestAlertException("A new quarto cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Quarto result = quartoRepository.save(quarto);
        return ResponseEntity.created(new URI("/api/quartos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /quartos : Updates an existing quarto.
     *
     * @param quarto the quarto to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated quarto,
     * or with status 400 (Bad Request) if the quarto is not valid,
     * or with status 500 (Internal Server Error) if the quarto couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/quartos")
    @Timed
    public ResponseEntity<Quarto> updateQuarto(@Valid @RequestBody Quarto quarto) throws URISyntaxException {
        log.debug("REST request to update Quarto : {}", quarto);
        if (quarto.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Quarto result = quartoRepository.save(quarto);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, quarto.getId().toString()))
            .body(result);
    }

    /**
     * GET  /quartos : get all the quartos.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of quartos in body
     */
    @GetMapping("/quartos")
    @Timed
    public List<Quarto> getAllQuartos() {
        log.debug("REST request to get all Quartos");
        return quartoRepository.findAll();
    }

    /**
     * GET  /quartos/:id : get the "id" quarto.
     *
     * @param id the id of the quarto to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the quarto, or with status 404 (Not Found)
     */
    @GetMapping("/quartos/{id}")
    @Timed
    public ResponseEntity<Quarto> getQuarto(@PathVariable Long id) {
        log.debug("REST request to get Quarto : {}", id);
        Optional<Quarto> quarto = quartoRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(quarto);
    }

    /**
     * DELETE  /quartos/:id : delete the "id" quarto.
     *
     * @param id the id of the quarto to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/quartos/{id}")
    @Timed
    public ResponseEntity<Void> deleteQuarto(@PathVariable Long id) {
        log.debug("REST request to delete Quarto : {}", id);

        quartoRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
