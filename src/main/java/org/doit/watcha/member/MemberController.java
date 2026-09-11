package org.doit.watcha.member;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    // 회원가입 처리
    @PostMapping("/join")
    public String join(@ModelAttribute Member member) {
        memberService.save(member);
        return "redirect:/main?signupSuccess=true";
    }

    // 회원 목록 (테스트용)
    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("members", memberService.findAll());
        return "member/list";
    }
}