package org.tsp.projects.ledar.security.payload.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.tsp.projects.ledar.security.payload.PayloadAbstractBase;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@ToString
public class AuthenticationRolesPayload extends PayloadAbstractBase {

    @NotNull
    @Size(min = 1, max = 55)
    private String roleName;
    @Size(max = 225)
    private String roleDescription;
    private Integer roleType;
    @NotNull
    private int roleOrder;
    @Size(max = 15)
    private String urlMapping;
    private Boolean isRestricted;
}
