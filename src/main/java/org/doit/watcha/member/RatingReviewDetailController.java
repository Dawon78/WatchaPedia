package org.doit.watcha.member;

import java.util.List;
import java.util.Map;
import java.util.ArrayList; // 추가

import org.doit.watcha.config.auth.PrincipalDetails;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor; // 추가

@Controller
@RequiredArgsConstructor // 🚩 1. 서비스 주입을 위해 추가
public class RatingReviewDetailController {

    // 🚩 2. 서비스 변수 선언 (final 필수)
    private final RatingService ratingService;

    @GetMapping("/ratingreviewdetail")
    public String ratingReviewDetail(
            @RequestParam("score") Double score, 
            @AuthenticationPrincipal PrincipalDetails principalDetails, 
            Model model) {
        
        // 로그인 체크 (보안 강화)
        if (principalDetails == null) {
            return "redirect:/login"; 
        }

        String memberId = principalDetails.getMember().getMemberId();
        
        // 🚩 3. 서비스에서 그룹화된 맵 가져오기
        Map<Double, List<Rating>> ratingGroup = ratingService.getRatingsGroupedByScore(memberId);
        
        // 해당 점수(score)의 리스트만 추출, 없으면 빈 리스트 반환
        List<Rating> movieList = ratingGroup.getOrDefault(score, new ArrayList<>());

        model.addAttribute("selectedRating", score);
        model.addAttribute("ratingCount", movieList.size());
        model.addAttribute("movieList", movieList);

        return "member/ratingreviewdetail";
    }
}