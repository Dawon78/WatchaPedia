package org.doit.watcha.work;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PersonRepository extends JpaRepository<Person, Integer> {

    List<Person> findByNameContainingIgnoreCase(String keyword);
    
    @Query("""
          SELECT DISTINCT p
          FROM Person p
          LEFT JOIN PersonWork pw ON pw.personId = p.personId
          LEFT JOIN Work w ON w.workId = pw.workId
          WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%'))
             OR LOWER(w.workTitle) LIKE LOWER(CONCAT('%', :keyword, '%'))
          """)
          List<Person> searchRelatedPersons(@Param("keyword") String keyword);


}