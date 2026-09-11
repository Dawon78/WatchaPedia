package org.doit.watcha.collection;

import java.security.Principal;
import java.util.List;

import org.doit.watcha.common.CollectionLikeService;
import org.doit.watcha.member.Member;
import org.doit.watcha.member.MemberService;
import org.doit.watcha.work.Work;
import org.doit.watcha.work.WorkService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/collection") // 클래스 상단에 공통 경로 지정
public class CollectionController {

    private final WorkService workService; 
    private final CollectionService collectionService;
    private final MemberService memberService;
    private final CollectionLikeService collectionLikeService; 
    
 // CollectionController.java

    @GetMapping("/list")
    public String collectionList(@RequestParam(value = "memberId", required = false) String memberId, Model model) {
        List<Collection> collections;
        
        if (memberId != null && !memberId.isEmpty()) {
            // 💡 특정 사용자의 컬렉션만 조회 (마이페이지에서 접근 시)
            collections = collectionService.findByMemberId(memberId);
            model.addAttribute("isMyCollection", true); // 화면 제목 처리를 위한 플래그
        } else {
            // 전체 컬렉션 조회
            collections = collectionService.findAll();
        }
        
        model.addAttribute("collections", collections);
        return "collection/collection";
    }
    
 // 새 컬렉션 작성 페이지 이동
    @GetMapping("/create")
    public String createForm(@RequestParam(value = "workId", required = false) Integer workId, Model model, Principal principal) {
        if (principal == null) return "redirect:/login";

        if (workId != null) {
            Work work = workService.findWebtoonDetail(workId); 
            model.addAttribute("work", work); // HTML의 ${work}와 이름 일치시킴
        }
        // workId가 없으면 모델에 "work"가 담기지 않아 null 상태가 됨
        
        return "collection/CreateCollection";
    }
    
 // 컬렉션 저장 실행
    @PostMapping("/save")
    public String saveCollection(CollectionRequest request, Principal principal) {
        if (principal == null) return "redirect:/login";

        String memberId = principal.getName();
        
        // 💡 저장된 컬렉션 객체를 받아옴
        Collection savedCollection = collectionService.save(request, memberId);
        
        // 💡 해당 컬렉션의 ID를 사용하여 상세 페이지로 리다이렉트
        return "redirect:/collection/detail?id=" + savedCollection.getCollectionId();
    }

    @GetMapping("/detail")
    public String collectionDetail(@RequestParam("id") Integer id, Model model, Principal principal) {
        Collection collection = collectionService.findById(id);
        
        // 💡 작성자의 ID를 통해 Member 객체 가져오기
        Member author = memberService.findById(collection.getMemberId()); 
        
        boolean isLiked = false;
        if (principal != null) {
            isLiked = collectionLikeService.isLiked(id, principal.getName());
        }
        
        model.addAttribute("collection", collection);
        model.addAttribute("author", author); // 💡 'author'라는 이름으로 모델에 추가
        model.addAttribute("isLiked", isLiked);
        
        return "collection/collectionDetail";
    }
    
    @DeleteMapping("/delete")
    @ResponseBody
    public ResponseEntity<String> delete(@RequestParam("id") Integer id, Principal principal) {
        Collection col = collectionService.findById(id);
        // 작성자 본인 확인
        if (col.getMemberId().equals(principal.getName())) {
            collectionService.deleteById(id);
            return ResponseEntity.ok("Deleted");
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }
    
 // 수정 페이지 이동
    @GetMapping("/edit")
    public String editForm(@RequestParam("id") Integer id, Model model, Principal principal) {
        // 1. 💡 로그인 체크 (null이면 로그인 페이지로)
        if (principal == null) {
            return "redirect:/login"; 
        }

        Collection collection = collectionService.findById(id);
        
        // 2. 작성자 본인 확인
        if (!collection.getMemberId().equals(principal.getName())) {
            // 본인이 아니면 경고창을 띄우거나 목록으로 리다이렉트
            return "redirect:/collection/list";
        }
        
        model.addAttribute("collection", collection);
        return "collection/EditCollection"; 
    }

    // 수정 실행
    @PostMapping("/update")
    public String updateCollection(CollectionRequest request, @RequestParam("id") Integer id, Principal principal) {
        if (principal == null) return "redirect:/login";
        
        collectionService.update(id, request);
        return "redirect:/collection/detail?id=" + id;
    }
}

