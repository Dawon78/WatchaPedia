package org.doit.watcha.event;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class EventController {
	

	@GetMapping("/event")
	public String magazineDetail() {
		return "event/event";
	}

}
