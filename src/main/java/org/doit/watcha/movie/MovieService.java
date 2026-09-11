package org.doit.watcha.movie;

import java.util.List;

import org.doit.watcha.work.PersonWork;
import org.doit.watcha.work.WorkService;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MovieService {

    private final MovieRepository movieRepository;
    private final MovieDetailRepository movieDetailRepository;
    private final WorkService workService;


    public List<Movie> getRangeWithDetail(Long start, Long end) {
        return movieRepository.findWithDetailByTypeAndWorkIdBetween("MOVIE", start, end);
    }

    public Movie getById(Long id) {
        return movieRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("영화가 없습니다. workId=" + id));
    }

    public MovieDetail getMovieDetailById(Long id) {
        return movieDetailRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("상세 정보가 없습니다. workId=" + id));
    }
    public String getGenresForMovie(Long movieId) {
        try {
            // Movie와 동일한 workId를 WorkService에서 조회
            return workService.findById(movieId.intValue()).getGenre();
        } catch (Exception e) {
            return ""; // 장르 없으면 빈 문자열
        }
    }
    public List<PersonWork> getPersonWorksByMovieId(Integer workId) {
        return workService.findById(workId).getPersonWorks(); 
        // workService.findById()는 Work 객체 반환, 거기서 personWorks 가져옴
    }

}