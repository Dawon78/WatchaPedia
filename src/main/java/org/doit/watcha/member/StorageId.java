// StorageId.java
package org.doit.watcha.member;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StorageId implements Serializable {
    private String memberId;
    private Integer workId;
}