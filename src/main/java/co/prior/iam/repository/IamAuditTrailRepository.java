package co.prior.iam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.prior.iam.entity.IamAuditTrail;

@Repository
public interface IamAuditTrailRepository extends JpaRepository<IamAuditTrail, Long> {

}
