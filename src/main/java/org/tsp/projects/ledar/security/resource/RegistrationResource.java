package org.tsp.projects.ledar.security.resource;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.tsp.projects.ledar.security.model.*;
import org.tsp.projects.ledar.security.payload.request.AddressesPayload;
import org.tsp.projects.ledar.security.payload.request.ContactInformationPayload;
import org.tsp.projects.ledar.security.payload.request.LoginInformationPayload;
import org.tsp.projects.ledar.security.payload.request.PersonPayload;
import org.tsp.projects.ledar.security.payload.response.ApiResponse;
import org.tsp.projects.ledar.security.repository.*;
import org.tsp.projects.ledar.security.util.ApplicationUtility;


import javax.validation.Valid;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/registrationResource")
public class RegistrationResource {

    private final PersonRepository personRepository;
    private final AddressesRepository addressesRepository;
    private final LoginInformationRepository loginInformationRepository;
    private final ContactInformationRepository contactInformationRepository;
    private final UserRoleRepository userRoleRepository;
    private final AuthenticationRolesRepository authenticationRolesRepository;
    private final PasswordEncoder passwordEncoder;

    private AuthenticationRole authenticationRole = null;
    private String errorMessage;

    @Autowired
    public RegistrationResource(PersonRepository personRepos, AddressesRepository addressesRepos, AuthenticationRolesRepository authenticationRolesRepos,
                                LoginInformationRepository loginInformationRepos, ContactInformationRepository contactInformationRepos,
                                UserRoleRepository userRoleRepos, PasswordEncoder passwordEncoder) {
        this.personRepository = personRepos;
        this.addressesRepository = addressesRepos;
        this.authenticationRolesRepository = authenticationRolesRepos;
        this.loginInformationRepository = loginInformationRepos;
        this.contactInformationRepository = contactInformationRepos;
        this.userRoleRepository = userRoleRepos;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    @RequestMapping(value = "/registerNewUser", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> createNewApplicationUser(@Valid @RequestBody PersonPayload personRequestPayload) {
        log.info("Registering new app user: {}", personRequestPayload);
        LoginInformationPayload loginPayload = personRequestPayload.getLoginInformation();
        AddressesPayload addressPayload = personRequestPayload.getUserAddress();
        ContactInformationPayload contactInfoPayload = personRequestPayload.getContactInformation();

        if (!isValidPayloadReferences(personRequestPayload)) {
            log.info("Payload reference data validation failed: {}", errorMessage);
            return ResponseEntity.badRequest().body(new ApiResponse(false, errorMessage, null));
        }
        log.info("Completed all reference data validation");

        LoginInformation loginInformation = new LoginInformation(null, loginPayload.getUsername(), passwordEncoder.encode(loginPayload.getPassword()), false,
                ApplicationUtility.createRandomCode(8, "ABCDEFGHIJKLMNOPQRSTUVWXYZ"), null, null);
        Addresses address = new Addresses(null, addressPayload.getStreetName(), addressPayload.getHouseNo(), addressPayload.getCity(),
                addressPayload.getLocalGovtArea(), loginInformation, addressPayload.getAddressState(), addressPayload.getAddressCountry(), null, null);

        Person person = new Person(null, personRequestPayload.getFirstName().toUpperCase(), personRequestPayload.getLastName().toUpperCase(),
                personRequestPayload.getMiddleName().toUpperCase(), personRequestPayload.getDateOfBirth(), personRequestPayload.getGender(),
                address, loginInformation, personRequestPayload.getPersonTitle(), personRequestPayload.getLocalGovtOfOrigin(), personRequestPayload.getMaritalStatus(),
                personRequestPayload.getOccupation(), personRequestPayload.getReligion(), loginInformation, null, null);
        ContactInformation contactInformation = new ContactInformation(null, contactInfoPayload.getContactPhoneNumber(), null,
                contactInfoPayload.getPrimaryEmailAddress(), null, address, loginInformation, person, null, null);

        loginInformationRepository.save(loginInformation);
        addressesRepository.save(address);
        personRepository.save(person);
        contactInformationRepository.save(contactInformation);
        userRoleRepository.save(new UserRole(null, loginInformation, authenticationRole, null, null, null));

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
