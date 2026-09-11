package org.doit.watcha.work; // 본인의 패키지 경로에 맞게 수정

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode // 복합키 비교를 위해 필수!
public class WorkGenreId implements Serializable {
    private Integer workId;
    private Integer genreId;
}