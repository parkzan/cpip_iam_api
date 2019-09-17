package co.prior.iam.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.prior.iam.entity.IamMsParameterInfo;

@Repository
public interface ParamInfoRepository extends JpaRepository<IamMsParameterInfo, Long> {

	Optional<IamMsParameterInfo> findByParamInfoAndIsDeleted(String paramInfo, String isDeleted);

	boolean existsByParamInfoAndIsDeleted(String paramInfo, String isDeleted);
	
	List<IamMsParameterInfo> findByParamGroup_ParamGroupAndIsDeleted(String paramGroup, String isDeleted);
}
