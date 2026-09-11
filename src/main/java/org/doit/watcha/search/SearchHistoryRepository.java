package org.doit.watcha.search;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SearchHistoryRepository
        extends JpaRepository<SearchHistory, Long> {

    List<SearchHistory> findTop10ByMemberIdOrderBySearchedAtDesc(String memberId);
    
    

    void deleteByMemberId(String memberId);
}