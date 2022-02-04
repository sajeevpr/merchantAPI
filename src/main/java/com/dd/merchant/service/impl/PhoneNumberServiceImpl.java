package com.dd.merchant.service.impl;

import com.dd.merchant.dao.PhoneNumDAO;
import com.dd.merchant.exception.DataPersistantException;
import com.dd.merchant.model.PhoneNumber;
import com.dd.merchant.service.PhoneNumberService;
import com.dd.merchant.util.MerchantUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * Service class for phone number persisting
 */
@Service
@Slf4j
public class PhoneNumberServiceImpl implements PhoneNumberService {

    @Autowired
    private PhoneNumDAO phoneNumDAO;

    /**
     * updatePhoneNumber based on phoneNumber and type.
     * @param phoneNumber phone number
     * @param phoneType phone type
     * @return phoneNumber object
     * @throws DataPersistantException for any persistance exceptions
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = DataPersistantException.class)
    public PhoneNumber updatePhoneNumber(BigInteger phoneNumber, String phoneType) throws DataPersistantException{
        PhoneNumber pNumber = null;
        try {
            pNumber = phoneNumDAO.findByPhoneNumberAndType(phoneNumber, phoneType);
            if (pNumber == null) {
                //insert phoneNumber, handle exception with an update operation if already inserted by another thread
                PhoneNumber pEntity = PhoneNumber.builder()
                        .id(MerchantUtil.getID(phoneNumber, phoneType))
                        .phoneNum(phoneNumber)
                        .phoneType(phoneType)
                        .occurrences(BigInteger.ONE).build();;
                try {
                    phoneNumDAO.savePhoneNumber(pEntity);
                    pNumber = pEntity;
                } catch (DataIntegrityViolationException e) {
                    int updated = phoneNumDAO.updatePhoneNumber(phoneNumber, phoneType);
                    if(updated == 1) {
                        pEntity.setOccurrences(pEntity.getOccurrences().add(BigInteger.ONE));
                    }
                    pNumber = pEntity;
                }
            } else {
                //invoke update operation
                int updated = phoneNumDAO.updatePhoneNumber(phoneNumber, phoneType);
                if(updated == 1) {
                    pNumber.setOccurrences(pNumber.getOccurrences().add(BigInteger.ONE));
                }
            }
        } catch(Exception e) {
            log.error("Exception : {}", e);
            throw new DataPersistantException("Error While persisting phone numbers into database");
        }

        return pNumber;
    }

    /**
     * Updates list of phone numbers. This method executes in a JPA transaction. if any number persisting fail, both will be rollbacked.
     * @param phoneNumberList phonenumber list
     * @return list of updated phone numbers
     * @throws DataPersistantException for any data persistant issue
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = DataPersistantException.class)
    public List<PhoneNumber> updateNumbers(List<PhoneNumber> phoneNumberList) throws DataPersistantException{
        List<PhoneNumber> outputList = new ArrayList<>();
        for(PhoneNumber num: phoneNumberList) {
            outputList.add(updatePhoneNumber(num.getPhoneNum(),num.getPhoneType()));
        }
        return outputList;
    }

}
