package org.doit.watcha.member;

import lombok.RequiredArgsConstructor;
import org.doit.watcha.config.auth.PrincipalDetails;
import org.doit.watcha.common.CommentService;
import org.doit.watcha.common.Comment;
import org.doit.watcha.collection.CollectionService; // 🚩 추가됨
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MyPageController {

    private final MemberService memberService;
    private final FollowService followService;
    private final RatingService ratingService;
    private final CommentService commentService;
    private final CollectionService collectionService; // 🚩 1. 컬렉션 서비스 주입 추가

    @GetMapping("/mypage")
    public String myPage(
            @AuthenticationPrincipal PrincipalDetails principalDetails,
            @RequestParam(value = "year", required = false) Integer year,
            @RequestParam(value = "month", required = false) Integer month,
            Model model) {
        
        if (principalDetails == null) {
            return "redirect:/main";
        }
        
        Member loginUser = principalDetails.getMember();
        String memberId = loginUser.getMemberId();
        
        // 1. 회원 정보 및 기본 카운트
        model.addAttribute("member", memberService.findById(memberId));
        model.addAttribute("followerCount", followService.getFollowerCount(memberId));
        model.addAttribute("followingCount", followService.getFollowingCount(memberId));
        model.addAttribute("totalRatingCount", ratingService.getTotalCountByMember(memberId));
        model.addAttribute("totalCommentCount", commentService.getTotalCommentCount(memberId));

        // 🚩 2. 컬렉션 개수 데이터 추가 (누락되었던 부분)
        // collectionService에 countByMemberId 메서드가 있어야 합니다.
        model.addAttribute("collectionCount", collectionService.countByMemberId(memberId));

        // 3. 친구 찾기 및 팔로우 리스트
        List<Member> suggestedFriends = memberService.findAllExceptMe(memberId);
        model.addAttribute("suggestedFriends", suggestedFriends);
        List<String> followingIds = followService.getFollowingIds(memberId);
        model.addAttribute("followingIds", followingIds);

        // 4. 달력 로직
        LocalDate targetDate = (year != null && month != null) ? LocalDate.of(year, month, 1) : LocalDate.now();
        model.addAttribute("currentYear", targetDate.getYear());
        model.addAttribute("currentMonth", targetDate.getMonthValue());
        model.addAttribute("startDayOfWeek", LocalDate.of(targetDate.getYear(), targetDate.getMonthValue(), 1).getDayOfWeek().getValue() % 7);
        model.addAttribute("lastDay", targetDate.lengthOfMonth());

        return "member/myPage";
    }

    @GetMapping("/comments")
    public String myComments(
            @AuthenticationPrincipal PrincipalDetails principalDetails,
            @RequestParam(value = "type", defaultValue = "MOVIE") String type, 
            Model model) {
        
        if (principalDetails == null) return "redirect:/login";
        String memberId = principalDetails.getMember().getMemberId();
        
        List<Comment> comments = commentService.getUserCommentsByType(memberId, type);
        model.addAttribute("comments", comments);
        model.addAttribute("currentType", type.toUpperCase()); 
        
        return "common/comment"; 
    }

    @GetMapping("/likes")
    public String getLikesPage(
            @RequestParam(value = "type", defaultValue = "people") String type,
            Model model) {
        model.addAttribute("currentType", type);
        return "member/likes";
    }
}