package co.prior.iam.repository;

import co.prior.iam.entity.RoleEntity;
import io.micrometer.core.instrument.util.StringUtils;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface RoleRepository extends JpaRepository<RoleEntity,Long>, JpaSpecificationExecutor<RoleEntity> {

    RoleEntity findByRoleCodeAndIsDeleted(String roleCode , String isDelete);

    RoleEntity findByRoleCodeAndSystemIdAndIsDeleted(String roleCode ,String systemId , String isDelete);

    List<RoleEntity> findByIsDeleted(String isDelete);

    List<RoleEntity> findBySystemIdAndIsDeleted(String systemId, String isDelete);
}
