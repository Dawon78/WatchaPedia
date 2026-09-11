package org.doit.watcha.member;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MyCommentController {
	@GetMapping("/mycomment")
    public String mycomment() {
        return "member/mycomment";
    }
}