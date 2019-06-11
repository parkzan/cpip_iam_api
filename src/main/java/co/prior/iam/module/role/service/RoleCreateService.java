package co.prior.iam.module.role.service;



import co.prior.iam.entity.IamMsRole;
import co.prior.iam.module.role.model.req.RoleCreateReq;
import co.prior.iam.module.role.model.res.RoleRespone;
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
    public ResponseEntity<RoleRespone> createRole(RoleCreateReq roleCreateReq) throws Exception {

            Optional<IamMsRole> iamMsRole = roleRepository.findByRoleCodeAndSystemIdAndIsDeleted(roleCreateReq.getRoleCode(),roleCreateReq.getSystemId(),"N");
            RoleRespone respone = new RoleRespone();
            if (!iamMsRole.isPresent()){
                IamMsRole model = new IamMsRole();
                model.setRoleCode(roleCreateReq.getRoleCode());
                model.setIsDeleted("N");
                model.setRoleName(roleCreateReq.getRoleName());
                model.setRoleIcon(roleCreateReq.getRoleIcon());
                model.setSystemId(roleCreateReq.getSystemId());
                model.setCreatedBy("ADMIN");
                model.setCreatedDate(new Date());


                roleRepository.save(model);

                respone.setCode("S001");
                respone.setMessage("Success");

                return new ResponseEntity<>(respone,HttpStatus.CREATED);

            }
        respone.setCode("E001");
        respone.setMessage("data not found");
        return new ResponseEntity<>(respone,HttpStatus.NOT_FOUND);
    }
}
