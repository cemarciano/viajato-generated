package br.com.viajato.web.rest;

import com.codahale.metrics.annotation.Timed;
import br.com.viajato.domain.Passagem;
import br.com.viajato.repository.PassagemRepository;
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
 * REST controller for managing Passagem.
 */
@RestController
@RequestMapping("/api")
public class PassagemResource {

    private final Logger log = LoggerFactory.getLogger(PassagemResource.class);

    private static final String ENTITY_NAME = "passagem";

    private final PassagemRepository passagemRepository;

    public PassagemResource(PassagemRepository passagemRepository) {
        this.passagemRepository = passagemRepository;
    }

    /**
     * POST  /passagems : Create a new passagem.
     *
     * @param passagem the passagem to create
     * @return the ResponseEntity with status 201 (Created) and with body the new passagem, or with status 400 (Bad Request) if the passagem has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/passagems")
    @Timed
    public ResponseEntity<Passagem> createPassagem(@Valid @RequestBody Passagem passagem) throws URISyntaxException {
        log.debug("REST request to save Passagem : {}", passagem);
        if (passagem.getId() != null) {
            throw new BadRequestAlertException("A new passagem cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Passagem result = passagemRepository.save(passagem);
        return ResponseEntity.created(new URI("/api/passagems/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /passagems : Updates an existing passagem.
     *
     * @param passagem the passagem to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated passagem,
     * or with status 400 (Bad Request) if the passagem is not valid,
     * or with status 500 (Internal Server Error) if the passagem couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/passagems")
    @Timed
    public ResponseEntity<Passagem> updatePassagem(@Valid @RequestBody Passagem passagem) throws URISyntaxException {
        log.debug("REST request to update Passagem : {}", passagem);
        if (passagem.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Passagem result = passagemRepository.save(passagem);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, passagem.getId().toString()))
            .body(result);
    }

    /**
     * GET  /passagems : get all the passagems.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of passagems in body
     */
    @GetMapping("/passagems")
    @Timed
    public List<Passagem> getAllPassagems() {
        log.debug("REST request to get all Passagems");
        return passagemRepository.findAll();
    }

    /**
     * GET  /passagems/:id : get the "id" passagem.
     *
     * @param id the id of the passagem to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the passagem, or with status 404 (Not Found)
     */
    @GetMapping("/passagems/{id}")
    @Timed
    public ResponseEntity<Passagem> getPassagem(@PathVariable Long id) {
        log.debug("REST request to get Passagem : {}", id);
        Optional<Passagem> passagem = passagemRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(passagem);
    }

    /**
     * DELETE  /passagems/:id : delete the "id" passagem.
     *
     * @param id the id of the passagem to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/passagems/{id}")
    @Timed
    public ResponseEntity<Void> deletePassagem(@PathVariable Long id) {
        log.debug("REST request to delete Passagem : {}", id);

        passagemRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
