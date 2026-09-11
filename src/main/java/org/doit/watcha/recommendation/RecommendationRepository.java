package org.doit.watcha.recommendation;

import java.util.List;

import org.doit.watcha.work.Work;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RecommendationRepository extends JpaRepository<Work, Integer> {

	@Query("""
		    SELECT new org.doit.watcha.recommendation.RecommendationCardDto(
		        w.workId,
		        w.workTitle,
		        CONCAT('/images/rec_', w.workId, '.jpg'),
		        w.workPublishDate,
		        w.avgRating
		    )
		    FROM Work w
		    WHERE w.workId IN :ids
		""")
		List<RecommendationCardDto> findByWorkIds(@Param("ids") List<Integer> ids);
	
}