package co.prior.iam.reposity;


import co.prior.iam.entity.IamMsSystem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SystemRepository  extends JpaRepository<IamMsSystem,Long>{

   Optional<IamMsSystem>  findBySystemCodeAndIsDeleted(String sysCode , String isDelete);

   List<IamMsSystem>  findByIsDeleted(String isDelete);
}
