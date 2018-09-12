package br.com.viajato.web.rest;

import com.codahale.metrics.annotation.Timed;
import br.com.viajato.domain.Locadora;
import br.com.viajato.repository.LocadoraRepository;
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
 * REST controller for managing Locadora.
 */
@RestController
@RequestMapping("/api")
public class LocadoraResource {

    private final Logger log = LoggerFactory.getLogger(LocadoraResource.class);

    private static final String ENTITY_NAME = "locadora";

    private final LocadoraRepository locadoraRepository;

    public LocadoraResource(LocadoraRepository locadoraRepository) {
        this.locadoraRepository = locadoraRepository;
    }

    /**
     * POST  /locadoras : Create a new locadora.
     *
     * @param locadora the locadora to create
     * @return the ResponseEntity with status 201 (Created) and with body the new locadora, or with status 400 (Bad Request) if the locadora has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/locadoras")
    @Timed
    public ResponseEntity<Locadora> createLocadora(@Valid @RequestBody Locadora locadora) throws URISyntaxException {
        log.debug("REST request to save Locadora : {}", locadora);
        if (locadora.getId() != null) {
            throw new BadRequestAlertException("A new locadora cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Locadora result = locadoraRepository.save(locadora);
        return ResponseEntity.created(new URI("/api/locadoras/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /locadoras : Updates an existing locadora.
     *
     * @param locadora the locadora to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated locadora,
     * or with status 400 (Bad Request) if the locadora is not valid,
     * or with status 500 (Internal Server Error) if the locadora couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/locadoras")
    @Timed
    public ResponseEntity<Locadora> updateLocadora(@Valid @RequestBody Locadora locadora) throws URISyntaxException {
        log.debug("REST request to update Locadora : {}", locadora);
        if (locadora.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Locadora result = locadoraRepository.save(locadora);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, locadora.getId().toString()))
            .body(result);
    }

    /**
     * GET  /locadoras : get all the locadoras.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of locadoras in body
     */
    @GetMapping("/locadoras")
    @Timed
    public List<Locadora> getAllLocadoras() {
        log.debug("REST request to get all Locadoras");
        return locadoraRepository.findAll();
    }

    /**
     * GET  /locadoras/:id : get the "id" locadora.
     *
     * @param id the id of the locadora to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the locadora, or with status 404 (Not Found)
     */
    @GetMapping("/locadoras/{id}")
    @Timed
    public ResponseEntity<Locadora> getLocadora(@PathVariable Long id) {
        log.debug("REST request to get Locadora : {}", id);
        Optional<Locadora> locadora = locadoraRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(locadora);
    }

    /**
     * DELETE  /locadoras/:id : delete the "id" locadora.
     *
     * @param id the id of the locadora to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/locadoras/{id}")
    @Timed
    public ResponseEntity<Void> deleteLocadora(@PathVariable Long id) {
        log.debug("REST request to delete Locadora : {}", id);

        locadoraRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
