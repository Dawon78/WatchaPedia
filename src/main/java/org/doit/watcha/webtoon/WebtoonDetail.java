package org.doit.watcha.webtoon;

import org.doit.watcha.work.Work;
import jakarta.persistence.Id;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
@Table(name = "WEBTOONDETAIL")
public class WebtoonDetail {
    @Id
    @Column(name = "WORKID")
    private Integer workId;

    @OneToOne
    @MapsId // Work의 ID를 그대로 사용
    @JoinColumn(name = "WORKID")
    private Work work;

    private String webtoonState;
    
}