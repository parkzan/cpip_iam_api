package co.prior.iam.repository;


import co.prior.iam.entity.VParameterData;
import co.prior.iam.module.vparamdata.model.respone.VparamDataReponse;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.internal.SessionImpl;
import org.hibernate.transform.Transformers;
import org.hibernate.type.LongType;
import org.hibernate.type.StringType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.jdbc.object.SqlQuery;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.xml.transform.Transformer;
import java.sql.Connection;
import java.util.List;

@Repository
public class VParameterDataRepository {

   @PersistenceContext
   private EntityManager em;


   public List<VparamDataReponse> getParamMenu() {
       String sql = "SELECT OBJECT_PARENT_ID objectParentId, OBJECT_ID objectId, OBJECT_CODE objectCode,OBJECT_NAME objectName, OBJECT_URL objectUrl\n" +
               "FROM IAM_MS_OBJECT IMO\n" +
               "WHERE IMO.SYSTEM_ID = (SELECT IMS.SYSTEM_ID\n" +
               "FROM IAM_MS_SYSTEM IMS\n" +
               "WHERE IMS.IS_DELETED = 'N'\n" +
               "AND IMS.SYSTEM_CODE = 'PPI')\n" +
               "AND IMO.OBJECT_TYPE = (SELECT PARAM_INFO_ID\n" +
               "FROM V_PARAMETER_DATA\n" +
               "WHERE PARAM_GROUP_IS_DELETED = 'N'\n" +
               "AND PARAM_INFO_IS_DELETED = 'N'\n" +
               "AND PARAM_GROUP = 'OBJECT_TYPE'\n" +
               "AND PARAM_CODE = 'MENU')\n" +
               "START WITH OBJECT_PARENT_ID IS NULL\n" +
               "CONNECT BY PRIOR OBJECT_ID = OBJECT_PARENT_ID\n" +
               "ORDER BY OBJECT_PARENT_ID, OBJECT_ID";

       Session session = this.em.unwrap(Session.class);
       SQLQuery query = session.createSQLQuery(sql);
       query.addScalar("objectParentId" , LongType.INSTANCE);
       query.addScalar("objectId" , LongType.INSTANCE);
       query.addScalar("objectCode" , StringType.INSTANCE);
       query.addScalar("objectName" , StringType.INSTANCE);
       query.addScalar("objectUrl" , StringType.INSTANCE);

       query.setResultTransformer(Transformers.aliasToBean(VparamDataReponse.class));
       List<VparamDataReponse> list = query.list();

       return list;

   }

}
