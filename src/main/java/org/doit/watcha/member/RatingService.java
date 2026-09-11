package org.doit.watcha.member;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.List;
import java.util.stream.Collectors;

import org.doit.watcha.work.WorkType;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RatingService {
    private final RatingRepository ratingRepository;

    @Transactional
    public void saveOrUpdateRating(RatingRequest request, String memberId) {
        // 기존 평가 확인 (JPA 사용 시)
        Optional<Rating> existing = ratingRepository.findByMemberIdAndWorkId(memberId, request.getWorkId());

        if (existing.isPresent()) {
            // 수정
            Rating rating = existing.get();
            rating.setRatingScore(request.getRatingScore());
            rating.setRateDay(LocalDate.now());
        } else {
            // 신규 등록
            Rating rating = new Rating();
            rating.setRatingScore(request.getRatingScore());
            rating.setMemberId(memberId);
            rating.setWorkId(request.getWorkId());
            rating.setRateDay(LocalDate.now());
            ratingRepository.save(rating);
        }
    }
    public Optional<Rating> findMyRating(String memberId, Integer workId) {
        return ratingRepository.findByMemberIdAndWorkId(memberId, workId);
    }
    
 // RatingService.java 내부에 추가
    public Double getAverage(Integer workId) {
        Double avg = ratingRepository.getAverageRating(workId);
        return (avg != null) ? Math.round(avg * 10) / 10.0 : 0.0; // 소수점 첫째자리까지
    }

    public Long getCount(Integer workId) {
        return ratingRepository.countByWorkId(workId);
    }
    
    public Long getTotalRatingCount() {
        return ratingRepository.countAllRatings();
    }
    
    public Map<String, Long> getMyRatingCounts(String memberId) {
        Map<String, Long> counts = new HashMap<>();
        
        // 👈 2. Enum에 정의된 대문자 이름 그대로 사용해야 합니다.
        counts.put("MOVIE", ratingRepository.countByMemberIdAndWorkType(memberId, WorkType.MOVIE));
        counts.put("WEBTOON", ratingRepository.countByMemberIdAndWorkType(memberId, WorkType.WEBTOON));
        counts.put("SERIES", ratingRepository.countByMemberIdAndWorkType(memberId, WorkType.SERIES));
        counts.put("BOOK", ratingRepository.countByMemberIdAndWorkType(memberId, WorkType.BOOK));
        
        return counts;
    }
    
 // RatingService.java
    public Long getTotalCountByMember(String memberId) {
        return ratingRepository.countByMemberId(memberId);
    }
    
    public Map<Double, List<Rating>> getRatingsGroupedByScore(String memberId) {
        List<Rating> myRatings = ratingRepository.findAllByMemberId(memberId);
        
        return myRatings.stream()
                .collect(Collectors.groupingBy(r -> 
                    r.getRatingScore() != null ? r.getRatingScore() : 0.0
                ));
    }
    public List<Rating> findAllByMemberId(String memberId) {
        return ratingRepository.findAllByMemberId(memberId);
    }

    // 🚩 [수정] String type -> WorkType type 으로 변경
    public List<Rating> getRatingsByType(String memberId, WorkType type) {
        // 리포지토리도 아까 WorkType을 받도록 수정했으므로 타입이 딱 맞게 됩니다.
        return ratingRepository.findAllByMemberIdAndWork_WorkType(memberId, type);
    }
}