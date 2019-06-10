package co.prior.iam.repository;

import co.prior.iam.entity.IamMsRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<IamMsRole,Long>{

    Optional<IamMsRole> findByRoleCodeAndIsDeleted(String roleCode , String isDelete);

   Optional<IamMsRole>  findByRoleCodeAndSystemIdAndIsDeleted(String roleCode , Long systemId , String isDelete);

    Optional<IamMsRole> findByRoleIdAndIsDeleted(Long roleId , String isDelete);

    Optional<List<IamMsRole>> findBySystemIdAndIsDeleted(Long systemId, String isDelete);
}
