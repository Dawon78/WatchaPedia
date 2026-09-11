package org.doit.watcha.common;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CollectionLikeRepository extends JpaRepository<CollectionLike, CollectionLikeId> {
    // 특정 사용자가 특정 컬렉션에 좋아요를 눌렀는지 확인
    boolean existsByMemberMemberIdAndCollectionCollectionId(String memberId, Integer collectionId);
    
    // 좋아요 취소 시 사용
    void deleteByMemberMemberIdAndCollectionCollectionId(String memberId, Integer collectionId);

    // 컬렉션별 좋아요 수 카운트
    long countByCollectionCollectionId(Integer collectionId);
}