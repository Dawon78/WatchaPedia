package org.doit.watcha.work;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface WorkRepository extends JpaRepository<Work, Integer> {

    // 타입별 조회
    List<Work> findByWorkType(WorkType workType);

    // 최신 작품 (출간일 기준)
    List<Work> findTop10ByWorkTypeOrderByWorkPublishDateDesc(WorkType workType);

    // HOT 점수 순
    List<Work> findTop10ByWorkTypeOrderByHotScoreDesc(WorkType workType);

    // 랭킹 순
    List<Work> findTop10ByWorkTypeOrderByWorkRankAsc(WorkType workType);
    List<Work> findByWorkTitleContaining(String workTitle);
    
    @Query(value = "SELECT GROUP_CONCAT(g.GENRENAME SEPARATOR ', ') " +
            "FROM WORK_GENRE wg " +
            "JOIN GENRE g ON wg.GENREID = g.GENREID " +
            "WHERE wg.WORKID = :workId", nativeQuery = true)
String findGenreNamesByWorkId(@Param("workId") Integer workId);
    
    @Query("SELECT w FROM Work w " +
              "LEFT JOIN FETCH w.webtoonDetail " +
              "LEFT JOIN FETCH w.writer " +
              "LEFT JOIN FETCH w.personWorks pw " +
              "LEFT JOIN FETCH pw.person " +
              "WHERE w.workId = :workId")
       Optional<Work> findByIdWithAllDetails(@Param("workId") Integer workId);
    
 // 1. 특정 장르 톱 5 조회 수정
    @Query(value = "SELECT w.*, (SELECT COALESCE(AVG(r.RATINGSCORE), 0.0) FROM RATING r WHERE r.WORKID = w.WORKID) AS avgRating " +
                   "FROM WORK w " +
                   "JOIN WORK_GENRE wg ON w.WORKID = wg.WORKID " +
                   "WHERE wg.GENREID = :genreId AND w.WORKTYPE = 'WEBTOON' " +
                   "ORDER BY w.HOTSCORE DESC LIMIT 5", nativeQuery = true)
    List<Work> findTop5ByGenreId(@Param("genreId") Integer genreId);

    // 2. 랜덤 작품 조회 수정
    @Query(value = "SELECT w.*, (SELECT COALESCE(AVG(r.RATINGSCORE), 0.0) FROM RATING r WHERE r.WORKID = w.WORKID) AS avgRating " +
                   "FROM WORK w " +
                   "WHERE w.WORKTYPE = :#{#type.name()} " +
                   "ORDER BY RAND() LIMIT 10", nativeQuery = true)
    List<Work> findRandomWorksByType(@Param("type") WorkType type);
    
    // MOVIE + SERIES 타입만 HOTSCORE 기준 내림차순
    @Query("""
        SELECT w
        FROM Work w
        WHERE w.workType IN ('MOVIE', 'SERIES')
        ORDER BY w.hotScore DESC
    """)
    List<Work> findHotMovieAndSeries(org.springframework.data.domain.Pageable pageable);

    @Query("""
    	    SELECT DISTINCT p
    	    FROM PersonWork pw
    	    JOIN pw.person p
    	    LEFT JOIN FETCH p.personWorks pw2
    	    LEFT JOIN FETCH pw2.work
    	    ORDER BY (p.personId * 7 + 120) DESC
    	""")
    	List<Person> findPersonRanking();
    
    List<Work> findByWorkTitleContainingIgnoreCaseAndWorkType(
            String keyword,
            WorkType workType
    );
    
    List<Work> findByWorkTitleContainingIgnoreCase(String keyword);
    
    @Query("""
    	    SELECT DISTINCT w
    	    FROM Work w
    	    LEFT JOIN FETCH w.personWorks pw
    	    LEFT JOIN FETCH pw.person
    	    WHERE LOWER(w.workTitle) LIKE LOWER(CONCAT('%', :keyword, '%'))
    	""")
    	List<Work> searchWithCast(@Param("keyword") String keyword);

    
    @Query("""
    	    SELECT DISTINCT p
    	    FROM Person p
    	    JOIN PersonWork pw ON p.personId = pw.personId
    	    JOIN Work w ON w.workId = pw.workId
    	    WHERE LOWER(w.workTitle) LIKE LOWER(CONCAT('%', :keyword, '%'))
    	       OR LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%'))
    	""")
    	List<Person> searchRelatedPersons(@Param("keyword") String keyword);

}