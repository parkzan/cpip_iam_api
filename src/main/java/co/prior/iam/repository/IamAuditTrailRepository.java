package co.prior.iam.repository;

import co.prior.iam.entity.AuditId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import co.prior.iam.entity.IamAuditTrail;

@Repository
public interface IamAuditTrailRepository extends JpaRepository<IamAuditTrail, AuditId> {


        @Query(value = "select nextval('audit_id_seq')" , nativeQuery = true)
        long getAuditId();
}
