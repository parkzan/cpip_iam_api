package co.prior.iam.repository;


import co.prior.iam.entity.PpiMsSurvey;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PpiMsSurveyRepository extends JpaRepository<PpiMsSurvey, Long>{
	
	List<PpiMsSurvey> findBySurveyCodeLikeAndIsDeleted(String surveyCode, String isDeleted);

	@Query(value = "SELECT MAX(SUBSTR(SURVEY_CODE,6,10)) "
			+ "FROM PPI_MS_SURVEY WHERE SURVEY_CODE LIKE ?1" , nativeQuery = true)
	String findNativeQueryMaxSurveyCodeBySurveyCode(String surveyCode);

	Optional<PpiMsSurvey> findBySurveyIdAndIsDeleted(long surveyId, String isDeleted);
	Optional<PpiMsSurvey> findBySurveyCodeAndIsDeleted(String surveyCode, String isDelete);
	Page<PpiMsSurvey> findPageableByIsDeletedOrderBySurveyCode(String isDeleted, Pageable pageable);
	Optional<PpiMsSurvey> findByTaxIdAndIsDeleted(String taxId, String isDeleted);


}
