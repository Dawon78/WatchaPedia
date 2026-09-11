package org.doit.watcha.megazine;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MagazineRepository extends JpaRepository<Magazine, Long> {

    // 카테고리별 최신순 정렬 (10개 전부)
    List<Magazine> findByMagazineCategoryIdOrderByMagazineCreateDateDesc(Integer categoryId);
    
    List<Magazine> findTop6ByOrderByMagazineCreateDateDesc();
    
    @Query("SELECT c.magazineCategoryName FROM MagazineCategory c WHERE c.magazineCategoryId = :id")
    String findNameById(@Param("id") Integer id);
}