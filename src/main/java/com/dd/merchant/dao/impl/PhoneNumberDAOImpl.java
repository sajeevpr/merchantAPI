package com.dd.merchant.dao.impl;

import com.dd.merchant.dao.PhoneNumDAO;
import com.dd.merchant.model.PhoneNumber;
import com.dd.merchant.util.MerchantUtil;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.math.BigInteger;

/**
 * DAO class for PhoneNumber
 */
@Component
public class PhoneNumberDAOImpl implements PhoneNumDAO {

    @Autowired
    private EntityManager entityManager;

    public static final String FIND_NUM_SQL = "select p from PhoneNumber p where p.id=:id";

    public static final String UPDATE_NUM_SQL = "update PhoneNumber p set occurrences=occurrences+1 where p.id=:id";

    /**
     * findByPhoneNumberAndType
     * @param phoneNumber phone number
     * @param phoneType phone type
     * @return phoneNumber
     */
    @Override
    public PhoneNumber findByPhoneNumberAndType(BigInteger phoneNumber, String phoneType) {
        Query query = entityManager.createQuery(FIND_NUM_SQL);
        query.setParameter(ID, MerchantUtil.getID(phoneNumber,phoneType));
        try {
            return (PhoneNumber) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    /**
     * updatePhoneNumber by incrementing occurance
     * @param phoneNumber phone number
     * @param phoneType phone type
     * @return result integer
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public int updatePhoneNumber(BigInteger phoneNumber, String phoneType) {
        Query query = entityManager.createQuery(UPDATE_NUM_SQL);
        query.setParameter(ID,MerchantUtil.getID(phoneNumber, phoneType));
        int result = query.executeUpdate();
        return result;
    }

    /**
     * savePhoneNumber to persist new phoneNumber and its type
     * @param phoneNumber phone number
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void savePhoneNumber(PhoneNumber phoneNumber) {
        entityManager.persist(phoneNumber);
    }
}
