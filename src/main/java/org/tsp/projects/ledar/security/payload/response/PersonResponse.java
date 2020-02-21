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
    private GenderTypeResponse genderType;
    private Date weddingAnniversary;
    private Boolean updateRequired;
    private Boolean isDisabled;
    private Boolean isDeleted;
    private LoginInformationResponse loginInformation;
    private String fullName;
    private String completeName;

    public PersonResponse(Long person_id, String firstName, String lastName, String middleName, Date dateOfBirth, GenderTypeResponse genderResponse,
                          Boolean updateRequired, Boolean isDisabled, Boolean isDeleted, LoginInformationResponse loginInformation,
                          String fullName, String completeName, LocalDateTime created, LocalDateTime modified) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.dateOfBirth = dateOfBirth;
        this.genderType = genderResponse;
        this.updateRequired = updateRequired;
        this.isDisabled = isDisabled;
        this.isDeleted = isDeleted;
        this.loginInformation = loginInformation;
        this.fullName = fullName;
        this.completeName = completeName;
        this.baseId = person_id;
        this.created = created;
        this.modified = modified;
    }
}
