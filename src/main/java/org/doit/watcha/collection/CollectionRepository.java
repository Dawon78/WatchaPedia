package org.doit.watcha.collection;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CollectionRepository extends JpaRepository<Collection, Integer> {
	
	
    // 특정 유저가 만든 컬렉션 리스트 조회
    List<Collection> findByMemberId(String memberId);
    
    @Query("SELECT DISTINCT c FROM Collection c LEFT JOIN FETCH c.works")
    List<Collection> findAllWithWorks();
    
    long countByMemberId(String memberId);
}
