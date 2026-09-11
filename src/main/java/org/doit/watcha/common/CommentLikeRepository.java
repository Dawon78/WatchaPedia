package org.doit.watcha.common;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentLikeRepository extends JpaRepository<CommentLike, CommentLikeId> {
    // 특정 사용자가 특정 코멘트에 좋아요를 눌렀는지 확인
    boolean existsByMemberIdAndCommentId(String memberId, Integer commentId);
    
    // 좋아요 취소 시 사용
    void deleteByMemberIdAndCommentId(String memberId, Integer commentId);
}

