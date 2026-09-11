package org.doit.watcha.work; // 패키지 경로는 본인 설정에 맞게 수정

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter @Setter
@Table(name = "PERSON")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PERSONID")
    private Integer personId;

    @Column(name = "NAME")
    private String name;

    @Column(name = "PROFILEURL")
    private String profileUrl;


    // 역방향 매핑 (필요 시)
    @OneToMany(mappedBy = "person")
    private List<PersonWork> personWorks;
}