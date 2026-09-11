package org.doit.watcha.member;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.doit.watcha.work.Work;
import java.time.LocalDate;

@Entity
@Getter @Setter
@Table(name = "STORAGE")
@IdClass(StorageId.class) // 복합키 식별자 클래스
public class Storage {

    @Id
    private String memberId;

    @Id
    private Integer workId;

    @Enumerated(EnumType.STRING)
    @Column(name = "STORAGESTATUS")
    private StorageStatus storageStatus; // '보고싶어요', '보는중'

    @Column(name = "STORAGEADDDATE")
    private LocalDate storageAddDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "WORKID", insertable = false, updatable = false)
    private Work work;
}