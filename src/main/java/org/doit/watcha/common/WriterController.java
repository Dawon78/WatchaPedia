package org.doit.watcha.common;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WriterController {
	@GetMapping("/writer")
    public String writer() {
        return "common/writer";
    }
}