package org.tsp.projects.ledar.security.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.Formula;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 *
 * @author sfagade
 */
@Entity
@Table(name = "people")
@AttributeOverride(name = "id", column = @Column(name = "person_id", nullable = false, columnDefinition = "BIGINT"))
@NoArgsConstructor
@ToString
@Data
public class Person extends LedarAbstractBase implements Serializable {

    private static final long serialVersionUID = 1L;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 35)
    @Column(name = "first_name")
    private String firstName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 35)
    @Column(name = "last_name")
    private String lastName;
    @Size(max = 40)
    @Column(name = "middle_name")
    private String middleName;
    @Size(max = 45)
    @Column(name = "mother_maiden_name")
    private String motherMaidenName;
    @Size(max = 15)
    @Column(name = "gender")
    private String  gender;

    @Column(name = "date_of_birth")
    @Temporal(TemporalType.DATE)
    private Date dateOfBirth;
    @Column(name = "wedding_anniversary")
    @Temporal(TemporalType.DATE)
    private Date weddingAnniversary;
    @Column(name = "update_required")
    private Boolean updateRequired;
    @Column(name = "is_disabled")
    private Boolean isDisabled;
    @Column(name = "is_deleted")
    private Boolean isDeleted;
    @Column(name = "person_title")
    private String  personTitle;
    @Column(name = "local_govt_of_origin")
    private String localGovtOfOrigin;
    @Column(name = "marital_status")
    private String  maritalStatus;
    @Column(name = "occupation")
    private String occupation;
    @Column(name = "religion")
    private String religionId;

    @JoinColumn(name = "address_id", referencedColumnName = "address_id")
    @ManyToOne
    private Addresses addressId;
    @JoinColumn(name = "picture_upload_id", referencedColumnName = "file_upload_id")
    @ManyToOne
    private FileUploads pictureUploadId;
    @JoinColumn(name = "created_by_id", referencedColumnName = "login_id")
    @ManyToOne(optional = false)
    private LoginInformation createdById;
    @OneToMany(mappedBy = "personId")
    private List<ContactInformation> contactInformationList;
    @JoinColumn(name = "login_id", referencedColumnName = "login_id")
    @OneToOne(cascade = CascadeType.ALL)
    private LoginInformation loginInformation;

    @Formula("Concat_ws(' ',first_name, last_name)")
    private String fullName;

    @Formula("Concat_ws(' ',first_name, middle_name, last_name)")
    private String completeName;

    public Person(Long personId, @NotNull @Size(min = 1, max = 35) String firstName, @NotNull @Size(min = 1, max = 35) String lastName,
                  @Size(max = 40) String middleName, Date dateOfBirth, String genderType, Addresses addressId, LoginInformation createdById,
                  String personTitle, String localGovtOfOrigin, String maritalStatus, String occupation, String religion,
                  LoginInformation loginInformation, LocalDateTime created, LocalDateTime modified) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.dateOfBirth = dateOfBirth;
        this.gender = genderType;
        this.addressId = addressId;
        this.personTitle = personTitle;
        this.localGovtOfOrigin = localGovtOfOrigin;
        this.maritalStatus = maritalStatus;
        this.occupation = occupation;
        this.religionId = religion;
        this.id = personId;
        this.created = created;
        this.modified = modified;
        this.createdById = createdById;
        this.loginInformation = loginInformation;
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
        if (!(object instanceof Person)) {
            return false;
        }
        Person other = (Person) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

}
