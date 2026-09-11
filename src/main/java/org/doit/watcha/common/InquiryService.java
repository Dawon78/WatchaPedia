package org.doit.watcha.common;

import java.io.File; // 반드시 java.io.File 이어야 합니다!
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // 권장되는 트랜잭션 임포트
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class InquiryService {

    private final InquiryRepository inquiryRepository;
    private final InquiryFileRepository inquiryFileRepository;

    public void saveInquiry(Inquiry inquiry,
    						List<MultipartFile> files)
    						throws IOException {
    	
        Inquiry savedInquiry = inquiryRepository.save(inquiry);

        if (files != null && !files.isEmpty()) {
            String uploadPath = "C:/watcha/uploads/"; 
            File uploadDir = new File(uploadPath);
            
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            for (MultipartFile file : files) {
                if (file == null || file.isEmpty()) continue;

                String orgName = file.getOriginalFilename();
                String uuidName = UUID.randomUUID().toString() + "_" + orgName;
                String fullPath = uploadPath + uuidName;

                file.transferTo(new File(fullPath));

                InquiryFile iFile = new InquiryFile();
                iFile.setInquiryId(savedInquiry.getInquiryId()); 
                iFile.setInquiryFileName(orgName);
                iFile.setInquiryFilePath(fullPath);
                
                inquiryFileRepository.save(iFile);
            }
        }
        
    }
    
    public List<Inquiry> getInquiryList(String memberId) {
        return inquiryRepository.findByMemberIdOrderByInquiryDateDesc(memberId);
    }
}