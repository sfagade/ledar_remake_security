package org.tsp.projects.ledar.security.payload.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.tsp.projects.ledar.security.payload.PayloadAbstractBase;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@ToString
public class PersonPayload extends PayloadAbstractBase {

    @NotNull
    @Size(min = 3, max = 55)
    private String firstName;
    @NotNull
    @Size(min = 3, max = 55)
    private String lastName;
    @Size(min = 3, max = 55)
    private String middleName;
    @NotNull
    private Date dateOfBirth;
    @NotNull
    private String gender ;
    @NotNull
    private String localGovtOfOrigin;
    @NotNull
    private String maritalStatus;
    private String occupation;
    private String personTitle;
    private String religion;

    @NotNull
    private AddressesPayload userAddress;
    @NotNull
    private ContactInformationPayload contactInformation;
    @NotNull
    private LoginInformationPayload loginInformation;
}
