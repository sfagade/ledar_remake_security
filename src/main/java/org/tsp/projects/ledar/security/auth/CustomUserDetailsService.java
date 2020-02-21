package org.tsp.projects.ledar.security.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tsp.projects.ledar.security.model.LoginInformation;
import org.tsp.projects.ledar.security.repository.LoginInformationRepository;


import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final LoginInformationRepository loginRepository;

    @Autowired
    public CustomUserDetailsService(LoginInformationRepository loginInformationRepos) {
        this.loginRepository = loginInformationRepos;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Let people login with either username or email
        LoginInformation loginInformation = loginRepository.findOneByUsername(username);

        if (loginInformation == null) {
            throw new UsernameNotFoundException("No user found with given username : " + username);
        }

        return UserPrincipal.create(loginInformation);
    }

    // This method is used by JWTAuthenticationFilter
    @Transactional
    public UserDetails loadUserById(Long id) {
        Optional<LoginInformation> optionalLoginInfo = loginRepository.findById(id);
        LoginInformation loginInfo;

        if (optionalLoginInfo.isPresent()) {
            loginInfo = optionalLoginInfo.get();
        } else {
            throw new UsernameNotFoundException("User not found with id : " + id);
        }

        return UserPrincipal.create(loginInfo);
    }
}
