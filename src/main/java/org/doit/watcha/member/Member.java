package org.doit.watcha.member;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "member")
public class Member {

    @Id
    @Column(name = "memberid", length = 100)
    private String memberId;   // 이메일 PK

    @Column(name = "memberpw", length = 255)
    private String memberPw;

    @Column(name = "memberphoto", length = 100)
    private String memberPhoto;

    @Column(name = "membername", length = 50)
    private String memberName;

    @Column(name = "memberbgphoto", length = 100)
    private String memberBgPhoto;

    @Column(name = "memberbio", length = 100)
    private String memberBio;
    
    @Column(name = "is_admin") // 관리자
    private Boolean isAdmin;
}