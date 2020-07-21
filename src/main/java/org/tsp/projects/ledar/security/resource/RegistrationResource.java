package org.tsp.projects.ledar.security.resource;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.tsp.projects.ledar.security.model.*;
import org.tsp.projects.ledar.security.payload.request.ContactInformationPayload;
import org.tsp.projects.ledar.security.payload.request.PersonPayload;
import org.tsp.projects.ledar.security.payload.response.ApiResponse;
import org.tsp.projects.ledar.security.repository.*;
import org.tsp.projects.ledar.security.service.AddressesService;
import org.tsp.projects.ledar.security.service.LoginInformationService;

import javax.validation.Valid;
import java.util.Optional;
import org.tsp.projects.ledar.security.service.ContactInformationService;
import org.tsp.projects.ledar.security.service.PersonService;
import org.tsp.projects.ledar.security.service.UserRoleService;

@Slf4j
@RestController
@RequestMapping("/api/registrationResource")
public class RegistrationResource {

    private final AuthenticationRolesRepository authenticationRolesRepository;

    private final LoginInformationService loginInformationService;
    private final AddressesService addressesService;
    private final PersonService personService;
    private final ContactInformationService contactInformationService;
    private final UserRoleService userRoleService;

    private AuthenticationRole authenticationRole = null;
    private String errorMessage;

    @Autowired
    public RegistrationResource(AuthenticationRolesRepository authenticationRolesRepos,
            UserRoleService userRoleService, ContactInformationService contactInformationService,
            LoginInformationService loginInformationService, 
            AddressesService addressesService, PersonService personService) {
        this.authenticationRolesRepository = authenticationRolesRepos;
        this.loginInformationService = loginInformationService;
        this.addressesService = addressesService;
        this.personService = personService;
        this.contactInformationService = contactInformationService;
        this.userRoleService = userRoleService;
    }

    @Transactional
    @RequestMapping(value = "/registerNewUser", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> createNewApplicationUser(@Valid @RequestBody PersonPayload personRequestPayload) {
        log.info("Registering new app user: {}", personRequestPayload);
        ContactInformationPayload contactInfoPayload = personRequestPayload.getContactInformation();

        if (!isValidPayloadReferences(personRequestPayload)) {
            log.info("Payload reference data validation failed: {}", errorMessage);
            return ResponseEntity.badRequest().body(new ApiResponse(false, errorMessage, null));
        }
        log.info("Completed all reference data validation");

        LoginInformation loginInformation = loginInformationService.saveNewLoginInformation(personRequestPayload.getLoginInformation());
        Addresses address = addressesService.saveNewAddresses(personRequestPayload.getUserAddress(), loginInformation);

        Person person = personService.saveNewPerson(personRequestPayload, address, loginInformation);
        contactInformationService.saveNewContactInformation(contactInfoPayload, address, loginInformation, person);

        this.userRoleService.saveNewUserRole(authenticationRole, loginInformation);

        return ResponseEntity.ok(person);
    }

    private Boolean isValidPayloadReferences(PersonPayload personRequestPayload) {

        Optional<AuthenticationRole> optionalAuthRole = authenticationRolesRepository.findById(personRequestPayload.getLoginInformation().getRoleId());
        if (!optionalAuthRole.isPresent()) {
            log.info("invalid authentication role id specified: {}", personRequestPayload.getLoginInformation().getRoleId());
            errorMessage = "Invalid Authentication Role specified";
            return false;
        }
        authenticationRole = optionalAuthRole.get();

        return true;
    }
}
