package co.prior.iam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.prior.iam.entity.IamMsUserRole;

@Repository
public interface IamMsUserRoleRepository extends JpaRepository<IamMsUserRole, Long> {

}
