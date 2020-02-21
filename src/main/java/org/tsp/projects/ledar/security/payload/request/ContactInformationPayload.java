package org.tsp.projects.ledar.security.payload.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.tsp.projects.ledar.security.payload.PayloadAbstractBase;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@ToString
public class ContactInformationPayload extends PayloadAbstractBase {

    @NotNull
    @Size(min = 11, max = 15)
    private String contactPhoneNumber;
    @NotNull
    @Email
    private String primaryEmailAddress;
}
