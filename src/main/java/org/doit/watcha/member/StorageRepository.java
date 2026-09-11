package org.doit.watcha.member;

import java.util.List;
import org.doit.watcha.work.WorkType; // 🚩 WorkType Enum 임포트 확인
import org.springframework.data.jpa.repository.JpaRepository;

public interface StorageRepository extends JpaRepository<Storage, StorageId> {
    
    // 🚩 파라미터 타입을 String -> WorkType 으로 수정
    long countByMemberIdAndStorageStatusAndWork_WorkType(
            String memberId, 
            StorageStatus status, 
            WorkType workType
    );

    // 🚩 목록 리스트 조회도 마찬가지로 WorkType으로 수정
    List<Storage> findAllByMemberIdAndStorageStatusAndWork_WorkType(
            String memberId, 
            StorageStatus status, 
            WorkType workType
    );
}