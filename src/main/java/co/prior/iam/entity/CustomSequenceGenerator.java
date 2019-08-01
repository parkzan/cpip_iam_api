package co.prior.iam.entity;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.enhanced.SequenceStyleGenerator;


import java.io.Serializable;
@Slf4j
public class CustomSequenceGenerator extends SequenceStyleGenerator {

    @Override
    public Serializable generate(SharedSessionContractImplementor session,Object object) throws HibernateException {
        IamAuditTrail iamAuditTrail = (IamAuditTrail) object;


        if( iamAuditTrail.getAuditId() != null && iamAuditTrail.getAuditId() > 0){

            return iamAuditTrail.getAuditId();
        }


     return super.generate(session,object);
    }

}
