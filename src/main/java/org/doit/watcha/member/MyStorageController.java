package org.doit.watcha.member;

import java.util.List;
import org.doit.watcha.config.auth.PrincipalDetails;
import org.doit.watcha.work.WorkType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor 
public class MyStorageController {

    private final StorageRepository storageRepository;
    private final RatingService ratingService; // 🚩 1. RatingService 필드 추가 (필수!)

    @GetMapping("/storage")
    public String myStorage(
            @RequestParam(value = "type", required = false, defaultValue = "MOVIE") String typeStr, // 🚩 이름을 typeStr로 변경
            @AuthenticationPrincipal PrincipalDetails principalDetails, 
            Model model) {

        String memberId = principalDetails.getMember().getMemberId();

        // 🚩 핵심: String을 Enum(WorkType)으로 변환
        WorkType type = WorkType.valueOf(typeStr); 

        // 🚩 이제 변환된 Enum 타입을 파라미터로 넘깁니다.
        long wantCount = storageRepository.countByMemberIdAndStorageStatusAndWork_WorkType(memberId, StorageStatus.보고싶어요, type);
        
        long watchingCount = storageRepository.countByMemberIdAndStorageStatusAndWork_WorkType(memberId, StorageStatus.보는중, type);

        List<Rating> allRatings = ratingService.getRatingsByType(memberId, type); 

        model.addAttribute("wantCount", wantCount);
        model.addAttribute("watchingCount", watchingCount);
        model.addAttribute("allRatings", allRatings);
        
        return "member/mystorage"; 
    }
    
    
}