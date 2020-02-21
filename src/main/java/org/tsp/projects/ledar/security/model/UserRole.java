/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.tsp.projects.ledar.security.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author sfagade
 */
@Entity
@NoArgsConstructor
@ToString
@Data
@Table(name = "user_roles")
@AttributeOverride(name = "id", column = @Column(name = "user_role_id", nullable = false, columnDefinition = "BIGINT"))
public class UserRole extends LedarAbstractBase implements Serializable {

    private static final long serialVersionUID = 1L;

    @Size(max = 500)
    @Column(name = "description")
    private String description;

    @JoinColumn(name = "authentication_role_id", referencedColumnName = "authentication_role_id")
    @ManyToOne(optional = false)
    private AuthenticationRole authenticationRoleId;
    @JoinColumn(name = "login_id", referencedColumnName = "login_id")
    @ManyToOne(optional = false)
    private LoginInformation loginInformation;

    public UserRole(Long userRoleId, LoginInformation loginInformation, AuthenticationRole role, String description, LocalDateTime created,
                    LocalDateTime modified) {
        this.id = userRoleId;
        this.loginInformation = loginInformation;
        this.authenticationRoleId = role;
        this.description = description;
        this.created = created;
        this.modified = modified;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UserRole)) {
            return false;
        }
        UserRole other = (UserRole) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

}
