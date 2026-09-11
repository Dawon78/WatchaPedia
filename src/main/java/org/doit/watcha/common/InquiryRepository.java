package org.doit.watcha.common;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface InquiryRepository extends JpaRepository<Inquiry, Integer> {
    // 특정 회원이 작성한 문의 내역만 최신순으로 가져오기 위한 메서드
    List<Inquiry> findByMemberIdOrderByInquiryDateDesc(String memberId);
}