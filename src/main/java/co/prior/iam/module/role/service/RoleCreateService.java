package co.prior.iam.module.role.service;


import co.prior.iam.common.BaseApiRespone;
import co.prior.iam.entity.IamMsRole;
import co.prior.iam.module.role.model.req.RoleCreateReq;
import co.prior.iam.module.role.model.res.RoleRespone;
import co.prior.iam.repository.RoleRepository;
import com.sun.deploy.net.HttpResponse;
import io.micrometer.core.instrument.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Date;
import java.util.Optional;

@Service
public class RoleCreateService {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    RoleRepository roleRepository;

    @Transactional
    public BaseApiRespone<RoleRespone> createRole(RoleCreateReq roleCreateReq) throws Exception {
        BaseApiRespone<RoleRespone> respone = new BaseApiRespone<>();
        if(!StringUtils.isBlank(roleCreateReq.getRoleCode()) && !StringUtils.isBlank(roleCreateReq.getRoleName()) && !StringUtils.isBlank(roleCreateReq.getSystemId())) {
            System.out.println(roleCreateReq.getRoleCode());
            Optional<IamMsRole> iamMsRole = roleRepository.findByRoleCodeAndSystemIdAndIsDeleted(roleCreateReq.getRoleCode(),Long.parseLong(roleCreateReq.getSystemId()),"N");

            if (!iamMsRole.isPresent()){
                IamMsRole model = new IamMsRole();
                model.setRoleCode(roleCreateReq.getRoleCode());
                model.setIsDeleted("N");
                model.setRoleName(roleCreateReq.getRoleName());
                model.setRoleIcon(roleCreateReq.getRoleIcon());
                model.setSystemId(Long.parseLong(roleCreateReq.getSystemId()) );
                model.setCreatedBy("ADMIN");
                model.setCreatedDate(new Date());


                roleRepository.save(model);

                respone.setResCode(HttpStatus.OK.toString());
                respone.setMessage("create " + model.getRoleCode() +" success" );

                return respone;

            }
            else {
                throw new Exception("duplicate role Code");
            }


        }
return respone;
    }
}
