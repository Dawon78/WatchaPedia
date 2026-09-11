package org.doit.watcha.member;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import org.doit.watcha.work.Work; // 🚩 Work 엔티티 임포트 확인

@Entity
@Getter @Setter
@Table(name = "RATING")
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RATINGID")
    private Integer ratingId;

    @Column(name = "RATINGSCORE")
    private Double ratingScore;

    @Column(name = "RATEDAY")
    private LocalDate rateDay;

    @Column(name = "MEMBERID")
    private String memberId;

    // 🚩 실제 DB에 저장할 때 사용하는 숫자 필드
    @Column(name = "WORKID")
    private Integer workId;

    // 🚩 [추가] JOIN FETCH를 위해 필요한 객체 필드
    // insertable=false, updatable=false는 숫자 필드(workId)와 겹치지 않게 하기 위함입니다.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "WORKID", insertable = false, updatable = false)
    private Work work;
}