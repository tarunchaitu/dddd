package com.github.tarunchaitu.service.impl;

import com.github.tarunchaitu.service.SharedAgentService;
import com.github.tarunchaitu.domain.SharedAgent;
import com.github.tarunchaitu.repository.SharedAgentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link SharedAgent}.
 */
@Service
@Transactional
public class SharedAgentServiceImpl implements SharedAgentService {

    private final Logger log = LoggerFactory.getLogger(SharedAgentServiceImpl.class);

    private final SharedAgentRepository sharedAgentRepository;

    public SharedAgentServiceImpl(SharedAgentRepository sharedAgentRepository) {
        this.sharedAgentRepository = sharedAgentRepository;
    }

    /**
     * Save a sharedAgent.
     *
     * @param sharedAgent the entity to save.
     * @return the persisted entity.
     */
    @Override
    public SharedAgent save(SharedAgent sharedAgent) {
        log.debug("Request to save SharedAgent : {}", sharedAgent);
        return sharedAgentRepository.save(sharedAgent);
    }

    /**
     * Get all the sharedAgents.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<SharedAgent> findAll(Pageable pageable) {
        log.debug("Request to get all SharedAgents");
        return sharedAgentRepository.findAll(pageable);
    }


    /**
     * Get one sharedAgent by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<SharedAgent> findOne(Long id) {
        log.debug("Request to get SharedAgent : {}", id);
        return sharedAgentRepository.findById(id);
    }

    /**
     * Delete the sharedAgent by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete SharedAgent : {}", id);
        sharedAgentRepository.deleteById(id);
    }
}
