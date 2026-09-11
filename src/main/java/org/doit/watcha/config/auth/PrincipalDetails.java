package org.doit.watcha.config.auth;

import org.doit.watcha.member.Member;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.ArrayList;
import java.util.Collection;

public class PrincipalDetails implements UserDetails {

    private Member member; // 우리 엔티티

    public PrincipalDetails(Member member) {
        this.member = member;
    }

    public Member getMember() { return member; } // 엔티티 통째로 꺼내기용

    @Override
    public String getPassword() { return member.getMemberPw(); } // 비밀번호 필드 연결

    @Override
    public String getUsername() { return member.getMemberId(); } // 아이디(이메일) 필드 연결

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> roles = new ArrayList<>();
        
        if (Boolean.TRUE.equals(member.getIsAdmin())) {
            roles.add(() -> "ROLE_ADMIN");
        } else {
            roles.add(() -> "ROLE_USER");
        }
        return roles;
    }

    @Override
    public boolean isAccountNonExpired() { return true; }
    @Override
    public boolean isAccountNonLocked() { return true; }
    @Override
    public boolean isCredentialsNonExpired() { return true; }
    @Override
    public boolean isEnabled() { return true; }
}