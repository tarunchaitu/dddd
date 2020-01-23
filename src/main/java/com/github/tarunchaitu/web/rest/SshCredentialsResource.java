package com.github.tarunchaitu.web.rest;

import com.github.tarunchaitu.domain.SshCredentials;
import com.github.tarunchaitu.service.SshCredentialsService;
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
 * REST controller for managing {@link com.github.tarunchaitu.domain.SshCredentials}.
 */
@RestController
@RequestMapping("/api")
public class SshCredentialsResource {

    private final Logger log = LoggerFactory.getLogger(SshCredentialsResource.class);

    private static final String ENTITY_NAME = "sshCredentials";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SshCredentialsService sshCredentialsService;

    public SshCredentialsResource(SshCredentialsService sshCredentialsService) {
        this.sshCredentialsService = sshCredentialsService;
    }

    /**
     * {@code POST  /ssh-credentials} : Create a new sshCredentials.
     *
     * @param sshCredentials the sshCredentials to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new sshCredentials, or with status {@code 400 (Bad Request)} if the sshCredentials has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ssh-credentials")
    public ResponseEntity<SshCredentials> createSshCredentials(@Valid @RequestBody SshCredentials sshCredentials) throws URISyntaxException {
        log.debug("REST request to save SshCredentials : {}", sshCredentials);
        if (sshCredentials.getId() != null) {
            throw new BadRequestAlertException("A new sshCredentials cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SshCredentials result = sshCredentialsService.save(sshCredentials);
        return ResponseEntity.created(new URI("/api/ssh-credentials/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ssh-credentials} : Updates an existing sshCredentials.
     *
     * @param sshCredentials the sshCredentials to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sshCredentials,
     * or with status {@code 400 (Bad Request)} if the sshCredentials is not valid,
     * or with status {@code 500 (Internal Server Error)} if the sshCredentials couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ssh-credentials")
    public ResponseEntity<SshCredentials> updateSshCredentials(@Valid @RequestBody SshCredentials sshCredentials) throws URISyntaxException {
        log.debug("REST request to update SshCredentials : {}", sshCredentials);
        if (sshCredentials.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SshCredentials result = sshCredentialsService.save(sshCredentials);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, sshCredentials.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /ssh-credentials} : get all the sshCredentials.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of sshCredentials in body.
     */
    @GetMapping("/ssh-credentials")
    public ResponseEntity<List<SshCredentials>> getAllSshCredentials(Pageable pageable) {
        log.debug("REST request to get a page of SshCredentials");
        Page<SshCredentials> page = sshCredentialsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /ssh-credentials/:id} : get the "id" sshCredentials.
     *
     * @param id the id of the sshCredentials to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the sshCredentials, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ssh-credentials/{id}")
    public ResponseEntity<SshCredentials> getSshCredentials(@PathVariable Long id) {
        log.debug("REST request to get SshCredentials : {}", id);
        Optional<SshCredentials> sshCredentials = sshCredentialsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(sshCredentials);
    }

    /**
     * {@code DELETE  /ssh-credentials/:id} : delete the "id" sshCredentials.
     *
     * @param id the id of the sshCredentials to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ssh-credentials/{id}")
    public ResponseEntity<Void> deleteSshCredentials(@PathVariable Long id) {
        log.debug("REST request to delete SshCredentials : {}", id);
        sshCredentialsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
