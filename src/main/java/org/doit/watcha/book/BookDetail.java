package org.doit.watcha.book;

import org.doit.watcha.work.Work;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "BOOKDETAIL") // 테이블명 매핑
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookDetail {

    @Id
    @Column(name = "WORKID")
    private Integer workId; // 기본키

    @Column(name = "BOOKPAGECOUNT")
    private Integer bookPageCount; // 페이지 수

    @Column(name = "BOOKCONTENTS", length = 5000)
    private String bookContents; // 목차

    @Column(name = "BOOKINTRODUCE", length = 5000)
    private String bookIntroduce; // 책 소개

    /* 💡 WORK 테이블과의 1:1 관계 설정 (선택 사항)
     공통 정보를 가진 WORK 엔티티와 연관 관계를 맺고 싶다면 아래 코드를 사용하세요.
    */
    @OneToOne
    @MapsId // WORKID를 기본키이자 외래키로 사용
    @JoinColumn(name = "WORKID")
    private Work work; 
}