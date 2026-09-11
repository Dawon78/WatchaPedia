package org.doit.watcha.movie;

import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name="WORK")
public class Movie {

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
    
    @Column(name="WORKDESC")  
    private String description;
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "WORKID")
    private MovieDetail movieDetail; // <- 이렇게 연결
}