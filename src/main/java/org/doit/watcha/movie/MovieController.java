package org.doit.watcha.movie;

import java.util.List;

import org.doit.watcha.work.PersonWork;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MovieController {
    
    private final MovieService movieService;

    // 영화 리스트 페이지
    @GetMapping("/movie")
    public String movie(Model model) {
        model.addAttribute("movies1", movieService.getRangeWithDetail(1L, 5L));
        model.addAttribute("movies2", movieService.getRangeWithDetail(6L, 10L));
        model.addAttribute("movies3", movieService.getRangeWithDetail(11L, 16L));
        return "movie/movie";
    }
    
    // 상세 페이지 (/movie/detail/{id})
 // 상세 페이지 (/movie/detail/{id})
    @GetMapping("/movie/detail/{id}")
    public String movieDetail(@PathVariable("id") Long id, Model model) {
        Movie movie = movieService.getById(id);
        MovieDetail movieDetail = movieService.getMovieDetailById(id);

        // 장르 가져오기
        String genre = movieService.getGenresForMovie(id);
        List<PersonWork> castList = movieService.getPersonWorksByMovieId(movie.getWorkId().intValue());

        model.addAttribute("movie", movie);
        model.addAttribute("movieDetail", movieDetail);
        model.addAttribute("genre", genre); 
        model.addAttribute("castList", castList); 
        return "movie/movie_detail";
    }
}