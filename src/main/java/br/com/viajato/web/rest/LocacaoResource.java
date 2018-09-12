package br.com.viajato.web.rest;

import com.codahale.metrics.annotation.Timed;
import br.com.viajato.domain.Locacao;
import br.com.viajato.repository.LocacaoRepository;
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
 * REST controller for managing Locacao.
 */
@RestController
@RequestMapping("/api")
public class LocacaoResource {

    private final Logger log = LoggerFactory.getLogger(LocacaoResource.class);

    private static final String ENTITY_NAME = "locacao";

    private final LocacaoRepository locacaoRepository;

    public LocacaoResource(LocacaoRepository locacaoRepository) {
        this.locacaoRepository = locacaoRepository;
    }

    /**
     * POST  /locacaos : Create a new locacao.
     *
     * @param locacao the locacao to create
     * @return the ResponseEntity with status 201 (Created) and with body the new locacao, or with status 400 (Bad Request) if the locacao has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/locacaos")
    @Timed
    public ResponseEntity<Locacao> createLocacao(@Valid @RequestBody Locacao locacao) throws URISyntaxException {
        log.debug("REST request to save Locacao : {}", locacao);
        if (locacao.getId() != null) {
            throw new BadRequestAlertException("A new locacao cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Locacao result = locacaoRepository.save(locacao);
        return ResponseEntity.created(new URI("/api/locacaos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /locacaos : Updates an existing locacao.
     *
     * @param locacao the locacao to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated locacao,
     * or with status 400 (Bad Request) if the locacao is not valid,
     * or with status 500 (Internal Server Error) if the locacao couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/locacaos")
    @Timed
    public ResponseEntity<Locacao> updateLocacao(@Valid @RequestBody Locacao locacao) throws URISyntaxException {
        log.debug("REST request to update Locacao : {}", locacao);
        if (locacao.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Locacao result = locacaoRepository.save(locacao);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, locacao.getId().toString()))
            .body(result);
    }

    /**
     * GET  /locacaos : get all the locacaos.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of locacaos in body
     */
    @GetMapping("/locacaos")
    @Timed
    public List<Locacao> getAllLocacaos() {
        log.debug("REST request to get all Locacaos");
        return locacaoRepository.findAll();
    }

    /**
     * GET  /locacaos/:id : get the "id" locacao.
     *
     * @param id the id of the locacao to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the locacao, or with status 404 (Not Found)
     */
    @GetMapping("/locacaos/{id}")
    @Timed
    public ResponseEntity<Locacao> getLocacao(@PathVariable Long id) {
        log.debug("REST request to get Locacao : {}", id);
        Optional<Locacao> locacao = locacaoRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(locacao);
    }

    /**
     * DELETE  /locacaos/:id : delete the "id" locacao.
     *
     * @param id the id of the locacao to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/locacaos/{id}")
    @Timed
    public ResponseEntity<Void> deleteLocacao(@PathVariable Long id) {
        log.debug("REST request to delete Locacao : {}", id);

        locacaoRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
