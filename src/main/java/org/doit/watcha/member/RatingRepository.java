package org.doit.watcha.member;

import org.doit.watcha.work.WorkType; // 🚩 Enum 임포트
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RatingRepository extends JpaRepository<Rating, Integer> {
    
    Optional<Rating> findByMemberIdAndWorkId(String memberId, Integer workId);

    // ⭐ 특정 작품의 평균 별점
    @Query("SELECT AVG(r.ratingScore) FROM Rating r WHERE r.workId = :workId")
    Double getAverageRating(@Param("workId") Integer workId);

    // ⭐ 특정 작품 평가 인원수
    Long countByWorkId(Integer workId);
    
    @Query("SELECT COUNT(r) FROM Rating r")
    Long countAllRatings();
    
    // ⭐ 특정 회원 + 특정 카테고리(Enum) 평가 개수
    @Query("SELECT COUNT(r) FROM Rating r JOIN Work w ON r.workId = w.workId " +
           "WHERE r.memberId = :memberId AND w.workType = :workType")
    Long countByMemberIdAndWorkType(@Param("memberId") String memberId, @Param("workType") WorkType workType);
    
    Long countByMemberId(String memberId);
    
    @Query("SELECT r FROM Rating r JOIN FETCH r.work WHERE r.memberId = :memberId")
    List<Rating> findAllByMemberId(@Param("memberId") String memberId);
    
    // 🚩 [수정] String workType -> WorkType workType
    List<Rating> findAllByMemberIdAndWork_WorkType(String memberId, WorkType workType);
}