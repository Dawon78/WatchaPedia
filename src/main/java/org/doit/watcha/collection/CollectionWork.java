package org.doit.watcha.collection;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.doit.watcha.work.Work;

@Entity
@Table(name = "COLLECTION_WORK")
@Getter
@NoArgsConstructor
public class CollectionWork {

    @EmbeddedId
    private CollectionWorkId id;

    @MapsId("workId") // CollectionWorkId의 workId와 매핑
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "WORKID")
    private Work work;

    @MapsId("collectionId") // CollectionWorkId의 collectionId와 매핑
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COLLECTIONID")
    private Collection collection;

    public CollectionWork(Work work, Collection collection) {
        this.id = new CollectionWorkId(work.getWorkId(), collection.getCollectionId());
        this.work = work;
        this.collection = collection;
    }
}