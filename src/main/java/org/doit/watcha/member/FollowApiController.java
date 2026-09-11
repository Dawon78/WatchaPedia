package org.doit.watcha.member; // 패키지 경로는 프로젝트에 맞게 수정하세요.

import lombok.RequiredArgsConstructor;
import org.doit.watcha.config.auth.PrincipalDetails;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/follow")
@RequiredArgsConstructor
public class FollowApiController {

    private final FollowService followService;

    @PostMapping("/{followingId}")
    public ResponseEntity<String> toggleFollow(
            @PathVariable("followingId") String followingId,
            @AuthenticationPrincipal PrincipalDetails principalDetails) {

        // 1. 로그인 체크
        if (principalDetails == null) {
            return ResponseEntity.status(401).body("auth_required");
        }

        String followerId = principalDetails.getMember().getMemberId();

        // 2. 서비스 호출 (팔로우/언팔로우 토글)
        // 리턴값: "followed" 또는 "unfollowed"
        String result = followService.toggleFollow(followerId, followingId);

        return ResponseEntity.ok(result);
    }
}