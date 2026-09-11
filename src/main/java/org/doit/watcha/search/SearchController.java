package org.doit.watcha.search;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.doit.watcha.work.PersonService;
import org.doit.watcha.work.WorkService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.HttpSession;
import jakarta.websocket.Session;

@Controller
public class SearchController {

    private final WorkService workService;
    private final PersonService personService;
    private final KeywordService keywordService;

    public SearchController(WorkService workService,
                            PersonService personService,
                            KeywordService keywordService) {
        this.workService = workService;
        this.personService = personService;
        this.keywordService = keywordService;
    }

    // ===============================
    // 🔎 검색 페이지
    // ===============================
    @GetMapping("/search")
    public String search(@RequestParam(value = "keyword", required = false) String keyword,
                         Model model, HttpSession session) {

    	// 🔥 여기 추가
    	 String memberId = (String) session.getAttribute("memberId");  // 🔥 session 소문자
    	 model.addAttribute("keyword", keyword);
    	 keywordService.saveKeyword(keyword, memberId);

        model.addAttribute("movieList", List.of());
        model.addAttribute("seriesList", List.of());
        model.addAttribute("bookList", List.of());
        model.addAttribute("webtoonList", List.of());
        model.addAttribute("personList", List.of());
        model.addAttribute("allList", List.of());

        if (keyword != null && !keyword.trim().isEmpty()) {
            model.addAttribute("allList", workService.searchByTitle(keyword));
            model.addAttribute("personList", personService.searchByName(keyword));
        }

        return "search/search";
    }

	 // ===============================
	 // 🔥 검색 드롭다운 JSON
	 // ===============================
	 @ResponseBody
	 @GetMapping("/search/popup")
	 public Map<String, Object> getSearchPopup(HttpSession session) {
	
	     String memberId = (String) session.getAttribute("memberId");
	
	     Map<String, Object> result = new HashMap<>();
	     result.put("recent", keywordService.getRecentKeywords(memberId));
	     result.put("popular", keywordService.getPopularKeywords());
	
	     return result;
	 }
	
	 // ===============================
	 // 🗑 모두 삭제
	 // ===============================
	 @ResponseBody
	 @PostMapping("/search/clear-recent")
	 public String clearRecent(HttpSession session) {
	
	     String memberId = (String) session.getAttribute("memberId");
	     keywordService.deleteRecentByMember(memberId);
	
	     return "ok";
	 }
	
	 // ===============================
	 // ❌ 개별 삭제
	 // ===============================
	 @ResponseBody
	 @PostMapping("/search/delete-one")
	 public String deleteOne(@RequestParam(value = "keyword", required = false) String keyword) {

	     if (keyword != null && !keyword.trim().isEmpty()) {
	         keywordService.deleteOne(keyword);
	     }

	     return "ok";
	 }
	 
}