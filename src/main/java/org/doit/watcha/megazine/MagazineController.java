package org.doit.watcha.megazine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MagazineController {

    private final MagazineService magazineService;

    @GetMapping("/magazine")
    public String magazine(Model model) {

        model.addAttribute("articleList", magazineService.getByCategory(1));
        model.addAttribute("watpediViewList", magazineService.getByCategory(2));
        model.addAttribute("curationList", magazineService.getByCategory(3));
        model.addAttribute("contentNewsList", magazineService.getByCategory(4));
        model.addAttribute("diggingClubList", magazineService.getByCategory(5));
        model.addAttribute("openEditorList", magazineService.getByCategory(6));

        return "magazine/magazine";
    }

	@GetMapping("/magazine/detail")
	public String magazineDetail() {
		return "magazine/magazine_detail";
	}
}