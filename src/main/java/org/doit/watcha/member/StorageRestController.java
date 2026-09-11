package org.doit.watcha.member;

import org.doit.watcha.config.auth.PrincipalDetails;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;
import java.util.Map;

@RestController
@RequestMapping("/api/storage")
@RequiredArgsConstructor
public class StorageRestController {

    private final StorageService storageService;

    @PostMapping("/toggle")
    public ResponseEntity<?> toggleStorage(
            @RequestBody Map<String, Object> params,
            @AuthenticationPrincipal PrincipalDetails principalDetails) {
        
        if (principalDetails == null) return ResponseEntity.status(401).body("로그인이 필요합니다.");

        String memberId = principalDetails.getMember().getMemberId();
        Integer workId = Integer.parseInt(params.get("workId").toString());
        StorageStatus status = StorageStatus.valueOf(params.get("status").toString());

        storageService.toggleStorage(memberId, workId, status);
        
        return ResponseEntity.ok().body(Map.of("success", true));
    }
}