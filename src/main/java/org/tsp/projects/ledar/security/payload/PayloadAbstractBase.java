package org.tsp.projects.ledar.security.payload;

import lombok.Data;
import org.tsp.projects.ledar.security.payload.response.LoginInformationResponse;

import java.time.LocalDateTime;

@Data
public abstract class PayloadAbstractBase {

    protected Long baseId;
    protected LocalDateTime created, modified;
    private LoginInformationResponse createdById;
}
