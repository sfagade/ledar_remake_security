package org.tsp.projects.ledar.security.payload.response;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.tsp.projects.ledar.security.payload.PayloadAbstractBase;

import java.time.LocalDateTime;

@NoArgsConstructor
@ToString
@Data
@EqualsAndHashCode(callSuper = false)
public class ContactInformationResponse extends PayloadAbstractBase {

    private String contactPhoneNumber;
    private String backupPhoneNumber;
    private String primaryEmailAddress;
    private String backupEmailAddress;
    private String webAddress;
    private AddressResponse addresses;
    private LoginInformationResponse createdBy;
    private PersonResponse person;

    public ContactInformationResponse(Long id, String contactPhoneNumber, String primaryEmailAddress, LocalDateTime created,
                                      LocalDateTime modified) {
        this.baseId = id;
        this.contactPhoneNumber = contactPhoneNumber;
        this.primaryEmailAddress = primaryEmailAddress;
        this.created = created;
        this.modified= modified;
    }
}
