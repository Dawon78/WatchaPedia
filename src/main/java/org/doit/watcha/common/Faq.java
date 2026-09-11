package org.doit.watcha.common;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "FAQ")
@Data
public class Faq {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FAQID") // DB의 실제 컬럼명과 정확히 일치 시킴
    private Integer faqid;

    @Column(name = "FAQTITLE")
    private String faqtitle;

    @Column(name = "FAQCONTENT")
    private String faqcontent;

    @Column(name = "FAQFIX")
    private String faqfix;
}