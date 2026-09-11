package org.doit.watcha.series;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeriesDetailRepository extends JpaRepository<SeriesDetail, Long> {
    // WORKID로 단일 조회 가능
    // Optional<MovieDetail> findById(Long workId); 는 JpaRepository에서 기본 제공
}