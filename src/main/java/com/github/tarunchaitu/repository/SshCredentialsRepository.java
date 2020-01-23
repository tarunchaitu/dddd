package com.github.tarunchaitu.repository;

import com.github.tarunchaitu.domain.SshCredentials;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the SshCredentials entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SshCredentialsRepository extends JpaRepository<SshCredentials, Long> {

}
