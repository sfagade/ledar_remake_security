package org.tsp.projects.ledar.security.payload.response;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.tsp.projects.ledar.security.payload.PayloadAbstractBase;

import java.time.LocalDateTime;
import java.util.Date;

@NoArgsConstructor
@ToString
@Data
@EqualsAndHashCode(callSuper = false)
public class PersonResponse extends PayloadAbstractBase {

    private String firstName;
    private String lastName;
    private String middleName;
    private String motherMaidenName;
    private Date dateOfBirth;
    private String gender;
    private Date weddingAnniversary;
    private Boolean updateRequired;
    private Boolean isDisabled;
    private Boolean isDeleted;
    private LoginInformationResponse loginInformation;
    private ContactInformationResponse contactInformationResponse;
    private String fullName;
    private String completeName;
    private String title;

    public PersonResponse(Long id, String firstName, String lastName, String middleName, String fullName, Date dateOfBirth,
                            String gender, String personTitle, LocalDateTime created, LocalDateTime modified) {
        this.baseId = id;
        this.firstName = firstName;
        this.lastName= lastName;
        this.middleName = middleName;
        this.fullName = fullName;
        this.dateOfBirth = dateOfBirth;
        this.gender= gender;
        this.title = personTitle;
        this.created = created;
        this.modified = modified;
    }
}
