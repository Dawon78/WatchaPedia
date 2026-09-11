package org.doit.watcha.common;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.doit.watcha.work.Work;
import org.doit.watcha.work.WorkRepository;
import org.doit.watcha.work.WorkType;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {
    private final CommentRepository commentRepository;
    private final WorkRepository workRepository;
    private final CommentLikeRepository commentLikeRepository; // 💡 추가

    @Transactional
    public void saveComment(Integer workId, String content, String memberId) {
        if (content == null || content.trim().isEmpty()) {
            throw new IllegalArgumentException("코멘트 내용을 입력해주세요.");
        }

        Work work = workRepository.findById(workId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 작품입니다. ID: " + workId));

        Comment comment = new Comment();
        comment.setWork(work);
        comment.setCommentContent(content);
        comment.setMemberId(memberId);
        
        comment.setLikeCount(0);
        comment.setHotScore(0);
        comment.setReportCount(0);
        comment.setCommentCreateDate(java.time.LocalDate.now());
        
        commentRepository.save(comment);
    }

    @Transactional
    public CommentLikeResponse toggleCommentLike(Integer commentId, String memberId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 코멘트입니다. ID: " + commentId));

        CommentLikeId likeId = new CommentLikeId(memberId, commentId);
        Optional<CommentLike> existingLike = commentLikeRepository.findById(likeId);

        boolean isLiked;
        if (existingLike.isEmpty()) {
            CommentLike newLike = new CommentLike();
            newLike.setMemberId(memberId);
            newLike.setCommentId(commentId);
            commentLikeRepository.save(newLike);

            comment.setLikeCount((comment.getLikeCount() == null ? 0 : comment.getLikeCount()) + 1);
            isLiked = true;
        } else {
            commentLikeRepository.delete(existingLike.get());
            
            comment.setLikeCount(Math.max(0, (comment.getLikeCount() == null ? 0 : comment.getLikeCount()) - 1));
            isLiked = false;
        }

        return new CommentLikeResponse(isLiked, comment.getLikeCount());
    }

    public List<Comment> getUserCommentsByType(String memberId, String type) {
        try {
            WorkType workType = WorkType.valueOf(type.toUpperCase());
            return commentRepository.findByMemberIdAndWorkType(memberId, workType);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("잘못된 작품 타입입니다: " + type);
        }
    }
    
    public long getTotalCommentCount(String memberId) {
        return commentRepository.countByMemberId(memberId);
    }
    
    public List<Comment> findHotComments() {
        return commentRepository.findTop6HotComments(PageRequest.of(0, 6));
    }
}