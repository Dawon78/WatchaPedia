package org.doit.watcha.common;

import org.doit.watcha.member.RatingService;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import lombok.RequiredArgsConstructor;

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalControllerAdvice {

    private final RatingService ratingService;

    // 모든 HTML에서 ${totalCount} 라는 이름으로 꺼내 쓸 수 있게 함
    @ModelAttribute("totalCount")
    public Long totalCount() {
        // 레이팅 테이블의 전체 갯수 반환
        return ratingService.getTotalRatingCount(); 
    }
}