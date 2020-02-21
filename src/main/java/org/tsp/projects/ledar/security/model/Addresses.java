package org.tsp.projects.ledar.security.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author sfagade
 */
@Entity
@Table(name = "addresses")
@AttributeOverride(name = "id", column = @Column(name = "address_id", nullable = false, columnDefinition = "BIGINT"))
@NoArgsConstructor
@ToString
@Data
public class Addresses extends LedarAbstractBase implements Serializable {

    private static final long serialVersionUID = 1L;

    @Size(max = 45)
    @Column(name = "house_no")
    private String houseNo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "street_name")
    private String streetName;
    @Size(max = 200)
    @Column(name = "address_cont")
    private String addressCont;
    @Size(max = 100)
    @Column(name = "directions")
    private String directions;
    @Size(max = 100)
    @Column(name = "city")
    private String city;
    @Column(name = "geographical_state")
    private String geographicalState;
    @Column(name = "geographical_country")
    private String geographicalCountry;
    @Column(name = "local_council_dev_area")
    private String localCouncilDevArea;

    @JoinColumn(name = "created_by_id", referencedColumnName = "login_id")
    @ManyToOne(optional = false)
    private LoginInformation createdById;
    @OneToMany(mappedBy = "addressId")
    private List<Person> personList;
    @OneToMany(mappedBy = "addressId")
    private List<ContactInformation> contactInformationList;

    public Addresses(Long addressId, String streetName, String houseNo, String city, String localGovt, LoginInformation createdById,
                     String geographicalState, String geographicalCountry,  LocalDateTime created, LocalDateTime modified) {
        this.id = addressId;
        this.houseNo = houseNo;
        this.city = city;
        this.localCouncilDevArea = localGovt;
        this.created = created;
        this.modified = modified;
        this.createdById = createdById;
        this.streetName = streetName;
        this.geographicalCountry = geographicalCountry;
        this.geographicalState = geographicalState;
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
        if (!(object instanceof Addresses)) {
            return false;
        }
        Addresses other = (Addresses) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

}
