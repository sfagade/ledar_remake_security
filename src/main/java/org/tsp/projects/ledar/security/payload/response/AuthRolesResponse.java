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
public class AuthRolesResponse extends PayloadAbstractBase {

    private String roleName;
    private String roleDescription;
    private String urlMapping;
    private Boolean isRestricted;
    private Long roleId;

    public AuthRolesResponse(Long id, String roleName, LocalDateTime created, LocalDateTime modified) {
        this.baseId = id;
        this.roleName = roleName;
        this.created = created;
        this.modified = modified;
    }
}
