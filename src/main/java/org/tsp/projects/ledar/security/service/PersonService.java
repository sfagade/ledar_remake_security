package org.tsp.projects.ledar.security.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tsp.projects.ledar.security.model.Addresses;
import org.tsp.projects.ledar.security.model.LoginInformation;
import org.tsp.projects.ledar.security.model.Person;
import org.tsp.projects.ledar.security.payload.request.PersonPayload;
import org.tsp.projects.ledar.security.repository.PersonRepository;

/**
 *
 * @author samsonfagade
 */
@Slf4j
@Service
public class PersonService {
    
    private final PersonRepository personRepository;
    
    @Autowired
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }
    
    /** This method is used to save new person information in the system
     * @param personRequestPayload
     * @param address
     * @param owner
     * @return 
     */
    public Person saveNewPerson(PersonPayload personRequestPayload, Addresses address, LoginInformation owner) {
        
        log.info("Save new person info method called with: {}", personRequestPayload);
        
        if (personRequestPayload != null && address != null) {
            Person person = new Person(null, personRequestPayload.getFirstName().toUpperCase(), personRequestPayload.getLastName().toUpperCase(),
                personRequestPayload.getMiddleName().toUpperCase(), personRequestPayload.getDateOfBirth(), personRequestPayload.getGender(),
                address, owner, personRequestPayload.getPersonTitle(), personRequestPayload.getLocalGovtOfOrigin(), personRequestPayload.getMaritalStatus(),
                personRequestPayload.getOccupation(), personRequestPayload.getReligion(), owner, null, null);
            
            personRepository.save(person);
            return person;
        }
        
        return null;
    }
    
}
