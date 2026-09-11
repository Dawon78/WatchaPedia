package org.doit.watcha.search;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
@Table(name = "KEYWORD")
public class Keyword {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long keywordId;

    @Column(unique = true)
    private String keywordText;

    private int searchCount;

    private LocalDateTime lastSearchedAt;

    private LocalDateTime createdAt;
}