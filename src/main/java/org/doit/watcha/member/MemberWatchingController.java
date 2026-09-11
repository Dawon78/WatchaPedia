package org.doit.watcha.member;

import java.util.List;
import org.doit.watcha.config.auth.PrincipalDetails;
import org.doit.watcha.work.WorkType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model; // 🚩 반드시 이 패키지를 임포트해야 합니다!
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor // 🚩 리포지토리 주입을 위해 필수!
public class MemberWatchingController {

    private final StorageRepository storageRepository;

    // 1. 보고싶어요 리스트
    @GetMapping("/memberwatch")
    public String memberWatchList(
            @RequestParam(value = "type", required = false, defaultValue = "MOVIE") String typeStr,
            @AuthenticationPrincipal PrincipalDetails principalDetails, 
            Model model) {

        String memberId = principalDetails.getMember().getMemberId();
        WorkType type = WorkType.valueOf(typeStr);

        // '보고싶어요' 상태인 리스트 조회
        List<Storage> watchList = storageRepository.findAllByMemberIdAndStorageStatusAndWork_WorkType(
                memberId, StorageStatus.보고싶어요, type);

        model.addAttribute("watchList", watchList);
        model.addAttribute("type", type); 
        
        return "member/memberwatch"; 
    }

    // 2. 보는 중 리스트 (동일한 로직, 상태만 다름)
    @GetMapping("/memberwatching")
    public String memberWatchingList(
            @RequestParam(value = "type", required = false, defaultValue = "MOVIE") String typeStr,
            @AuthenticationPrincipal PrincipalDetails principalDetails, 
            Model model) {

        String memberId = principalDetails.getMember().getMemberId();
        WorkType type = WorkType.valueOf(typeStr);

        // '보는중' 상태인 리스트 조회
        List<Storage> watchingList = storageRepository.findAllByMemberIdAndStorageStatusAndWork_WorkType(
                memberId, StorageStatus.보는중, type);

        model.addAttribute("watchList", watchingList); // HTML 재사용을 위해 키값을 맞춰주는 게 편합니다.
        model.addAttribute("type", type);
        model.addAttribute("isWatching", true); // 보는 중 페이지임을 알리는 플래그
        
        return "member/memberwatching";
    }
}