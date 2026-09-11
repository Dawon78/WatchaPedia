package org.doit.watcha.book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;

    @Override
    public List<BookDetail> getBestsellers() {
        return bookRepository.findTop20ByOrderByHotScoreDesc();
    }

    @Override
    public List<BookDetail> getSearchRankBooks() {
        return bookRepository.findTop20ByOrderByHotScoreDesc();
    }

    @Override
    public List<BookDetail> getTopRatedBooks() {
        return bookRepository.findTop20ByOrderByHotScoreDesc();
    }

    @Override
    public List<BookDetail> getBooksByAuthor(String author) {
        return bookRepository.findByAuthorName(author);
    }

    @Override
    public List<BookDetail> getBooksByGenreId(Integer genreId) {
        return bookRepository.findByGenreId(genreId);
    }

    @Override
    public List<BookDetail> getBooksByUserCollection(String userName) {
        return bookRepository.findByUserName(userName);
    }
    
    @Override
    public BookDetail getBookDetail(Integer workId) {
        // findById를 사용하여 데이터를 가져오고 없으면 예외 처리를 합니다.
        return bookRepository.findById(workId).orElse(null);
    }
    
 // BookServiceImpl.java 예시
    public List<BookDetail> getNewestBooks() {
        return bookRepository.findNewestBooks(); // 새로 만든 리포지토리 메서드 호출
    }

    public List<BookDetail> getBestSellers() {
        return bookRepository.findBestSellers(); // 새로 만든 리포지토리 메서드 호출
    }
}