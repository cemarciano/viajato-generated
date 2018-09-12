package br.com.viajato.web.rest;

import com.codahale.metrics.annotation.Timed;
import br.com.viajato.domain.Contrato;
import br.com.viajato.repository.ContratoRepository;
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
 * REST controller for managing Contrato.
 */
@RestController
@RequestMapping("/api")
public class ContratoResource {

    private final Logger log = LoggerFactory.getLogger(ContratoResource.class);

    private static final String ENTITY_NAME = "contrato";

    private final ContratoRepository contratoRepository;

    public ContratoResource(ContratoRepository contratoRepository) {
        this.contratoRepository = contratoRepository;
    }

    /**
     * POST  /contratoes : Create a new contrato.
     *
     * @param contrato the contrato to create
     * @return the ResponseEntity with status 201 (Created) and with body the new contrato, or with status 400 (Bad Request) if the contrato has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/contratoes")
    @Timed
    public ResponseEntity<Contrato> createContrato(@Valid @RequestBody Contrato contrato) throws URISyntaxException {
        log.debug("REST request to save Contrato : {}", contrato);
        if (contrato.getId() != null) {
            throw new BadRequestAlertException("A new contrato cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Contrato result = contratoRepository.save(contrato);
        return ResponseEntity.created(new URI("/api/contratoes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /contratoes : Updates an existing contrato.
     *
     * @param contrato the contrato to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated contrato,
     * or with status 400 (Bad Request) if the contrato is not valid,
     * or with status 500 (Internal Server Error) if the contrato couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/contratoes")
    @Timed
    public ResponseEntity<Contrato> updateContrato(@Valid @RequestBody Contrato contrato) throws URISyntaxException {
        log.debug("REST request to update Contrato : {}", contrato);
        if (contrato.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Contrato result = contratoRepository.save(contrato);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, contrato.getId().toString()))
            .body(result);
    }

    /**
     * GET  /contratoes : get all the contratoes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of contratoes in body
     */
    @GetMapping("/contratoes")
    @Timed
    public List<Contrato> getAllContratoes() {
        log.debug("REST request to get all Contratoes");
        return contratoRepository.findAll();
    }

    /**
     * GET  /contratoes/:id : get the "id" contrato.
     *
     * @param id the id of the contrato to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the contrato, or with status 404 (Not Found)
     */
    @GetMapping("/contratoes/{id}")
    @Timed
    public ResponseEntity<Contrato> getContrato(@PathVariable Long id) {
        log.debug("REST request to get Contrato : {}", id);
        Optional<Contrato> contrato = contratoRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(contrato);
    }

    /**
     * DELETE  /contratoes/:id : delete the "id" contrato.
     *
     * @param id the id of the contrato to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/contratoes/{id}")
    @Timed
    public ResponseEntity<Void> deleteContrato(@PathVariable Long id) {
        log.debug("REST request to delete Contrato : {}", id);

        contratoRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
