package org.tsp.projects.ledar.security.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.tsp.projects.ledar.security.model.LoginInformation;
import org.tsp.projects.ledar.security.payload.request.LoginInformationPayload;
import org.tsp.projects.ledar.security.repository.LoginInformationRepository;
import org.tsp.projects.ledar.security.util.ApplicationUtility;

/**
 * This service is used to handle all crud functions for Login-Information
 * entity
 */
@Slf4j
@Service
public class LoginInformationService {

    private final LoginInformationRepository loginInformationRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public LoginInformationService(LoginInformationRepository loginInformationRepository, PasswordEncoder passwordEncoder) {
        this.loginInformationRepository = loginInformationRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * This method is used to save user's LoginInformation during registration
     */
    public LoginInformation saveNewLoginInformation(LoginInformationPayload loginPayload) {

        if (loginPayload != null) {
            log.info("Saving Login-Information for registration: {}", loginPayload);
            LoginInformation loginInformation = new LoginInformation(null, loginPayload.getUsername(), passwordEncoder.encode(loginPayload.getPassword()), false,
                    ApplicationUtility.createRandomCode(8, "ABCDEFGHIJKLMNOPQRSTUVWXYZ"), null, null);

            loginInformationRepository.save(loginInformation);
            return loginInformation;
        }

        return null;
    }
}
