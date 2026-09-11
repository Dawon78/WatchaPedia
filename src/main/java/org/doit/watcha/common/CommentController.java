package org.doit.watcha.common;

import java.security.Principal;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/review")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody Map<String, Object> payload, Principal principal) {
        if (principal == null) return ResponseEntity.status(401).body("로그인 필요");

        Integer workId = Integer.parseInt(payload.get("workId").toString());
        String content = payload.get("content").toString();
        String memberId = principal.getName();

        commentService.saveComment(workId, content, memberId);
        return ResponseEntity.ok("성공");
    }
    
    @GetMapping("/comments") // 브라우저 접속 주소: /mypage/my/comments
    public String myComments(@RequestParam(value = "type", defaultValue = "MOVIE") String type, 
                             Principal principal, Model model) {
        if (principal == null) return "redirect:/login";

        String memberId = principal.getName();
        
        // DB에서 해당 타입(MOVIE, SERIES 등)의 코멘트만 가져오기
        List<Comment> comments = commentService.getUserCommentsByType(memberId, type);

        model.addAttribute("comments", comments);
        model.addAttribute("currentType", type.toUpperCase()); // 대문자로 통일 (탭 강조용)
        
        // 💡 파일을 src/main/resources/templates/common/comment.html 에 두셨을 경우
        return "common/comment"; 
    }
    
    @PostMapping("/like")
    public ResponseEntity<CommentLikeResponse> toggleLike(
            @RequestBody Map<String, Integer> request,
            @AuthenticationPrincipal UserDetails userDetails) {
        
        if (userDetails == null) return ResponseEntity.status(401).build();

        Integer commentId = request.get("commentId");
        if (commentId == null) return ResponseEntity.badRequest().build();

        CommentLikeResponse response = commentService.toggleCommentLike(commentId, userDetails.getUsername());
        
        return ResponseEntity.ok(response);
    }
}