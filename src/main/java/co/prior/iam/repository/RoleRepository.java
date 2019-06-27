package co.prior.iam.repository;

import co.prior.iam.entity.IamMsRole;
import co.prior.iam.entity.IamMsSystem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<IamMsRole,Long>{



   Optional<IamMsRole>  findByRoleCodeAndIamMsSystemAndIsDeleted(String roleCode , IamMsSystem iamMsSystem , String isDelete);
    Optional<IamMsRole>  findByRoleCodeAndIamMsSystem_SystemIdAndIsDeleted(String roleCode , long systemId , String isDelete);

    List<IamMsRole> findByIamMsSystemAndIsDeleted(IamMsSystem iamMsSystem, String isDelete);

    List<IamMsRole> findByIamMsSystem_SystemIdAndIsDeleted(long systemId,String isDelete);

    Optional<IamMsRole> findByRoleIdAndIsDeleted(Long roleId,String isDelete);



    Optional<IamMsRole> findByIamMsSystem_SystemIdAndRoleIdAndIsDeleted(long systemId ,Long roleId,String isDelete);
}
