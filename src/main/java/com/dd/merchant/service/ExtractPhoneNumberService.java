package com.dd.merchant.service;

import com.dd.merchant.exception.InvalidInputException;
import com.dd.merchant.model.PhoneNumber;
import com.dd.merchant.model.ui.UIRequest;

import java.util.List;

/**
 * To extract the input payload
 */
public interface ExtractPhoneNumberService {
    /**
     * Extracting and validating phone numbers
     * @param request request from UI
     * @return list of phone numbers
     * @throws InvalidInputException for invalid inputs
     */
    List<PhoneNumber> extractAndValidatePhoneNumbers(UIRequest request) throws InvalidInputException;
}
