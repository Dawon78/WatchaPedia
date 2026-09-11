package org.doit.watcha.series;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;

@Getter
@Entity
@Table(name="WORK")
public class Series {

    @Id
    @Column(name="WORKID")
    private Long workId;

    @Column(name="WORKTYPE")
    private String type;

    @Column(name="WORKTITLE")
    private String title;

    @Column(name="WORKTHUMBNAIL")
    private String thumbnail;

    @Column(name="WORKRANK")
    private Integer rank;

    @Column(name="WORKPUBLISHDATE")   // ⭐ 추가
    private LocalDate publishDate;
    
    @Column(name="WORKDESC")  // 시놉시스 추가
    private String description;
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "WORKID")
    private SeriesDetail seriesDetail; // <- 이렇게 연결
}