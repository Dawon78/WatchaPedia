package org.doit.watcha.common;

import java.security.Principal;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/collection")
@RequiredArgsConstructor
public class CollectionLikeController {

    private final CollectionLikeService likeService;

    @PostMapping("/{id}/like")
    public ResponseEntity<Boolean> toggleLike(@PathVariable("id") Integer collectionId, Principal principal) {
        if (principal == null) {
            return ResponseEntity.status(401).build(); // 권한 없음
        }
        
        boolean result = likeService.toggleLike(collectionId, principal.getName());
        return ResponseEntity.ok(result); // true: 좋아요, false: 취소
    }
}