package co.prior.iam.repository;


import co.prior.iam.entity.VParameterData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VParameterDataRepository extends JpaRepository<VParameterData , Long> {




}
