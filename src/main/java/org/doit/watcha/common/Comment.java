package org.doit.watcha.common;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.util.List;

import org.doit.watcha.work.Work;

@Entity
@Getter @Setter
@Table(name = "COMMENT")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "COMMENTID")
    private Integer commentId;

    @Column(name = "COMMENTCONTENT", length = 5000, nullable = false)
    private String commentContent;

    @Column(name = "COMMENTCREATEDATE")
    private LocalDate commentCreateDate = LocalDate.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "WORKID")
    private Work work;

    @Column(name = "MEMBERID") // 멤버 엔티티와 연관관계 설정 전이라면 우선 String으로 처리
    private String memberId;

    @Column(name = "LIKECOUNT")
    private Integer likeCount = 0;

    @Column(name = "HOTSCORE")
    private Integer hotScore = 0;

    @Column(name = "REPORTCOUNT")
    private Integer reportCount = 0;
    
    @OneToMany(mappedBy = "comment")
    private List<CommentLike> commentLikes;
}