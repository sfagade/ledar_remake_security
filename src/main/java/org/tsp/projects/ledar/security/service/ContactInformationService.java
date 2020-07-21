
package org.tsp.projects.ledar.security.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tsp.projects.ledar.security.model.Addresses;
import org.tsp.projects.ledar.security.model.ContactInformation;
import org.tsp.projects.ledar.security.model.LoginInformation;
import org.tsp.projects.ledar.security.model.Person;
import org.tsp.projects.ledar.security.payload.request.ContactInformationPayload;
import org.tsp.projects.ledar.security.repository.ContactInformationRepository;

/**
 *
 * @author samsonfagade
 */
@Slf4j
@Service
public class ContactInformationService {
    
    private final ContactInformationRepository contactInformationRepository;
    
    @Autowired
    public ContactInformationService(ContactInformationRepository contactInformationRepos) {
        this.contactInformationRepository = contactInformationRepos;
    }
    
    /**  This method is used to save new person contact-information in the system
     * @param contactInfoPayload
     * @param addresses
     * @param creatorInformation
     * @param ownerPerson
     * @return 
     */
    public ContactInformation saveNewContactInformation(ContactInformationPayload contactInfoPayload, 
            Addresses addresses, LoginInformation creatorInformation, Person ownerPerson) {
        
        if (contactInfoPayload != null && addresses != null && creatorInformation != null && ownerPerson != null) {
            ContactInformation contactInformation = new ContactInformation(null, contactInfoPayload.getContactPhoneNumber(), null,
                contactInfoPayload.getPrimaryEmailAddress(), null, addresses, creatorInformation, ownerPerson, null, null);
            
            contactInformationRepository.save(contactInformation);
            
            return contactInformation;
        }
        
        return null;
    }
}
