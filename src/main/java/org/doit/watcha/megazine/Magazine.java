package org.doit.watcha.megazine;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "MAGAZINE")
public class Magazine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MAGAZINEID")
    private Integer magazineId;

    @Column(name = "MAGAZINETITLE")
    private String magazineTitle;

    @Column(name = "MAGAZINESUMMARY")
    private String magazineSummary;

    @Column(name = "MAGAZINETHUMBNAIL")
    private String magazineThumbnail;

    @Column(name = "MAGAZINEVIEWCOUNT")
    private Integer magazineViewCount;

    @Column(name = "MAGAZINECREATEDATE")
    private LocalDate magazineCreateDate;

    @Column(name = "MAGAZINECATEGORYID")
    private Integer magazineCategoryId;

    @Column(name = "MEMBERID")
    private String memberId;
    
    // 카테고리 연관관계 추가
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MAGAZINECATEGORYID", insertable = false, updatable = false)
    private MagazineCategory magazineCategory;
    
    @Transient
    private String magazineCategoryName;
}