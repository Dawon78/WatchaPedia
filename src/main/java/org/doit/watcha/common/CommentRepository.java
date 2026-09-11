package org.doit.watcha.common;

import java.util.List;

import org.doit.watcha.work.WorkType;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
    // 특정 사용자가 작성한 코멘트를 작품 타입별로 조회 (최신순)
    @Query("SELECT c FROM Comment c JOIN FETCH c.work w " +
           "WHERE c.memberId = :memberId AND w.workType = :workType " +
           "ORDER BY c.commentCreateDate DESC, c.commentId DESC")
    List<Comment> findByMemberIdAndWorkType(@Param("memberId") String memberId, @Param("workType") WorkType workType);
    
    long countByMemberId(String memberId);
    
    @Query("""
    	    SELECT c
    	    FROM Comment c
    	    LEFT JOIN c.commentLikes cl
    	    GROUP BY c
    	    ORDER BY COUNT(cl) DESC
    	""")
    	List<Comment> findTop6HotComments(Pageable pageable);
}