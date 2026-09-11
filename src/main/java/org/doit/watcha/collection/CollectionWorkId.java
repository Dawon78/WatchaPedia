package org.doit.watcha.collection;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class CollectionWorkId implements Serializable {

    @Column(name = "WORKID")
    private Integer workId;

    @Column(name = "COLLECTIONID")
    private Integer collectionId;
}