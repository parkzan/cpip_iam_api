package co.prior.iam.repository;

import co.prior.iam.entity.IamMsParameterInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ParamInfoRepository extends JpaRepository<IamMsParameterInfo,Long> {

    List<IamMsParameterInfo> findByParamGroup_ParamGroupAndIsDeleted(String paramGroup , String isDelete);
    Optional<IamMsParameterInfo> findByParamInfoAndIsDeleted(String paramInfo , String isDelete);
}
