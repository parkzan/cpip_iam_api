package co.prior.iam.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.prior.iam.entity.IamMsParameterGroup;

@Repository
public interface ParamGroupRepository extends JpaRepository<IamMsParameterGroup, Long> {

	Optional<IamMsParameterGroup> findByParamGroupAndIsDeleted(String paramGroup, String isDeleted);
	
	boolean existsByParamGroupAndIsDeleted(String paramGroup, String isDeleted);

	List<IamMsParameterGroup> findByIsDeleted(String isDeleted);
}
