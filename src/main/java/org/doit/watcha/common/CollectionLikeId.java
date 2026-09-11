package org.doit.watcha.common;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class CollectionLikeId implements Serializable {
    private String member;       // CollectionLike의 member 필드명과 일치해야 함
    private Integer collection; // CollectionLike의 collection 필드명과 일치해야 함
}