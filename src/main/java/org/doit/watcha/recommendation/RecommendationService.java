package org.doit.watcha.recommendation;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class RecommendationService {

    private final RecommendationRepository recommendationRepository;

    public RecommendationService(RecommendationRepository recommendationRepository) {
        this.recommendationRepository = recommendationRepository;
    }
    
    private void applyRatings(List<RecommendationCardDto> list,
            Map<Integer, Double> ratingMap) {

	for (RecommendationCardDto dto : list) {
	dto.setAvgRating(ratingMap.getOrDefault(dto.getWorkId(), 0.0));
	}
	}
    
    public Map<String, List<RecommendationCardDto>> getMovieCardsLimitedGenres() {

        Map<String, List<RecommendationCardDto>> result = new LinkedHashMap<>();

        // 🔥 작품별 평점 맵
        Map<Integer, Double> ratingMap = Map.of(
                11, 3.8,
                13, 3.9,
                15, 4.2,
                2, 0.0,
                7, 4.0,
                8, 3.8,
                1, 3.3,
                3, 3.7,
                5, 2.8
        );

        // 액션
        List<RecommendationCardDto> action =
                recommendationRepository.findByWorkIds(List.of(11, 13, 15));
        applyRatings(action, ratingMap);
        result.put("액션", action);

        // 로맨스
        List<RecommendationCardDto> romance =
                recommendationRepository.findByWorkIds(List.of(2, 7, 8));
        applyRatings(romance, ratingMap);
        result.put("로맨스", romance);

        // 휴먼/코미디
        List<RecommendationCardDto> human =
                recommendationRepository.findByWorkIds(List.of(1, 3, 5));
        applyRatings(human, ratingMap);
        result.put("휴먼/코미디", human);

        return result;
    }
}