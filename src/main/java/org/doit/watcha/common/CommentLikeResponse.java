package org.doit.watcha.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommentLikeResponse {
    private boolean isLiked;
    private Integer newLikeCount;
}