package org.tsp.projects.ledar.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.tsp.projects.ledar.security.model.AuthenticationRole;


/**
 *
 * @author sfagade
 */
@Repository
public interface AuthenticationRolesRepository extends JpaRepository<AuthenticationRole, Long> {
    
}
