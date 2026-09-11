package org.doit.watcha.movie;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

    // 기존 (전체 조회)
    List<Movie> findByTypeOrderByWorkIdAsc(String type);

    @Query("SELECT m FROM Movie m JOIN FETCH m.movieDetail " +
            "WHERE m.type = :type AND m.workId BETWEEN :start AND :end " +
            "ORDER BY m.workId ASC")
     List<Movie> findWithDetailByTypeAndWorkIdBetween(
             @Param("type") String type, 
             @Param("start") Long start, 
             @Param("end") Long end);
 
}
