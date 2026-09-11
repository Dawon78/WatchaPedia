package org.doit.watcha.common;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class FaqController {

    @Autowired
    private FaqService faqService;

    @Autowired
    private InquiryService inquiryService;

    // 1. FAQ 목록
    @GetMapping("/faq")
    public String faq(Model model) {
        List<Faq> faqList = faqService.getFaqListOrderByFix();
        model.addAttribute("faqList", faqList);
        return "common/faq";
    }

    // 2. FAQ 전체보기 (필요시)
    @GetMapping("/faqall")
    public String getFaqList(Model model) {
        List<Faq> faqList = faqService.getFaqListOrderByFix();
        model.addAttribute("faqList", faqList);
        return "common/faqall";
    }

    // 3. FAQ 상세 페이지
    @GetMapping("/faqdetail")
    public String faqdetail(@RequestParam("id") Integer id, Model model) {
        Faq faq = faqService.getFaqDetail(id);
        model.addAttribute("faq", faq);
        return "common/faqdetail";
    }

    // 4. 문의 내역 리스트 (로그인한 사용자 본인의 것만)
    @GetMapping("/support")
    public String support(Model model, Principal principal) {
        // 보안상 로그인이 안 되어 있으면 메인이나 로그인 페이지로 튕겨내기
        if (principal == null) {
            return "redirect:/login"; 
        }
        
        // 서비스에서 로그인한 ID로 리스트를 가져옵니다.
        List<Inquiry> myInquiries = inquiryService.getInquiryList(principal.getName());
        model.addAttribute("inquiries", myInquiries);
        
        return "common/support";
    }

    // 5. 문의 등록 폼 이동
    @GetMapping("/supportwrite")
    public String supportWriteForm(Principal principal) {
        if (principal == null) return "redirect:/login";
        return "common/supportwrite";
    }

    // 6. 문의 등록 처리 (POST)
    @PostMapping("/supportwrite")
    public String processSupportWrite(
            @ModelAttribute Inquiry inquiry,
            @RequestParam(value = "uploadFiles", required = false) 
            List<MultipartFile> uploadFiles,
            Principal principal) throws IOException {

        if (principal != null) {
            inquiry.setMemberId(principal.getName());
        }
        
        inquiryService.saveInquiry(inquiry, uploadFiles);

        return "redirect:/support"; 
    }
}