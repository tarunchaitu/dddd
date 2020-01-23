package com.github.tarunchaitu.service;

import com.github.tarunchaitu.domain.SharedAgent;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link SharedAgent}.
 */
public interface SharedAgentService {

    /**
     * Save a sharedAgent.
     *
     * @param sharedAgent the entity to save.
     * @return the persisted entity.
     */
    SharedAgent save(SharedAgent sharedAgent);

    /**
     * Get all the sharedAgents.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SharedAgent> findAll(Pageable pageable);


    /**
     * Get the "id" sharedAgent.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SharedAgent> findOne(Long id);

    /**
     * Delete the "id" sharedAgent.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
