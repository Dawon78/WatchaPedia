package org.doit.watcha.member;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder; // 추가
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder; // [추가] 암호화 도구 주입

    // 전체 회원 조회
    public List<Member> findAll() {
        return memberRepository.findAll();
    }

    // 아이디로 찾기
    public Member findById(String memberId) {
        return memberRepository.findById(memberId).orElse(null);
    }

    /**
     * 회원 저장 (회원가입 처리)
     */
    public void save(Member member) {
        // 1. 비밀번호 암호화 (가장 중요!)
        // 사용자가 입력한 "zz1103"을 "$2a$10$..." 형태의 암호문으로 변경합니다.
        String encodedPassword = passwordEncoder.encode(member.getMemberPw());
        member.setMemberPw(encodedPassword);

        // 2. 기본 프로필 이미지 설정
        if (member.getMemberPhoto() == null || member.getMemberPhoto().isEmpty()) {
            member.setMemberPhoto("/images/default_profile.png");
        }
        
        // 3. DB 저장
        memberRepository.save(member);
    }

    // [참고] 시큐리티 사용 시 이 login 메서드는 더 이상 호출되지 않습니다.
    // 시큐리티는 PrincipalDetailsService를 통해 로그인 절차를 진행합니다.
    @Deprecated
    public Member login(String memberId, String memberPw) {
        return memberRepository.findById(memberId)
                .filter(m -> passwordEncoder.matches(memberPw, m.getMemberPw()))
                .orElse(null);
    }
    
    public List<Member> findAllExceptMe(String memberId) {
        return memberRepository.findAll().stream()
                .filter(m -> !m.getMemberId().equals(memberId))
                .collect(Collectors.toList());
    }
}