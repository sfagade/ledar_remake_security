package org.tsp.projects.ledar.security.builder;

import org.tsp.projects.ledar.security.model.ContactInformation;
import org.tsp.projects.ledar.security.payload.response.ContactInformationResponse;

public class ContactInformationBuilder {

    public static ContactInformationResponse buildContactInformationPayload(ContactInformation contact, Boolean isDetail) {

        if (contact != null) {
            ContactInformationResponse contactPayload;
            if (!isDetail) {
                contactPayload = new ContactInformationResponse(contact.getId(), contact.getContactPhoneNumber(), contact.getPrimaryEmailAddress(), contact.getCreated(),
                        contact.getModified());
            } else {
                contactPayload = buildContactInformationPayload(contact, Boolean.FALSE);

            }
            return contactPayload;
        }

        return null;
    }
}
