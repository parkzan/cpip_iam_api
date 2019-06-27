package co.prior.iam.repository;

import co.prior.iam.entity.IamMsParameterCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParameterCodeRepository extends JpaRepository<IamMsParameterCode,Long> {
}
