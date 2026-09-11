package org.doit.watcha.analysis;

import lombok.RequiredArgsConstructor;
import org.doit.watcha.config.auth.PrincipalDetails;
import org.doit.watcha.member.MemberService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class AnalysisController {

    private final MemberService memberService;

    @GetMapping("/analysis")
    public String analysis(
            @AuthenticationPrincipal PrincipalDetails principalDetails,
            Model model) {

        if (principalDetails == null) {
            return "redirect:/main";
        }

        String memberId = principalDetails.getMember().getMemberId();

        // 마이페이지와 동일하게 DB에서 다시 조회
        model.addAttribute("member", memberService.findById(memberId));
        
        

        return "analysis/analysis";
    }
}