package org.doit.watcha.series;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SeriesRepository extends JpaRepository<Series, Long> {

    // 기존 (전체 조회)
    List<Series> findByTypeOrderByWorkIdAsc(String type);

    @Query("SELECT m FROM Series m JOIN FETCH m.seriesDetail " +
            "WHERE m.type = :type AND m.workId BETWEEN :start AND :end " +
            "ORDER BY m.workId ASC")
     List<Series> findWithDetailByTypeAndWorkIdBetween(
             @Param("type") String type, 
             @Param("start") Long start, 
             @Param("end") Long end);
}
