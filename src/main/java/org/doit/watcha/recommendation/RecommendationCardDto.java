package org.doit.watcha.recommendation;

import java.time.LocalDate;

import org.doit.watcha.work.Work;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RecommendationCardDto {

    private Integer workId;        // ✅ Integer로 변경
    private String workTitle;
    private String workThumbnail;
    private LocalDate workPublishDate;
    private Double avgRating;

    public RecommendationCardDto(
            Integer workId,        // ✅ 여기 반드시 Integer
            String workTitle,
            String workThumbnail,
            LocalDate workPublishDate,
            Double avgRating
    ) {
        this.workId = workId;
        this.workTitle = workTitle;
        this.workThumbnail = workThumbnail;
        this.workPublishDate = workPublishDate;
        this.avgRating = avgRating;
    }
    
    private RecommendationCardDto toDto(Work w) {
        return new RecommendationCardDto(
                w.getWorkId(),
                w.getWorkTitle(),
                w.getWorkThumbnail(),
                w.getWorkPublishDate(),
                w.getAvgRating()
        );
    }
    

    public void setAvgRating(Double avgRating) {
        this.avgRating = avgRating;
    }
}