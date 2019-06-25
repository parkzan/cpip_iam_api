package co.prior.iam.module.role_object.service;


import co.prior.iam.entity.IamMsRole;
import co.prior.iam.entity.IamMsRoleObject;
import co.prior.iam.entity.IamMsSystem;
import co.prior.iam.module.role_object.model.respone.ObjectModel;
import co.prior.iam.module.role_object.model.respone.RoleMapObjectRespone;
import co.prior.iam.repository.ObjectRepository;
import co.prior.iam.repository.RoleObjectRepository;
import co.prior.iam.repository.RoleRepository;
import co.prior.iam.repository.SystemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class GetRoleObjectService {

    RoleObjectRepository roleObjectRepository;
    RoleRepository roleRepository;
    ObjectRepository objectRepository;
    SystemRepository systemRepository;

    GetRoleObjectService(RoleObjectRepository roleObjectRepository,RoleRepository roleRepository , ObjectRepository objectRepository , SystemRepository systemRepository){

        this.roleObjectRepository = roleObjectRepository ;
        this.roleRepository = roleRepository;
        this.objectRepository = objectRepository;
        this.systemRepository  = systemRepository;
    }

    @Transactional
    public RoleMapObjectRespone getRoleObject(Long systemId ,Long roleId) throws Exception{

        IamMsSystem system = systemRepository.findBySystemIdAndIsDeleted(systemId,"N")
                .orElseThrow(() -> new Exception("data not found"));

        IamMsRole iamMsRole = roleRepository.findByIamMsSystemAndRoleIdAndIsDeleted(system,roleId,"N")
                .orElseThrow(() -> new Exception("data not found"));



        List<IamMsRoleObject> objectList = roleObjectRepository.findByIamMsRoleAndIsDeleted(iamMsRole,"N");
        List<ObjectModel> listChid = new ArrayList<>();
        RoleMapObjectRespone respone = new RoleMapObjectRespone();

        respone.setSystemId(iamMsRole.getIamMsSystem().getSystemId());
        respone.setRoleId(iamMsRole.getRoleId());
        respone.setRoleName(iamMsRole.getRoleName());

        if (!objectList.isEmpty()) {
            for (IamMsRoleObject obj : objectList) {

                ObjectModel objectModel = new ObjectModel();

                if (obj.getIamMsObject().getObjectParentId() == null) {

                    objectModel.setObjectId(obj.getIamMsObject().getObjectId());
                    objectModel.setObjectName(obj.getIamMsObject().getObjectName());
                    setObjectChild(obj, objectList ,objectModel.getObjects());
                    listChid.add(objectModel);
                }

            }
            respone.setObjects(listChid);

            return  respone ;
        }
        else throw new Exception("data not found");

    }

    @Transactional
    public List<RoleMapObjectRespone> allRoleMapObject(long systemId) throws Exception{
        IamMsSystem system = systemRepository.findBySystemIdAndIsDeleted(systemId,"N")
                .orElseThrow(() -> new Exception("data not found"));

        List<IamMsRole> listRole = roleRepository.findByIamMsSystemAndIsDeleted(system,"N");
        List<RoleMapObjectRespone> listRespone = new ArrayList<>();

        if (!listRole.isEmpty()){
            for (IamMsRole role : listRole){
                List<IamMsRoleObject> objectList = roleObjectRepository.findByIamMsRoleAndIsDeleted(role,"N");
                List<ObjectModel> listChid = new ArrayList<>();
                RoleMapObjectRespone respone = new RoleMapObjectRespone();

                respone.setSystemId(role.getIamMsSystem().getSystemId());
                respone.setRoleId(role.getRoleId());
                respone.setRoleName(role.getRoleName());

                if (!objectList.isEmpty()) {
                    for (IamMsRoleObject obj : objectList) {

                        ObjectModel objectModel = new ObjectModel();

                        if (obj.getIamMsObject().getObjectParentId() == null) {

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
        else throw  new Exception("data not found");

    }

    private void setObjectChild(IamMsRoleObject root ,List<IamMsRoleObject> list  ,List<ObjectModel> listChild ) throws Exception{

            ObjectModel childObjectModel = new ObjectModel();

            for (IamMsRoleObject obj : list) {
                if (obj.getIamMsObject().getObjectParentId() == root.getIamMsObject().getObjectId()) {
                    childObjectModel.setObjectId(obj.getIamMsObject().getObjectId());
                    childObjectModel.setObjectName(obj.getIamMsObject().getObjectName());
                    listChild.add(childObjectModel);
                   // list.remove(root);


                    setObjectChild(obj, list , childObjectModel.getObjects() );

                }
            }




    }
}
