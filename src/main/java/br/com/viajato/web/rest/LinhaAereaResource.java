package br.com.viajato.web.rest;

import com.codahale.metrics.annotation.Timed;
import br.com.viajato.domain.LinhaAerea;
import br.com.viajato.repository.LinhaAereaRepository;
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
 * REST controller for managing LinhaAerea.
 */
@RestController
@RequestMapping("/api")
public class LinhaAereaResource {

    private final Logger log = LoggerFactory.getLogger(LinhaAereaResource.class);

    private static final String ENTITY_NAME = "linhaAerea";

    private final LinhaAereaRepository linhaAereaRepository;

    public LinhaAereaResource(LinhaAereaRepository linhaAereaRepository) {
        this.linhaAereaRepository = linhaAereaRepository;
    }

    /**
     * POST  /linha-aereas : Create a new linhaAerea.
     *
     * @param linhaAerea the linhaAerea to create
     * @return the ResponseEntity with status 201 (Created) and with body the new linhaAerea, or with status 400 (Bad Request) if the linhaAerea has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/linha-aereas")
    @Timed
    public ResponseEntity<LinhaAerea> createLinhaAerea(@Valid @RequestBody LinhaAerea linhaAerea) throws URISyntaxException {
        log.debug("REST request to save LinhaAerea : {}", linhaAerea);
        if (linhaAerea.getId() != null) {
            throw new BadRequestAlertException("A new linhaAerea cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LinhaAerea result = linhaAereaRepository.save(linhaAerea);
        return ResponseEntity.created(new URI("/api/linha-aereas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /linha-aereas : Updates an existing linhaAerea.
     *
     * @param linhaAerea the linhaAerea to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated linhaAerea,
     * or with status 400 (Bad Request) if the linhaAerea is not valid,
     * or with status 500 (Internal Server Error) if the linhaAerea couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/linha-aereas")
    @Timed
    public ResponseEntity<LinhaAerea> updateLinhaAerea(@Valid @RequestBody LinhaAerea linhaAerea) throws URISyntaxException {
        log.debug("REST request to update LinhaAerea : {}", linhaAerea);
        if (linhaAerea.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        LinhaAerea result = linhaAereaRepository.save(linhaAerea);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, linhaAerea.getId().toString()))
            .body(result);
    }

    /**
     * GET  /linha-aereas : get all the linhaAereas.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of linhaAereas in body
     */
    @GetMapping("/linha-aereas")
    @Timed
    public List<LinhaAerea> getAllLinhaAereas() {
        log.debug("REST request to get all LinhaAereas");
        return linhaAereaRepository.findAll();
    }

    /**
     * GET  /linha-aereas/:id : get the "id" linhaAerea.
     *
     * @param id the id of the linhaAerea to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the linhaAerea, or with status 404 (Not Found)
     */
    @GetMapping("/linha-aereas/{id}")
    @Timed
    public ResponseEntity<LinhaAerea> getLinhaAerea(@PathVariable Long id) {
        log.debug("REST request to get LinhaAerea : {}", id);
        Optional<LinhaAerea> linhaAerea = linhaAereaRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(linhaAerea);
    }

    /**
     * DELETE  /linha-aereas/:id : delete the "id" linhaAerea.
     *
     * @param id the id of the linhaAerea to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/linha-aereas/{id}")
    @Timed
    public ResponseEntity<Void> deleteLinhaAerea(@PathVariable Long id) {
        log.debug("REST request to delete LinhaAerea : {}", id);

        linhaAereaRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
