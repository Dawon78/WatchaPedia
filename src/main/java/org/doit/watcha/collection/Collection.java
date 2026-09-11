package org.doit.watcha.collection;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.doit.watcha.work.Work;
import org.hibernate.annotations.Formula;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Transient;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter 
@Setter
@Table(name = "COLLECTION")
@NoArgsConstructor  // 기본 생성자 필수
@AllArgsConstructor // Builder를 위해 필요
@Builder            // 데이터 생성을 편리하게
public class Collection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "COLLECTIONID")
    private Integer collectionId;

    @Column(name = "COLLECTIONTITLE", length = 100)
    private String collectionTitle;

    @Column(name = "COLLECTIONDESC", length = 1000)
    private String collectionDesc;

    @Column(name = "MEMBERID", nullable = false)
    private String memberId;

    @ManyToMany
    @JoinTable(
        name = "COLLECTION_WORK",
        joinColumns = @JoinColumn(name = "COLLECTIONID"),
        inverseJoinColumns = @JoinColumn(name = "WORKID")
    )
    @Builder.Default // 빌더 사용 시 리스트 초기화 보장
    private List<Work> works = new ArrayList<>();
    
    @Formula("(SELECT COUNT(*) FROM COLLECTIONLIKE cl WHERE cl.COLLECTIONID = COLLECTIONID)")
    private Integer likeCount;
    
    @CreatedDate // 생성 시 날짜 자동 입력
    @Column(name = "COLLECTIONCREATEDATE", updatable = false)
    private LocalDateTime collectionCreateDate;
    
 // Collection.java에 임시로 추가할 경우
    @Transient
    private boolean isLiked; // DB에는 저장되지 않는 가상 필드
}