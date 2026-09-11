package org.doit.watcha.member;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import java.security.Principal;

@RestController
@RequestMapping("/api/rating")
@RequiredArgsConstructor
public class RatingController {

    private final RatingService ratingService;

    @PostMapping("/save")
    public ResponseEntity<String> saveRating(@RequestBody RatingRequest request, 
                                           Principal principal) { // 👈 시큐리티가 제공하는 로그인 정보
        
        // 1. 로그인 여부 체크
        if (principal == null) {
            System.out.println("❌ 로그인 정보가 없습니다 (Security Principal is null)");
            return ResponseEntity.status(401).body("로그인이 필요합니다.");
        }

        // 2. 로그인된 아이디(이메일 등) 가져오기
        String memberId = principal.getName(); 
        System.out.println("✅ 로그인된 유저 ID: " + memberId);

        // 3. 별점 저장 로직 실행
        ratingService.saveOrUpdateRating(request, memberId);
        
        return ResponseEntity.ok("success");
    }
}