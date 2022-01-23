package com.dd.merchant.exception;

/**
 * To capture any invalid input
 */
public class InvalidInputException extends MerchantException{

    /**
     * Constructor
     * @param errDesc error description
     */
    public InvalidInputException(String errDesc) {
        super(errDesc);
    }
}
