package org.doit.watcha.member;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor // 💡 서비스 주입을 위해 추가
public class FollowController {

    private final FollowService followService;

    // 팔로워 리스트 (나를 팔로우하는 사람들)
    @GetMapping("/follower")
    public String follower(Model model, Principal principal) {
        if (principal == null) return "redirect:/login"; // 로그인 안했으면 로그인 페이지로

        String currentMemberId = principal.getName();
        List<Member> followerList = followService.getFollowerList(currentMemberId);
        
        model.addAttribute("followerList", followerList);
        return "member/follower";
    }

    // 팔로잉 리스트 (내가 팔로우하는 사람들)
    @GetMapping("/following")
    public String following(Model model, Principal principal) {
        if (principal == null) return "redirect:/login";

        String currentMemberId = principal.getName();
        // 💡 팔로잉 리스트를 가져오는 메서드도 서비스에 필요합니다 (아래 추가 설명 참고)
        List<Member> followingList = followService.getFollowingList(currentMemberId);
        
        model.addAttribute("followingList", followingList);
        return "member/following";
    }
}