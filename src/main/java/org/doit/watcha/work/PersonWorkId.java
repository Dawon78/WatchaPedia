package org.doit.watcha.work;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class PersonWorkId implements Serializable {
    private String roleType;
    private Integer personId;
    private Integer workId;
}