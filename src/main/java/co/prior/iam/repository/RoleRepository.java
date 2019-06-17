package co.prior.iam.repository;

import co.prior.iam.entity.IamMsRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<IamMsRole,Long>{



   Optional<IamMsRole>  findByRoleCodeAndSystemIdAndIsDeleted(String roleCode , Long systemId , String isDelete);


    List<IamMsRole> findBySystemIdAndIsDeleted(Long systemId, String isDelete);
}
