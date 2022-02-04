package com.dd.merchant.service;

import com.dd.merchant.exception.MerchantException;
import com.dd.merchant.model.ui.UIRequest;
import com.dd.merchant.model.ui.UIResponse;

/**
 * Handler service for parsing and persisting phone numbers
 */
public interface PhoneNumberReqHandlerService {
    /**
     * Receives UI request and updates the database
     * @param request UI request
     * @return UI Response
     * @throws MerchantException incase of issues in parsing or persisting
     */
    UIResponse updatePhoneNumbers(UIRequest request) throws MerchantException;
}
