package org.doit.watcha.work;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.doit.watcha.collection.Collection;
import org.doit.watcha.common.Comment;
import org.doit.watcha.webtoon.WebtoonDetail;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany; // 1:N 관계 어노테이션
import jakarta.persistence.OneToOne;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "work")
public class Work {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "WORKID")
    private Integer workId;

    @Enumerated(EnumType.STRING)
    @Column(name = "WORKTYPE")
    private WorkType workType;

    @Column(name = "WORKTITLE")
    private String workTitle;

    @Column(name = "WORKDESC", length = 5000)
    private String workDesc;

    @Column(name = "WORKTHUMBNAIL")
    private String workThumbnail;

    @Column(name = "WORKPUBLISHDATE")
    private LocalDate workPublishDate;

    // 1. 작가 정보 연결 (WRITERID 컬럼 사용)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "WRITERID", insertable = false, updatable = false)
    private Writer writer;

    // 3. 웹툰 상세 정보 (1:1 관계)
    @OneToOne(mappedBy = "work", fetch = FetchType.LAZY)
    private WebtoonDetail webtoonDetail;

    // 4. 출연진/제작진 리스트 (1:N 관계)
    @OneToMany(mappedBy = "work")
    private List<PersonWork> personWorks;


    @Column(name = "WORKRANK")
    private Integer workRank;
    
    @Column(name = "HOTSCORE")
    private Integer hotScore;

    @Transient
    private String genre;
    
 // Work.java 내부
    @OneToMany(mappedBy = "work")
    private List<WorkGenre> workGenres = new ArrayList<>();
    
 // Work.java 내부에 추가
    @ManyToMany(mappedBy = "works")
    private List<Collection> collections = new ArrayList<>();

    @org.hibernate.annotations.Formula("(SELECT COALESCE(AVG(r.RATINGSCORE), 0.0) FROM RATING r WHERE r.WORKID = WORKID)")
    private Double avgRating;
    
    @OneToMany(mappedBy = "work", cascade = CascadeType.ALL)
    @OrderBy("commentId DESC") // 최신 코멘트가 위로 오게 정렬
    private List<Comment> comments;
    
    // 평균 별점 (DB 컬럼 없음, 화면 출력용)
    @Transient
    private Double averageRating;
}