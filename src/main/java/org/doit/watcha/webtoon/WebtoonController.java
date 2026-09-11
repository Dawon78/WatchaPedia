package org.doit.watcha.webtoon;

import org.doit.watcha.work.Work;
import org.doit.watcha.work.WorkService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class WebtoonController {

    private final WorkService workService;

    @GetMapping("/webtoon")
    public String webtoon(Model model) {

        // 최신 웹툰
        model.addAttribute("latestList",
                workService.findLatestWebtoon());

        // HOT 점수 높은 웹툰
        model.addAttribute("hotList",
                workService.findHotWebtoon());

        // 랭킹순 웹툰
        model.addAttribute("rankList",
                workService.findTopRankWebtoon());
        
        model.addAttribute("blList", workService.findBlWebtoon());
        model.addAttribute("romanceList", workService.findRomanceWebtoon());
        
        return "webtoon/webtoon";
        
        
    }

}