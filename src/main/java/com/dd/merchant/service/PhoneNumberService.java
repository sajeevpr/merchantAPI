package com.dd.merchant.service;

import com.dd.merchant.exception.DataPersistantException;
import com.dd.merchant.model.PhoneNumber;

import java.math.BigInteger;
import java.util.List;

/**
 * Service interface for phone number persisting
 */
public interface PhoneNumberService {

    /**
     * updatePhoneNumber based on phoneNumber and type
     * @param phoneNumber phone number
     * @param phoneType phone type
     * @return phoneNumber object
     * @throws DataPersistantException for any persistance exceptions
     */
    PhoneNumber updatePhoneNumber(BigInteger phoneNumber, String phoneType) throws DataPersistantException;
    /**
     * Updates list of phone numbers
     * @param phoneNumberList phonenumber list
     * @return list of updated phone numbers
     * @throws DataPersistantException for any data persistant issue
     */
    List<PhoneNumber> updateNumbers(List<PhoneNumber> phoneNumberList) throws DataPersistantException;
}
