package org.doit.watcha.recommendation;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RecommendationController {

    private final RecommendationService recommendationService;

    public RecommendationController(RecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }

    @GetMapping("/recommendation")
    public String recommendation(Model model) {

        Map<String, List<RecommendationCardDto>> movieSections =
                recommendationService.getMovieCardsLimitedGenres();

        model.addAttribute("movieSections", movieSections);

        return "recommendation/recommendation";
    }
}