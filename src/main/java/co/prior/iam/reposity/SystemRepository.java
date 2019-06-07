package co.prior.iam.reposity;


import co.prior.iam.entity.SystemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SystemRepository  extends JpaRepository<SystemEntity,Long>, JpaSpecificationExecutor<SystemEntity> {

    SystemEntity findBySystemCodeAndIsDeleted(String sysCode , String isDelete);

    List<SystemEntity> findByIsDeleted(String isDelete);
}
