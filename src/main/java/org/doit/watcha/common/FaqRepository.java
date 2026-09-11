package org.doit.watcha.common;

import org.doit.watcha.common.Faq;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface FaqRepository extends JpaRepository<Faq, Integer> {
    // 필드명이 faqfix, faqid이므로 첫 글자를 대문자로 써서 조합합니다.
    List<Faq> findAllByOrderByFaqfixDescFaqidDesc();
}