package org.tsp.projects.ledar.security.model;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author sfagade
 */
@Entity
@Table(name = "contact_information")
@AttributeOverride(name = "id", column = @Column(name = "contact_information_id", nullable = false, columnDefinition = "BIGINT"))
@NoArgsConstructor
@ToString
@Data
public class ContactInformation extends LedarAbstractBase implements Serializable {

    private static final long serialVersionUID = 1L;

    @Size(max = 15)
    @Column(name = "contact_phone_number")
    private String contactPhoneNumber;
    @Size(max = 15)
    @Column(name = "backup_phone_number")
    private String backupPhoneNumber;
    @Basic(optional = false)
    @NotNull
    @Email
    @Size(min = 1, max = 100)
    @Column(name = "primary_email_address", unique = true)
    private String primaryEmailAddress;
    @Size(max = 100)
    @Column(name = "backup_email_address")
    private String backupEmailAddress;
    @Size(max = 100)
    @Column(name = "web_address")
    private String webAddress;

    @JoinColumn(name = "address_id", referencedColumnName = "address_id")
    @ManyToOne
    private Addresses addressId;
    @JoinColumn(name = "created_by_id", referencedColumnName = "login_id")
    @ManyToOne(optional = false)
    private LoginInformation createdById;
    @JoinColumn(name = "person_id", referencedColumnName = "person_id")
    @ManyToOne
    private Person personId;

    public ContactInformation(Long contactId, @Size(max = 15) String contactPhoneNumber, @Size(max = 15) String backupPhoneNumber,
                              @NotNull @Email @Size(min = 1, max = 100) String primaryEmailAddress,
                              @Size(max = 100) String backupEmailAddress, Addresses addressId, LoginInformation createdById,
                              Person personId, LocalDateTime created, LocalDateTime modified) {
        this.contactPhoneNumber = contactPhoneNumber;
        this.backupPhoneNumber = backupPhoneNumber;
        this.primaryEmailAddress = primaryEmailAddress;
        this.backupEmailAddress = backupEmailAddress;
        this.addressId = addressId;
        this.createdById = createdById;
        this.personId = personId;
        this.id = contactId;
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
        if (!(object instanceof ContactInformation)) {
            return false;
        }
        ContactInformation other = (ContactInformation) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

}
