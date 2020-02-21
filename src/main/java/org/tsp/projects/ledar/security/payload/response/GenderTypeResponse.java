package org.tsp.projects.ledar.security.payload.response;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.tsp.projects.ledar.security.payload.PayloadAbstractBase;

import java.time.LocalDateTime;

@NoArgsConstructor
@ToString
@Data
@EqualsAndHashCode(callSuper = false)
public class GenderTypeResponse extends PayloadAbstractBase {

    private String genderName;
    private String description;

    public GenderTypeResponse(Long genderId, String genderName, String description, LocalDateTime created, LocalDateTime modified) {
        this.genderName = genderName;
        this.description = description;
        this.created = created;
        this.modified = modified;
        this.baseId = genderId;
    }
}
