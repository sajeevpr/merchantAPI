package com.dd.merchant.controller;

import com.dd.merchant.exception.MerchantException;
import com.dd.merchant.model.ui.UIRequest;
import com.dd.merchant.model.ui.UIResponse;
import com.dd.merchant.service.PhoneNumberReqHandlerService;
import com.dd.merchant.util.MerchantUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for updating phone numbers
 */
@RestController
@Slf4j
public class PhoneController {

    @Autowired
    PhoneNumberReqHandlerService phoneNumberReqHandlerService;

    /**
     * POST method for updating phone numbers
     * @param request with raw numbers
     * @return response with list of numbers, types and their occurances
     */
    @RequestMapping(value = "/phone-numbers", method = RequestMethod.POST)
    public UIResponse updatePhoneNumbers(@RequestBody UIRequest request) {
        try {
            long startTime = System.currentTimeMillis();
            UIResponse responseList =  phoneNumberReqHandlerService.updatePhoneNumbers(request);
            long endTime = System.currentTimeMillis();
            log.info("Response Time : {} ms",(endTime-startTime));
            return responseList;
        } catch(MerchantException e) {
            return MerchantUtil.getErrorResponse(e.getErrDesc());
        } catch(Exception e) {
            return MerchantUtil.getErrorResponse("Error handling request");
        }
    }

}
