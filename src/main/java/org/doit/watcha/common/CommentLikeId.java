package org.doit.watcha.common;

import java.io.Serializable;
import lombok.*;

@Data // @Getter, @Setter, @EqualsAndHashCode, @RequiredArgsConstructor 포함
@NoArgsConstructor
@AllArgsConstructor
public class CommentLikeId implements Serializable {
    private String memberId;
    private Integer commentId;
}