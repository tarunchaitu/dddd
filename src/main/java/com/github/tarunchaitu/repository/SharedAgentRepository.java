package com.github.tarunchaitu.repository;

import com.github.tarunchaitu.domain.SharedAgent;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the SharedAgent entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SharedAgentRepository extends JpaRepository<SharedAgent, Long> {

}
