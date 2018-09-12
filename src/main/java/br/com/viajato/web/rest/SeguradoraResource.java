package br.com.viajato.web.rest;

import com.codahale.metrics.annotation.Timed;
import br.com.viajato.domain.Seguradora;
import br.com.viajato.repository.SeguradoraRepository;
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
 * REST controller for managing Seguradora.
 */
@RestController
@RequestMapping("/api")
public class SeguradoraResource {

    private final Logger log = LoggerFactory.getLogger(SeguradoraResource.class);

    private static final String ENTITY_NAME = "seguradora";

    private final SeguradoraRepository seguradoraRepository;

    public SeguradoraResource(SeguradoraRepository seguradoraRepository) {
        this.seguradoraRepository = seguradoraRepository;
    }

    /**
     * POST  /seguradoras : Create a new seguradora.
     *
     * @param seguradora the seguradora to create
     * @return the ResponseEntity with status 201 (Created) and with body the new seguradora, or with status 400 (Bad Request) if the seguradora has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/seguradoras")
    @Timed
    public ResponseEntity<Seguradora> createSeguradora(@Valid @RequestBody Seguradora seguradora) throws URISyntaxException {
        log.debug("REST request to save Seguradora : {}", seguradora);
        if (seguradora.getId() != null) {
            throw new BadRequestAlertException("A new seguradora cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Seguradora result = seguradoraRepository.save(seguradora);
        return ResponseEntity.created(new URI("/api/seguradoras/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /seguradoras : Updates an existing seguradora.
     *
     * @param seguradora the seguradora to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated seguradora,
     * or with status 400 (Bad Request) if the seguradora is not valid,
     * or with status 500 (Internal Server Error) if the seguradora couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/seguradoras")
    @Timed
    public ResponseEntity<Seguradora> updateSeguradora(@Valid @RequestBody Seguradora seguradora) throws URISyntaxException {
        log.debug("REST request to update Seguradora : {}", seguradora);
        if (seguradora.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Seguradora result = seguradoraRepository.save(seguradora);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, seguradora.getId().toString()))
            .body(result);
    }

    /**
     * GET  /seguradoras : get all the seguradoras.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of seguradoras in body
     */
    @GetMapping("/seguradoras")
    @Timed
    public List<Seguradora> getAllSeguradoras() {
        log.debug("REST request to get all Seguradoras");
        return seguradoraRepository.findAll();
    }

    /**
     * GET  /seguradoras/:id : get the "id" seguradora.
     *
     * @param id the id of the seguradora to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the seguradora, or with status 404 (Not Found)
     */
    @GetMapping("/seguradoras/{id}")
    @Timed
    public ResponseEntity<Seguradora> getSeguradora(@PathVariable Long id) {
        log.debug("REST request to get Seguradora : {}", id);
        Optional<Seguradora> seguradora = seguradoraRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(seguradora);
    }

    /**
     * DELETE  /seguradoras/:id : delete the "id" seguradora.
     *
     * @param id the id of the seguradora to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/seguradoras/{id}")
    @Timed
    public ResponseEntity<Void> deleteSeguradora(@PathVariable Long id) {
        log.debug("REST request to delete Seguradora : {}", id);

        seguradoraRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
