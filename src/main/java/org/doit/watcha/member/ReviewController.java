package org.doit.watcha.member;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.doit.watcha.config.auth.PrincipalDetails;
import org.doit.watcha.work.Work; // Work 엔티티 임포트
import org.doit.watcha.work.WorkService; // WorkService 임포트
import org.doit.watcha.work.WorkType; // WorkType Enum 임포트
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ReviewController {

    private final RatingService ratingService;
    private final WorkService workService;

    // 🚩 A. [평가 통계 페이지] - "영화 1, 웹툰 2" 숫자가 나오는 곳
    @GetMapping("/review")
    public String reviewStats(Model model, Principal principal) {
        if (principal != null) {
            String memberId = principal.getName();
            // 각 카테고리별 내가 평가한 개수만 가져옴
            model.addAttribute("counts", ratingService.getMyRatingCounts(memberId));
        }
        return "member/review"; // 기존에 만드신 통계 HTML
    }

    @GetMapping("/mainreview")
    public String mainReview(
            @RequestParam(value = "type", defaultValue = "MOVIE") String type, 
            @AuthenticationPrincipal PrincipalDetails principalDetails, 
            Model model) {

        // 🚩 1. 서비스에서 전체 평가 개수(DB의 모든 Rating 레코드 수) 가져오기
        long totalCount = ratingService.getTotalRatingCount(); // 이 메서드를 서비스에 만드셔야 합니다.
        model.addAttribute("totalCount", totalCount);

        model.addAttribute("selectedType", type.toUpperCase());

        if (principalDetails != null) {
            String memberId = principalDetails.getMember().getMemberId();
            model.addAttribute("counts", ratingService.getMyRatingCounts(memberId));
            
            WorkType workType = WorkType.valueOf(type.toUpperCase());
            model.addAttribute("works", workService.findRandomWorksByType(workType));
        } else {
            model.addAttribute("counts", new java.util.HashMap<String, Long>());
            model.addAttribute("works", new java.util.ArrayList<Work>());
        }

        return "member/mainreview"; 
    }
    
    @GetMapping("/reviewdetail")
    public String reviewDetail(@AuthenticationPrincipal PrincipalDetails principalDetails, Model model) {
        String memberId = principalDetails.getMember().getMemberId();

        // 1. 전체 평가 리스트 (담은 순/최신 순 등 기본 정렬)
        List<Rating> allRatings = ratingService.findAllByMemberId(memberId);
        model.addAttribute("allRatings", allRatings);

        // 2. 별점별 그룹화된 데이터 (Map<Double, List<Rating>>)
        // 5.0, 4.5 ... 0.5 순서로 정렬된 맵을 가져옵니다.
        Map<Double, List<Rating>> ratingGroup = ratingService.getRatingsGroupedByScore(memberId);
        model.addAttribute("ratingGroup", ratingGroup);
        
        // 3. 별점 리스트 (반복문을 돌리기 위한 용도: 5.0 downTo 0.5)
        List<Double> scoreSteps = Arrays.asList(5.0, 4.5, 4.0, 3.5, 3.0, 2.5, 2.0, 1.5, 1.0, 0.5);
        model.addAttribute("scoreSteps", scoreSteps);

        return "member/reviewdetail";
    }
}