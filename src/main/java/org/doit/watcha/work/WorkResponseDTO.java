package org.doit.watcha.work;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class WorkResponseDTO {
    private Integer workId;
    private String workTitle;
    private String workThumbnail;
    private String workDesc;
    private String genre; // <--- 여기에 "로맨스, 판타지"가 들어감

    // 생성자 (JPQL에서 사용하기 위함)
    public WorkResponseDTO(Integer workId, String workTitle, String workThumbnail, String workDesc, String genre) {
        this.workId = workId;
        this.workTitle = workTitle;
        this.workThumbnail = workThumbnail;
        this.workDesc = workDesc;
        this.genre = genre;
    }
}