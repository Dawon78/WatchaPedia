package org.doit.watcha.common;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WatchaCollectionController {
	@GetMapping("/watchacollection")
    public String watchacollection() {
        return "common/watchacollection";
    }
}