package org.doit.watcha.search;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface KeywordRepository extends JpaRepository<Keyword, Long> {

    Optional<Keyword> findByKeywordText(String keywordText);

    List<Keyword> findTop10ByOrderBySearchCountDesc();
    
    
}