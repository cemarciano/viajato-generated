package br.com.viajato.web.rest;

import com.codahale.metrics.annotation.Timed;
import br.com.viajato.domain.Voo;
import br.com.viajato.repository.VooRepository;
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
 * REST controller for managing Voo.
 */
@RestController
@RequestMapping("/api")
public class VooResource {

    private final Logger log = LoggerFactory.getLogger(VooResource.class);

    private static final String ENTITY_NAME = "voo";

    private final VooRepository vooRepository;

    public VooResource(VooRepository vooRepository) {
        this.vooRepository = vooRepository;
    }

    /**
     * POST  /voos : Create a new voo.
     *
     * @param voo the voo to create
     * @return the ResponseEntity with status 201 (Created) and with body the new voo, or with status 400 (Bad Request) if the voo has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/voos")
    @Timed
    public ResponseEntity<Voo> createVoo(@Valid @RequestBody Voo voo) throws URISyntaxException {
        log.debug("REST request to save Voo : {}", voo);
        if (voo.getId() != null) {
            throw new BadRequestAlertException("A new voo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Voo result = vooRepository.save(voo);
        return ResponseEntity.created(new URI("/api/voos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /voos : Updates an existing voo.
     *
     * @param voo the voo to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated voo,
     * or with status 400 (Bad Request) if the voo is not valid,
     * or with status 500 (Internal Server Error) if the voo couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/voos")
    @Timed
    public ResponseEntity<Voo> updateVoo(@Valid @RequestBody Voo voo) throws URISyntaxException {
        log.debug("REST request to update Voo : {}", voo);
        if (voo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Voo result = vooRepository.save(voo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, voo.getId().toString()))
            .body(result);
    }

    /**
     * GET  /voos : get all the voos.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of voos in body
     */
    @GetMapping("/voos")
    @Timed
    public List<Voo> getAllVoos() {
        log.debug("REST request to get all Voos");
        return vooRepository.findAll();
    }

    /**
     * GET  /voos/:id : get the "id" voo.
     *
     * @param id the id of the voo to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the voo, or with status 404 (Not Found)
     */
    @GetMapping("/voos/{id}")
    @Timed
    public ResponseEntity<Voo> getVoo(@PathVariable Long id) {
        log.debug("REST request to get Voo : {}", id);
        Optional<Voo> voo = vooRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(voo);
    }

    /**
     * DELETE  /voos/:id : delete the "id" voo.
     *
     * @param id the id of the voo to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/voos/{id}")
    @Timed
    public ResponseEntity<Void> deleteVoo(@PathVariable Long id) {
        log.debug("REST request to delete Voo : {}", id);

        vooRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
