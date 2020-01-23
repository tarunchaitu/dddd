package com.github.tarunchaitu.service;

import com.github.tarunchaitu.domain.SshCredentials;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link SshCredentials}.
 */
public interface SshCredentialsService {

    /**
     * Save a sshCredentials.
     *
     * @param sshCredentials the entity to save.
     * @return the persisted entity.
     */
    SshCredentials save(SshCredentials sshCredentials);

    /**
     * Get all the sshCredentials.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SshCredentials> findAll(Pageable pageable);


    /**
     * Get the "id" sshCredentials.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SshCredentials> findOne(Long id);

    /**
     * Delete the "id" sshCredentials.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
