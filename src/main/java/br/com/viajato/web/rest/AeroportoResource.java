package br.com.viajato.web.rest;

import com.codahale.metrics.annotation.Timed;
import br.com.viajato.domain.Aeroporto;
import br.com.viajato.repository.AeroportoRepository;
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
 * REST controller for managing Aeroporto.
 */
@RestController
@RequestMapping("/api")
public class AeroportoResource {

    private final Logger log = LoggerFactory.getLogger(AeroportoResource.class);

    private static final String ENTITY_NAME = "aeroporto";

    private final AeroportoRepository aeroportoRepository;

    public AeroportoResource(AeroportoRepository aeroportoRepository) {
        this.aeroportoRepository = aeroportoRepository;
    }

    /**
     * POST  /aeroportos : Create a new aeroporto.
     *
     * @param aeroporto the aeroporto to create
     * @return the ResponseEntity with status 201 (Created) and with body the new aeroporto, or with status 400 (Bad Request) if the aeroporto has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/aeroportos")
    @Timed
    public ResponseEntity<Aeroporto> createAeroporto(@Valid @RequestBody Aeroporto aeroporto) throws URISyntaxException {
        log.debug("REST request to save Aeroporto : {}", aeroporto);
        if (aeroporto.getId() != null) {
            throw new BadRequestAlertException("A new aeroporto cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Aeroporto result = aeroportoRepository.save(aeroporto);
        return ResponseEntity.created(new URI("/api/aeroportos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /aeroportos : Updates an existing aeroporto.
     *
     * @param aeroporto the aeroporto to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated aeroporto,
     * or with status 400 (Bad Request) if the aeroporto is not valid,
     * or with status 500 (Internal Server Error) if the aeroporto couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/aeroportos")
    @Timed
    public ResponseEntity<Aeroporto> updateAeroporto(@Valid @RequestBody Aeroporto aeroporto) throws URISyntaxException {
        log.debug("REST request to update Aeroporto : {}", aeroporto);
        if (aeroporto.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Aeroporto result = aeroportoRepository.save(aeroporto);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, aeroporto.getId().toString()))
            .body(result);
    }

    /**
     * GET  /aeroportos : get all the aeroportos.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of aeroportos in body
     */
    @GetMapping("/aeroportos")
    @Timed
    public List<Aeroporto> getAllAeroportos() {
        log.debug("REST request to get all Aeroportos");
        return aeroportoRepository.findAll();
    }

    /**
     * GET  /aeroportos/:id : get the "id" aeroporto.
     *
     * @param id the id of the aeroporto to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the aeroporto, or with status 404 (Not Found)
     */
    @GetMapping("/aeroportos/{id}")
    @Timed
    public ResponseEntity<Aeroporto> getAeroporto(@PathVariable Long id) {
        log.debug("REST request to get Aeroporto : {}", id);
        Optional<Aeroporto> aeroporto = aeroportoRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(aeroporto);
    }

    /**
     * DELETE  /aeroportos/:id : delete the "id" aeroporto.
     *
     * @param id the id of the aeroporto to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/aeroportos/{id}")
    @Timed
    public ResponseEntity<Void> deleteAeroporto(@PathVariable Long id) {
        log.debug("REST request to delete Aeroporto : {}", id);

        aeroportoRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
