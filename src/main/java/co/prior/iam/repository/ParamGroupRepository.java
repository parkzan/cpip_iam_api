package co.prior.iam.repository;

import co.prior.iam.entity.IamMsParameterGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ParamGroupRepository  extends JpaRepository<IamMsParameterGroup,Long> {

    Optional<IamMsParameterGroup> findByParamGroupAndIsDeleted(String paramGroup, String isDelete);
    List<IamMsParameterGroup> findByIsDeleted(String isDelete);
}
