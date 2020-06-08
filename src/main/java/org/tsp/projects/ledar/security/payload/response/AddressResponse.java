package org.tsp.projects.ledar.security.payload.response;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.tsp.projects.ledar.security.payload.PayloadAbstractBase;

import java.util.List;

@NoArgsConstructor
@ToString
@Data
@EqualsAndHashCode(callSuper = false)
public class AddressResponse extends PayloadAbstractBase {

    private String houseNo;
    private String streetName;
    private String addressCont;
    private String directions;
    private String city;
    private String geographicalState;
    private String geographicalCountry;
    private String localCouncilDevArea;
    private List<PersonResponse> personList;
    private List<ContactInformationResponse> contactInformationList;
}
