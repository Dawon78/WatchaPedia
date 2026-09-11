package org.doit.watcha.news;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class NewsController {
	

	@GetMapping("/news")
	public String magazineDetail() {
		return "news/news";
	}
	@GetMapping("/news/friend")
	public String newsFriend() {
		return "news/news_friend";
	}
}
