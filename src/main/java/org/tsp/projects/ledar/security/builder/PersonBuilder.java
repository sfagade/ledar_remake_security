package org.tsp.projects.ledar.security.builder;

import org.tsp.projects.ledar.security.model.Person;
import org.tsp.projects.ledar.security.payload.response.PersonResponse;

public class PersonBuilder {

    public static PersonResponse buildPersonPayload(Person person, Boolean isDetail) {

        if (person != null) {
            PersonResponse personPayload;
            if (!isDetail) {
                personPayload = new PersonResponse(person.getId(), person.getFirstName(), person.getLastName(),
                        person.getMiddleName(), person.getFullName(), person.getDateOfBirth(),
                        person.getGender(), person.getPersonTitle(), person.getCreated(), person.getModified());

                if(person.getContactInformationList() != null && person.getContactInformationList().size() > 0) {
                    personPayload.setContactInformationResponse(
                            ContactInformationBuilder.buildContactInformationPayload(person.getContactInformationList().get(0), false));
                }

            } else {

                personPayload = buildPersonPayload(person, Boolean.FALSE);
                if(person.getContactInformationList() != null && person.getContactInformationList().size() > 0) {
                    personPayload.setContactInformationResponse(
                            ContactInformationBuilder.buildContactInformationPayload(person.getContactInformationList().get(0), true));
                }
            }

            return personPayload;
        }

        return null;
    }
}
