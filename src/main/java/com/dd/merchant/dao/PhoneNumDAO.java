package com.dd.merchant.dao;

import com.dd.merchant.model.PhoneNumber;

import java.math.BigInteger;

/**
 * PhoneNumDAO interface
 */
public interface PhoneNumDAO {

    String ID = "id";

    /**
     * findByPhoneNumberAndType
     * @param phoneNumber phone number
     * @param phoneType phone type
     * @return phone number
     */
    PhoneNumber findByPhoneNumberAndType(BigInteger phoneNumber, String phoneType);

    /**
     * updatePhoneNumber
     * @param phoneNumber phone number
     * @param phoneType phone type
     * @return updated integer
     */
    int updatePhoneNumber(BigInteger phoneNumber, String phoneType);

    /**
     * savePhoneNumber
     * @param phoneNumber phoneNumber object
     */
    void savePhoneNumber(PhoneNumber phoneNumber);
}
