package org.doit.watcha.config.auth;

import org.doit.watcha.member.Member;
import org.doit.watcha.member.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PrincipalDetailsService implements UserDetailsService {

    @Autowired
    private MemberRepository memberRepository; // 레포지토리 주입 확인

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findById(username)
                .orElseThrow(() -> new UsernameNotFoundException("사용자 없음"));

        // 로그인은 시큐리티가 내부적으로 matches()를 호출해 처리하므로 
        // 여기서는 Member 객체만 담아서 리턴하면 끝입니다!
        return new PrincipalDetails(member);
    }
}