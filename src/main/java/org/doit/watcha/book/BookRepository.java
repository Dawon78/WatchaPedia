package org.doit.watcha.book;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface BookRepository extends JpaRepository<BookDetail, Integer> {

   // 1. 작가 이름으로 찾기 (제한 해제)
   @Query(value = "SELECT bd.* FROM bookdetail bd " +
         "JOIN work w ON bd.workid = w.workid " +
         "JOIN writer wr ON w.writerid = wr.writerid " +
         "WHERE wr.writername = :author", nativeQuery = true)
   List<BookDetail> findByAuthorName(@Param("author") String author);

   // 2. 장르 이름으로 찾기 (제한 해제)
   @Query(value = "SELECT bd.* FROM bookdetail bd " +
            "JOIN work w ON bd.workid = w.workid " +
            "JOIN work_genre wg ON w.workid = wg.workid " + // 💡 테이블명: WORK_GENRE
            "WHERE wg.genreid = :genreId", nativeQuery = true)
List<BookDetail> findByGenreId(@Param("genreId") Integer genreId);
   
   // 1. 신간 도서: 출판일(workpublishdate) 내림차순 정렬 상위 20개
    // 💡 JOIN을 통해 WORK 테이블의 WORKPUBLISHDATE를 기준으로 정렬합니다.
    @Query(value = "SELECT bd.* FROM bookdetail bd " +
                   "JOIN work w ON bd.workid = w.workid " +
                   "ORDER BY w.workpublishdate DESC LIMIT 20", nativeQuery = true)
    List<BookDetail> findNewestBooks();
    
 // 2. 베스트셀러: 워크랭크(workrank) 오름차순 정렬 상위 20개
    // 💡 순위 데이터인 WORKRANK가 낮을수록(1위, 2위...) 상위권이므로 ASC(오름차순) 정렬합니다.
    @Query(value = "SELECT bd.* FROM bookdetail bd " +
                   "JOIN work w ON bd.workid = w.workid " +
                   "ORDER BY w.workrank ASC LIMIT 20", nativeQuery = true)
    List<BookDetail> findBestSellers();
    
   // 3. 특정 유저 컬렉션 (제한 해제)
   @Query(value = "SELECT bd.* FROM bookdetail bd " +
         "JOIN work w ON bd.workid = w.workid " +
         "JOIN writer wr ON w.writerid = wr.writerid " +
         "WHERE wr.writername = :userName", nativeQuery = true)
   List<BookDetail> findByUserName(@Param("userName") String userName);

   // 4. 베스트셀러/평점순 상위 20개
   @Query(value = "SELECT bd.* FROM bookdetail bd JOIN work w ON bd.workid = w.workid ORDER BY w.hotscore DESC LIMIT 20", nativeQuery = true)
   List<BookDetail> findTop20ByOrderByHotScoreDesc();
}