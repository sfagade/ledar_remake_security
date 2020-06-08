package org.tsp.projects.ledar.security.payload.response;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.tsp.projects.ledar.security.model.UserRole;
import org.tsp.projects.ledar.security.payload.PayloadAbstractBase;


import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@ToString
@Data
@EqualsAndHashCode(callSuper = false)
public class LoginInformationResponse extends PayloadAbstractBase {
    private String username;
    private String password;
    private boolean active;
    private Boolean loginStatus;
    private String activationKey;
    private String secretQuestionAnswer;
    private Boolean activateStatus;
    private Date lastLoginDate;
    private Boolean passwordResetRequired;
    private String resetKey;
    private String lastLoginIp;
    private String rememberToken;
    private String longitude;
    private String latitude;
    private Date dayKeyExpiry;
    private List<AuthRolesResponse> userRoleList;
    private PersonResponse personResponse;

    public LoginInformationResponse(Long id, String username, boolean active, PersonResponse personPayload,
                                    List<AuthRolesResponse> userRoles, Boolean resetPassword, LocalDateTime created,
                                    LocalDateTime modified) {
        this.baseId = id;
        this.username = username;
        this.active = active;
        this.created = created;
        this.modified = modified;
        this.userRoleList = userRoles;
        this.personResponse = personPayload;
        this.passwordResetRequired = resetPassword;
    }
}
