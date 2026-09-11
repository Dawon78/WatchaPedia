package org.doit.watcha.collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CollectionWorkRepository extends JpaRepository<CollectionWork, CollectionWorkId> {
    
    // 특정 컬렉션에 이미 작품이 있는지 확인할 때 유용합니다.
    boolean existsByIdWorkIdAndIdCollectionId(Integer workId, Integer collectionId);
    
    // 특정 컬렉션의 모든 연결 관계를 삭제할 때 (필요 시)
    void deleteByIdCollectionId(Integer collectionId);
}