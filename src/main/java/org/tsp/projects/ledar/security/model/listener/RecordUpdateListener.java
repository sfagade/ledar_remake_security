package org.tsp.projects.ledar.security.model.listener;


import org.tsp.projects.ledar.security.model.LedarAbstractBase;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;

public class RecordUpdateListener {

    @PreUpdate
    public void setUpdateDates(LedarAbstractBase abso) {
        abso.setModified(LocalDateTime.now());
    }

    @PrePersist
    public void setCreateDates(LedarAbstractBase abs) {
        abs.setModified(LocalDateTime.now());
        abs.setCreated(LocalDateTime.now());
    }
}
