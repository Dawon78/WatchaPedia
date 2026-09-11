package org.doit.watcha.member;

import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;

// 💡 1. 복합키를 위한 ID 클래스 (별도 파일 안 만들어도 됨)
@Data
@NoArgsConstructor
@AllArgsConstructor
class FollowId implements Serializable {
    private String followerId;
    private String followingId;
}

// 💡 2. 실제 팔로우 엔티티
@Entity
@Table(name = "FOLLOW")
@IdClass(FollowId.class) // 복합키 클래스 지정
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Follow {

    @Id
    @Column(name = "FOLLOWER_ID")
    private String followerId;

    @Id
    @Column(name = "FOLLOWING_ID")
    private String followingId;
}