package co.prior.iam.module.role.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.prior.iam.entity.IamMsRole;
import co.prior.iam.entity.IamMsRoleObject;
import co.prior.iam.error.exception.DataNotFoundException;
import co.prior.iam.model.AnswerFlag;
import co.prior.iam.model.ErrorCode;
import co.prior.iam.module.role.model.respone.ObjectModel;
import co.prior.iam.module.role.model.respone.RoleMapObjectRespone;
import co.prior.iam.repository.RoleObjectRepository;
import co.prior.iam.repository.RoleRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class GetRoleObjectService {

    private final RoleObjectRepository roleObjectRepository;
    private final RoleRepository roleRepository;

    GetRoleObjectService(RoleObjectRepository roleObjectRepository, RoleRepository roleRepository) {
        this.roleObjectRepository = roleObjectRepository ;
        this.roleRepository = roleRepository;
    }

    @Transactional
    public RoleMapObjectRespone getRoleObject(long systemId, long roleId) {
        log.info("Service getRoleObject systemId: {}, roleId: {}", systemId, roleId);

		IamMsRole iamMsRole = this.roleRepository.findByIamMsSystem_SystemIdAndRoleIdAndIsDeleted(
				systemId, roleId, AnswerFlag.N.toString())
                .orElseThrow(() -> new DataNotFoundException(ErrorCode.ROLE_NOT_FOUND));

        List<IamMsRoleObject> objectList = this.roleObjectRepository.findByIamMsRoleAndIsDeleted(iamMsRole,AnswerFlag.N.toString());
        List<ObjectModel> listChid = new ArrayList<>();
        RoleMapObjectRespone respone = new RoleMapObjectRespone();

        respone.setSystemId(iamMsRole.getIamMsSystem().getSystemId());
        respone.setRoleId(iamMsRole.getRoleId());
        respone.setRoleName(iamMsRole.getRoleName());

        if (objectList.isEmpty()) {
        	throw new DataNotFoundException(ErrorCode.ROLE_OBJECT_NOT_FOUND);
        }
        
        for (IamMsRoleObject obj : objectList) {
            ObjectModel objectModel = new ObjectModel();
            if (obj.getIamMsObject().getObjectParent() == null) {
                objectModel.setObjectId(obj.getIamMsObject().getObjectId());
                objectModel.setObjectName(obj.getIamMsObject().getObjectName());
                setObjectChild(obj, objectList ,objectModel.getObjects());
                listChid.add(objectModel);
            }
        }
        
        respone.setObjects(listChid);
        return respone ;
    }

    @Transactional
    public List<RoleMapObjectRespone> allRoleMapObject(long systemId) {
    	log.info("Service allRoleMapObject systemId: {}", systemId);
    	
        List<IamMsRole> listRole = this.roleRepository.findByIamMsSystem_SystemIdAndIsDeleted(systemId, AnswerFlag.N.toString());
        List<RoleMapObjectRespone> listRespone = new ArrayList<>();
        if (listRole.isEmpty()){
        	throw new DataNotFoundException(ErrorCode.ROLE_NOT_FOUND);
        }

        for (IamMsRole role : listRole){
            List<IamMsRoleObject> objectList = this.roleObjectRepository.findByIamMsRoleAndIsDeleted(role, AnswerFlag.N.toString());
            List<ObjectModel> listChid = new ArrayList<>();
            RoleMapObjectRespone respone = new RoleMapObjectRespone();

            respone.setSystemId(role.getIamMsSystem().getSystemId());
            respone.setRoleId(role.getRoleId());
            respone.setRoleName(role.getRoleName());

            if (!objectList.isEmpty()) {
                for (IamMsRoleObject obj : objectList) {
                    ObjectModel objectModel = new ObjectModel();
                    if (obj.getIamMsObject().getObjectParent() == null) {
                        objectModel.setObjectId(obj.getIamMsObject().getObjectId());
                        objectModel.setObjectName(obj.getIamMsObject().getObjectName());
                        setObjectChild(obj, objectList ,objectModel.getObjects());
                        listChid.add(objectModel);
                    }
                }
                
                respone.setObjects(listChid);
                listRespone.add(respone);
            }
        }
        
        return listRespone;
    }

    private void setObjectChild(IamMsRoleObject root, List<IamMsRoleObject> list, List<ObjectModel> listChild) {
    	ObjectModel childObjectModel = new ObjectModel();
        for (IamMsRoleObject obj : list) {
            if (obj.getIamMsObject().getObjectParent() == root.getIamMsObject()) {
                childObjectModel.setObjectId(obj.getIamMsObject().getObjectId());
                childObjectModel.setObjectName(obj.getIamMsObject().getObjectName());
                listChild.add(childObjectModel);
                setObjectChild(obj, list , childObjectModel.getObjects() );
            }
        }
    }
    
}
