package org.doit.watcha.book;

import org.springframework.beans.factory.annotation.Autowired; // 주입을 위해 추가
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class BookController {

    // 💡 1. 서비스 주입 (컴파일 에러 해결)
    @Autowired
    private BookService bookService; 

    @GetMapping("/book")
    public String bookPage(Model model) {
        // 각각 다른 데이터를 가져오도록 설정
        model.addAttribute("bestsellers", bookService.getBestsellers());
        model.addAttribute("workList", bookService.getSearchRankBooks());
        model.addAttribute("topRatedBooks", bookService.getTopRatedBooks());
     // 신간 도서 데이터를 가져와서 "newestBooks"라는 이름으로 담음
        model.addAttribute("newestBooks", bookService.getNewestBooks());
        
        // 베스트셀러 데이터를 가져와서 "bestSellers"라는 이름으로 담음
        model.addAttribute("bestSellers", bookService.getBestSellers());
        // 추가 요청 카테고리
        model.addAttribute("criticBooks", bookService.getBooksByUserCollection("이동진"));
        model.addAttribute("authorBooks", bookService.getBooksByAuthor("천선란"));
        model.addAttribute("dramaBooks", bookService.getBooksByGenreId(4));
        model.addAttribute("sfBooks", bookService.getBooksByGenreId(17));
        

        return "book/book";
    }

 // 💡 도서 상세 페이지 추가
    @GetMapping("/book/detail/{workId}")
    public String bookDetailPage(@PathVariable("workId") Integer workId, Model model) {
        // 1. 데이터 조회
        BookDetail bookDetail = bookService.getBookDetail(workId);
        model.addAttribute("work", bookDetail);

        // 💡 별점 계산 시 null 에러를 방지하기 위해 기본값 0을 담아줍니다.
        model.addAttribute("myRating", 0);      // 내가 매긴 별점
        model.addAttribute("avgRating", 0.0);   // 평균 별점
        model.addAttribute("ratingCount", 0);   // 평가 인원
        
        return "book/book_detail"; 
    }
}