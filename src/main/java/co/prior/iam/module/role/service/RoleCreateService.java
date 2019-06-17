package co.prior.iam.module.role.service;



import co.prior.iam.entity.IamMsRole;
import co.prior.iam.module.role.model.request.RoleCreateReq;
import co.prior.iam.module.role.model.respone.RoleRespone;
import co.prior.iam.repository.RoleRepository;
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

    public RoleCreateService(RoleRepository roleRepository){
        this.roleRepository = roleRepository ;


    }

    @Transactional
    public void createRole(RoleCreateReq roleCreateReq) throws Exception {

            Optional<IamMsRole> iamMsRole = roleRepository.findByRoleCodeAndSystemIdAndIsDeleted(roleCreateReq.getRoleCode(),roleCreateReq.getSystemId(),"N");

            if (!iamMsRole.isPresent()){
                IamMsRole model = new IamMsRole();
                model.setRoleCode(roleCreateReq.getRoleCode());
                model.setRoleName(roleCreateReq.getRoleName());
                model.setRoleIcon(roleCreateReq.getRoleIcon());
                model.setSystemId(roleCreateReq.getSystemId());



                roleRepository.save(model);



            }else {
                throw new Exception("data not found");
            }

    }
}
