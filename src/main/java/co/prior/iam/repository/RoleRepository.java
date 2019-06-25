package co.prior.iam.repository;

import co.prior.iam.entity.IamMsRole;
import co.prior.iam.entity.IamMsSystem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<IamMsRole,Long>{



   Optional<IamMsRole>  findByRoleCodeAndIamMsSystemAndIsDeleted(String roleCode , IamMsSystem iamMsSystem , String isDelete);


    List<IamMsRole> findByIamMsSystemAndIsDeleted(IamMsSystem iamMsSystem, String isDelete);

    Optional<IamMsRole> findByRoleIdAndIsDeleted(Long roleId,String isDelete);

    Optional<IamMsRole> findByIamMsSystemAndRoleIdAndIsDeleted(IamMsSystem system ,Long roleId,String isDelete);
}
