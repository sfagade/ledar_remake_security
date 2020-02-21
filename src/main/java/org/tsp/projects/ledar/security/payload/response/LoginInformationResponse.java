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
    private List<UserRole> userRoleList;

    public LoginInformationResponse(Long login_id, String username, boolean active, Boolean loginStatus,
                                    Date lastLoginDate, LocalDateTime created, LocalDateTime modified) {
        this.username = username;
        this.active = active;
        this.loginStatus = loginStatus;
        this.lastLoginDate = lastLoginDate;
        this.baseId = login_id;
        this.created = created;
        this.modified = modified;
    }
}
