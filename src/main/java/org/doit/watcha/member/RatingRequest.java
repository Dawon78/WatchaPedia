package org.doit.watcha.member;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class RatingRequest {
    private Double ratingScore;
    private Integer workId;
}