package com.dd.merchant.util;

import com.dd.merchant.model.ui.UIResponse;

import javax.naming.OperationNotSupportedException;
import java.math.BigInteger;

/**
 * Utility class
 */
public final class MerchantUtil {

    private MerchantUtil() throws OperationNotSupportedException{
        throw new OperationNotSupportedException("Cannot instantiate the class");
    }

    /**
     * To get ID based on phone number and type
     * @param phoneNumber phone number
     * @param phoneType phone type
     * @return id
     */
    public static String getID(BigInteger phoneNumber, String phoneType) {
        return phoneNumber+"-"+phoneType;
    }

    /**
     * To generate exception UIResponse with an input message
     * @param errorMsg error message
     * @return UIResponse object
     */
    public static UIResponse getErrorResponse(String errorMsg) {
        UIResponse response = new UIResponse();
        response.setError(errorMsg);
        return response;
    }

}
