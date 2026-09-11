package org.doit.watcha.common;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "COMMENTLIKE")
@IdClass(CommentLikeId.class)
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentLike {

    @Id
    @Column(name = "MEMBERID")
    private String memberId;

    @Id
    @Column(name = "COMMENTID")
    private Integer commentId;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COMMENTID", insertable = false, updatable = false)
    private Comment comment;
    
}