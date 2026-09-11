package org.doit.watcha.series;

import java.util.List;

import org.doit.watcha.work.PersonWork;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class SeriesController {
	
	private final SeriesService seriesService;
	
    @GetMapping("/series")
    public String series(Model model) {

    	model.addAttribute("series1", seriesService.getRangeWithDetail(101L, 105L));
    	model.addAttribute("series2", seriesService.getRangeWithDetail(106L, 110L));
    	model.addAttribute("series3", seriesService.getRangeWithDetail(111L, 116L));

        return "series/series";
	}
    
    @GetMapping("/series/detail/{id}")
    public String seriesDetail(@PathVariable("id") Long id, Model model) {
    	Series series= seriesService.getById(id);
    	SeriesDetail seriesDetail = seriesService.getSeriesDetailById(id);

        // 장르 가져오기
        String genre = seriesService.getGenresForSeries(id);
        List<PersonWork> castList = seriesService.getPersonWorksBySeriesId(series.getWorkId().intValue());

        model.addAttribute("series", series);
        model.addAttribute("seriesDetail", seriesDetail);
        model.addAttribute("genre", genre); 
        model.addAttribute("castList", castList); 
        
	    return "series/series_detail";
	}
}
