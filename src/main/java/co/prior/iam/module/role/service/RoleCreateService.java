package co.prior.iam.module.role.service;



import co.prior.iam.entity.IamMsRole;
import co.prior.iam.entity.IamMsSystem;
import co.prior.iam.module.role.model.request.RoleCreateReq;
import co.prior.iam.module.role.model.respone.RoleRespone;
import co.prior.iam.repository.RoleRepository;
import co.prior.iam.repository.SystemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Date;
import java.util.Optional;

@Service
public class RoleCreateService {
    protected final Logger log = LoggerFactory.getLogger(getClass());


    RoleRepository roleRepository;

    SystemRepository systemRepository;

    public RoleCreateService(RoleRepository roleRepository , SystemRepository systemRepository){
        this.roleRepository = roleRepository ;
        this.systemRepository = systemRepository;


    }

    @Transactional
    public void createRole(RoleCreateReq roleCreateReq) throws Exception {

            IamMsSystem iamMsSystem = systemRepository.findBySystemIdAndIsDeleted(roleCreateReq.getSystemId(),"N")
                .orElseThrow(() -> new Exception("data not found"));

            System.out.println(iamMsSystem.getSystemId());

            Optional<IamMsRole> iamMsRole = roleRepository.findByRoleCodeAndIamMsSystemAndIsDeleted(roleCreateReq.getRoleCode(),iamMsSystem,"N");
        System.out.println(iamMsRole.isPresent());
            if (!iamMsRole.isPresent()){
                IamMsRole model = new IamMsRole();
                model.setRoleCode(roleCreateReq.getRoleCode());
                model.setRoleName(roleCreateReq.getRoleName());
                model.setRoleIcon(roleCreateReq.getRoleIcon());
                model.setIamMsSystem(iamMsSystem);



                roleRepository.save(model);



            }else {
                throw new Exception("Role code duplicate");
            }

    }
}
