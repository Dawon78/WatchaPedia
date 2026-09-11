package org.doit.watcha.series;

import java.util.List;

import org.doit.watcha.work.PersonWork;
import org.doit.watcha.work.WorkService;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SeriesService {

    private final SeriesRepository seriesRepository;
    private final SeriesDetailRepository seriesDetailRepository;
    private final WorkService workService;

    public List<Series> getRangeWithDetail(Long start, Long end) {
        return seriesRepository.findWithDetailByTypeAndWorkIdBetween("SERIES", start, end);
    }
    public Series getById(Long id) {
        return seriesRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("영화가 없습니다. workId=" + id));
    }

    public SeriesDetail getSeriesDetailById(Long id) {
        return seriesDetailRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("상세 정보가 없습니다. workId=" + id));
    }
    public String getGenresForSeries(Long seriesId) {
        try {
            // Movie와 동일한 workId를 WorkService에서 조회
            return workService.findById(seriesId.intValue()).getGenre();
        } catch (Exception e) {
            return ""; // 장르 없으면 빈 문자열
        }
    }
    public List<PersonWork> getPersonWorksBySeriesId(Integer workId) {
        return workService.findById(workId).getPersonWorks(); 
        // workService.findById()는 Work 객체 반환, 거기서 personWorks 가져옴
    }

}