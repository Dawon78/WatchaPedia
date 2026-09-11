package org.doit.watcha.book;

import java.util.List;
import org.doit.watcha.book.BookDetail;

public interface BookService {
    List<BookDetail> getBestsellers();
    List<BookDetail> getTopRatedBooks();
    List<BookDetail> getSearchRankBooks();
    
    
 // 1. 신간 도서 조회 (출판일순)
    List<BookDetail> getNewestBooks();

    // 2. 베스트셀러 조회 (워크랭크순)
    List<BookDetail> getBestSellers();
    // 추가 카테고리
    List<BookDetail> getBooksByAuthor(String author);
    List<BookDetail> getBooksByGenreId(Integer genreId);
    List<BookDetail> getBooksByUserCollection(String userName);

    BookDetail getBookDetail(Integer workId);
}