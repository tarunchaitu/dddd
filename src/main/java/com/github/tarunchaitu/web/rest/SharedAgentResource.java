package com.github.tarunchaitu.web.rest;

import com.github.tarunchaitu.domain.SharedAgent;
import com.github.tarunchaitu.service.SharedAgentService;
import com.github.tarunchaitu.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.github.tarunchaitu.domain.SharedAgent}.
 */
@RestController
@RequestMapping("/api")
public class SharedAgentResource {

    private final Logger log = LoggerFactory.getLogger(SharedAgentResource.class);

    private static final String ENTITY_NAME = "sharedAgent";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SharedAgentService sharedAgentService;

    public SharedAgentResource(SharedAgentService sharedAgentService) {
        this.sharedAgentService = sharedAgentService;
    }

    /**
     * {@code POST  /shared-agents} : Create a new sharedAgent.
     *
     * @param sharedAgent the sharedAgent to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new sharedAgent, or with status {@code 400 (Bad Request)} if the sharedAgent has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/shared-agents")
    public ResponseEntity<SharedAgent> createSharedAgent(@Valid @RequestBody SharedAgent sharedAgent) throws URISyntaxException {
        log.debug("REST request to save SharedAgent : {}", sharedAgent);
        if (sharedAgent.getId() != null) {
            throw new BadRequestAlertException("A new sharedAgent cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SharedAgent result = sharedAgentService.save(sharedAgent);
        return ResponseEntity.created(new URI("/api/shared-agents/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /shared-agents} : Updates an existing sharedAgent.
     *
     * @param sharedAgent the sharedAgent to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sharedAgent,
     * or with status {@code 400 (Bad Request)} if the sharedAgent is not valid,
     * or with status {@code 500 (Internal Server Error)} if the sharedAgent couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/shared-agents")
    public ResponseEntity<SharedAgent> updateSharedAgent(@Valid @RequestBody SharedAgent sharedAgent) throws URISyntaxException {
        log.debug("REST request to update SharedAgent : {}", sharedAgent);
        if (sharedAgent.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SharedAgent result = sharedAgentService.save(sharedAgent);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, sharedAgent.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /shared-agents} : get all the sharedAgents.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of sharedAgents in body.
     */
    @GetMapping("/shared-agents")
    public ResponseEntity<List<SharedAgent>> getAllSharedAgents(Pageable pageable) {
        log.debug("REST request to get a page of SharedAgents");
        Page<SharedAgent> page = sharedAgentService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /shared-agents/:id} : get the "id" sharedAgent.
     *
     * @param id the id of the sharedAgent to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the sharedAgent, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/shared-agents/{id}")
    public ResponseEntity<SharedAgent> getSharedAgent(@PathVariable Long id) {
        log.debug("REST request to get SharedAgent : {}", id);
        Optional<SharedAgent> sharedAgent = sharedAgentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(sharedAgent);
    }

    /**
     * {@code DELETE  /shared-agents/:id} : delete the "id" sharedAgent.
     *
     * @param id the id of the sharedAgent to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/shared-agents/{id}")
    public ResponseEntity<Void> deleteSharedAgent(@PathVariable Long id) {
        log.debug("REST request to delete SharedAgent : {}", id);
        sharedAgentService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
