package co.prior.iam.repository;


import co.prior.iam.entity.PpiMsProvince;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PpiMsProvinceRepository extends JpaRepository<PpiMsProvince,Long> {


    List<PpiMsProvince> findByProvinceNameContains(String provinceName);
    List<PpiMsProvince> findByPpiMsRegion_RegionIdAndIsDeleted(long regionCode, String isDeleted);
    Optional<PpiMsProvince> findByProvinceCodeAndIsDeleted(String provinceCode, String isDeleted);
    Optional<PpiMsProvince> findByProvinceIdAndIsDeleted(long provinceId, String isDeletesd);
    List<PpiMsProvince> findByIsDeletedOrderByProvinceName(String isDeleted);

}