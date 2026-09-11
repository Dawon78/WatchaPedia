package org.doit.watcha.work;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.doit.watcha.member.Rating;
import org.doit.watcha.member.RatingService;
import org.doit.watcha.member.Storage; // 🚩 추가
import org.doit.watcha.member.StorageId; // 🚩 추가
import org.doit.watcha.member.StorageRepository; // 🚩 추가
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/work")
public class WorkController {

    private final WorkService workService;
    private final RatingService ratingService;
    private final StorageRepository storageRepository; 
    private final WorkRepository workRepository;

    @GetMapping("/{id}")
    public String workDetail(@PathVariable("id") Integer id, Model model, Principal principal) {
        // 1. DB에서 상세 데이터 조회
        Work work = workService.findWebtoonDetail(id);
        model.addAttribute("work", work);

        // 2. 로그인한 유저의 데이터 조회
        if (principal != null) {
            String memberId = principal.getName();
            
            // [기존 평가 점수 조회]
            Optional<Rating> ratingOpt = ratingService.findMyRating(memberId, id);
            Double score = 0.0;
            if (ratingOpt.isPresent() && ratingOpt.get().getRatingScore() != null) {
                score = ratingOpt.get().getRatingScore();
            }
            model.addAttribute("myRating", score);

            // 🚩 2. [보관 상태 조회 추가]
            StorageId storageId = new StorageId(memberId, id);
            Optional<Storage> storageOpt = storageRepository.findById(storageId);
            
            if (storageOpt.isPresent()) {
                // "보고싶어요" 혹은 "보는중" 문자열을 모델에 담음
                model.addAttribute("userStorageStatus", storageOpt.get().getStorageStatus().name());
            }
        } else {
            model.addAttribute("myRating", 0.0);
        }

        // 3. 전체 통계 조회 (평균 별점 등)
        model.addAttribute("avgRating", ratingService.getAverage(id));
        model.addAttribute("ratingCount", ratingService.getCount(id));
        model.addAttribute("totalCount", ratingService.getTotalRatingCount());

        return "webtoon/webtoon_detail"; 
    }
    
 // WorkController.java 내부의 search 메서드 수정
    @GetMapping("/search")
    @ResponseBody
    public List<Map<String, Object>> search(@RequestParam("q") String query) {
        // 1. DB에서 키워드로 검색 (인스턴스 workRepository 사용)
        List<Work> works = workRepository.findByWorkTitleContaining(query);
        
        // 2. JSON 변환 에러를 피하기 위해 필요한 데이터만 Map에 담기
        List<Map<String, Object>> result = new ArrayList<>();
        for (Work w : works) {
            Map<String, Object> map = new HashMap<>();
            map.put("workId", w.getWorkId());
            map.put("workTitle", w.getWorkTitle());
            map.put("workThumbnail", w.getWorkThumbnail());
            map.put("workType", w.getWorkType() != null ? w.getWorkType().toString() : "");
            result.add(map);
        }
        return result;
    }
}