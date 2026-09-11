package org.doit.watcha.common;

import org.doit.watcha.common.Faq;
import org.doit.watcha.common.FaqRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class FaqService {

    @Autowired
    private FaqRepository faqRepository;

    // 전체 목록 조회 (정렬 포함)
    public List<Faq> getFaqListOrderByFix() {
        return faqRepository.findAllByOrderByFaqfixDescFaqidDesc();
    }

    // 상세 내용 조회 (faqdetail 페이지용)
    public Faq getFaqDetail(Integer id) {
        return faqRepository.findById(id).orElse(null);
    }
}