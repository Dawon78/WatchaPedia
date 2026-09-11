package org.doit.watcha.feed;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/feed")
public class FeedController {

	@GetMapping("/comments")
	public String commentPage(
	        @RequestParam(name = "filter", defaultValue = "popular") String filter,
	        Model model) {

	    model.addAttribute("filter", filter);
	    return "feed/comment";
	}
	
	
	
}