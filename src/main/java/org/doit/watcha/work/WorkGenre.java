package org.doit.watcha.work;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
@Table(name = "WORK_GENRE")
@IdClass(WorkGenreId.class) // 위에서 만든 클래스 이름을 여기에 적습니다.
public class WorkGenre {

    @Id
    @Column(name = "WORKID")
    private Integer workId;

    @Id
    @Column(name = "GENREID")
    private Integer genreId;

    // 장르 이름을 가져오기 위한 다리
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "GENREID", insertable = false, updatable = false)
    private Genre genre;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "WORKID", insertable = false, updatable = false)
    private Work work;
}