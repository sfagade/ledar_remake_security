package org.tsp.projects.ledar.security.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tsp.projects.ledar.security.model.AuthenticationRole;
import org.tsp.projects.ledar.security.model.LoginInformation;
import org.tsp.projects.ledar.security.model.UserRole;
import org.tsp.projects.ledar.security.repository.UserRoleRepository;

/**
 *
 * @author samsonfagade
 */
@Slf4j
@Service
public class UserRoleService {

    private final UserRoleRepository userRoleRepository;

    @Autowired
    public UserRoleService(UserRoleRepository userRoleRepos) {
        this.userRoleRepository = userRoleRepos;
    }

    public UserRole saveNewUserRole(AuthenticationRole authenticationRole, LoginInformation loginInformation) {

        if (authenticationRole != null && loginInformation != null) {
            UserRole userRole = new UserRole(null, loginInformation, authenticationRole, null, null, null);
            userRoleRepository.save(userRole);

            return userRole;
        }

        return null;
    }

}
