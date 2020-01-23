package com.github.tarunchaitu.service.impl;

import com.github.tarunchaitu.service.SshCredentialsService;
import com.github.tarunchaitu.domain.SshCredentials;
import com.github.tarunchaitu.repository.SshCredentialsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link SshCredentials}.
 */
@Service
@Transactional
public class SshCredentialsServiceImpl implements SshCredentialsService {

    private final Logger log = LoggerFactory.getLogger(SshCredentialsServiceImpl.class);

    private final SshCredentialsRepository sshCredentialsRepository;

    public SshCredentialsServiceImpl(SshCredentialsRepository sshCredentialsRepository) {
        this.sshCredentialsRepository = sshCredentialsRepository;
    }

    /**
     * Save a sshCredentials.
     *
     * @param sshCredentials the entity to save.
     * @return the persisted entity.
     */
    @Override
    public SshCredentials save(SshCredentials sshCredentials) {
        log.debug("Request to save SshCredentials : {}", sshCredentials);
        return sshCredentialsRepository.save(sshCredentials);
    }

    /**
     * Get all the sshCredentials.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<SshCredentials> findAll(Pageable pageable) {
        log.debug("Request to get all SshCredentials");
        return sshCredentialsRepository.findAll(pageable);
    }


    /**
     * Get one sshCredentials by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<SshCredentials> findOne(Long id) {
        log.debug("Request to get SshCredentials : {}", id);
        return sshCredentialsRepository.findById(id);
    }

    /**
     * Delete the sshCredentials by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete SshCredentials : {}", id);
        sshCredentialsRepository.deleteById(id);
    }
}
