package org.doit.watcha.main;

import java.util.List;

import org.doit.watcha.common.CommentService;
import org.doit.watcha.megazine.MagazineService;
import org.doit.watcha.work.Person;
import org.doit.watcha.work.PersonWork;
import org.doit.watcha.work.WorkService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    private final CommentService commentService;

    private final MagazineService magazineService;

    private final WorkService workService;

    MainController(WorkService workService, MagazineService magazineService, CommentService commentService) {
        this.workService = workService;
        this.magazineService = magazineService;
        this.commentService = commentService;
    }

    @GetMapping("/main")
    public String home(Model model) {

        model.addAttribute("hotMovieList",
                workService.findHotMovie());
        
        model.addAttribute("magazineList",
                magazineService.findTop6());
        
        model.addAttribute("hotCommentList",
                commentService.findHotComments());
        
        model.addAttribute("personRankingList",
                workService.getPersonRankingList());

        return "common/main";
    }
    

}