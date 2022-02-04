package com.dd.merchant.service.impl;

import com.dd.merchant.exception.MerchantException;
import com.dd.merchant.model.PhoneNumber;
import com.dd.merchant.model.ui.Response;
import com.dd.merchant.model.ui.UIRequest;
import com.dd.merchant.model.ui.UIResponse;
import com.dd.merchant.service.ExtractPhoneNumberService;
import com.dd.merchant.service.PhoneNumberReqHandlerService;
import com.dd.merchant.service.PhoneNumberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Request handler for UI Request. This class orchestrates calls between parsing and persisting numbers
 */
@Service
public class PhoneNumberReqHandlerServiceImpl implements PhoneNumberReqHandlerService {

    @Autowired
    PhoneNumberService phoneNumberService;

    @Autowired
    ExtractPhoneNumberService extractPhoneNumberService;

    /**
     * Receives UI request and updates the database
     * @param request UI request
     * @return UI Response
     * @throws MerchantException incase of issues in parsing or persisting
     */
    @Override
    public UIResponse updatePhoneNumbers(UIRequest request) throws MerchantException {
        List<Response> responseList = new ArrayList<>();

        //parseAndExtract UIRequest
        List<PhoneNumber> phoneNumbers = extractPhoneNumberService.extractAndValidatePhoneNumbers(request);

        //update phoneNumbers
        List<PhoneNumber> updatedNumbers = phoneNumberService.updateNumbers(phoneNumbers);

        //Create response
        for(PhoneNumber num: updatedNumbers) {
            responseList.add(Response.builder()
                    .id(num.getId())
                    .phoneNumber(String.valueOf(num.getPhoneNum()))
                    .phoneType(num.getPhoneType())
                    .occurrences(num.getOccurrences())
                    .build()
            );
        }

        return UIResponse.builder().results(responseList).build();
    }
}
