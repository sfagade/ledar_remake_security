package org.tsp.projects.ledar.security.model;

import lombok.Data;
import org.tsp.projects.ledar.security.model.listener.RecordUpdateListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@MappedSuperclass
@EntityListeners(RecordUpdateListener.class)
public abstract class LedarAbstractBase implements Serializable {

    @Id
    @TableGenerator(name = "OnlinePkGen",
            table = "ID_GEN",
            pkColumnName = "GEN_NAME",
            pkColumnValue = "Online_Gen",
            valueColumnName = "GEN_VAL",
            initialValue = 0,
            allocationSize = 5
    )
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Basic(optional = false)
    @Column(name = "id", nullable = false, columnDefinition = "BIGINT")
    protected Long id;

    @Column(name = "created")
    protected LocalDateTime created;

    @Column(name = "modified")
    protected LocalDateTime modified;
}
