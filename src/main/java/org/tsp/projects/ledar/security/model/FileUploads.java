package org.tsp.projects.ledar.security.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 *
 * @author sfagade
 */
@Entity
@Table(name = "file_uploads")
@AttributeOverride(name = "id", column = @Column(name = "file_upload_id", nullable = false, columnDefinition = "BIGINT"))
@NoArgsConstructor
@ToString
@AllArgsConstructor
public class FileUploads extends LedarAbstractBase implements Serializable {

    private static final long serialVersionUID = 1L;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 150)
    @Column(name = "file_name")
    private String fileName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 35)
    @Column(name = "file_type")
    private String fileType;
    @Size(max = 9)
    @Column(name = "picture_side")
    private String pictureSide;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 250)
    @Column(name = "file_path")
    private String filePath;
    @Size(max = 200)
    @Column(name = "absolute_path")
    private String absolutePath;
    @Lob
    @Column(name = "file_content")
    private byte[] fileContent;

    @Column(name = "picture_encoded")
    private String pictureEncoded;

    @Column(name = "is_deleted")
    private Boolean isDeleted;
    @Column(name = "delete_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date deleteDate;
    @Column(name = "file_purpose")
    private String  filePurpose;

    @JoinColumn(name = "uploaded_by_id", referencedColumnName = "login_id")
    @ManyToOne
    private LoginInformation uploadedById;
    @JoinColumn(name = "deleted_by_id", referencedColumnName = "login_id")
    @ManyToOne
    private LoginInformation deletedById;
    @OneToMany(mappedBy = "pictureUploadId")
    private List<Person> personList;


    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FileUploads)) {
            return false;
        }
        FileUploads other = (FileUploads) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

}
