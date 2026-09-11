package org.doit.watcha.work;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter 
@Setter
@Table(name = "GENRE")
public class Genre {

    @Id  // <--- 이 어노테이션이 반드시 있어야 합니다!
    @GeneratedValue(strategy = GenerationType.IDENTITY) // DB에서 AUTO_INCREMENT를 쓰므로 추가
    @Column(name = "GENREID")
    private Integer genreId;

    @Column(name = "GENRENAME", nullable = false, unique = true)
    private String genreName;
}