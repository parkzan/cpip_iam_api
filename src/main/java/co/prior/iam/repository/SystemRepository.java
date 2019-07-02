package co.prior.iam.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.prior.iam.entity.IamMsSystem;

@Repository
public interface SystemRepository extends JpaRepository<IamMsSystem, Long> {

	Optional<IamMsSystem> findBySystemCodeAndIsDeleted(String systeamCode, String isDelete);

	List<IamMsSystem> findByIsDeleted(String isDelete);

	Optional<IamMsSystem> findBySystemIdAndIsDeleted(Long systemId, String isDelete);
}
