package com.dd.merchant.exception;

import lombok.Getter;

/**
 * MerchantException which is the parent of all exceptions in this project
 */
@Getter
public class MerchantException extends Exception{

    /**
     * errDesc
     */
    private String errDesc;

    /**
     * Constructor
     * @param errDesc error description
     */
    public MerchantException(String errDesc) {
        this.errDesc = errDesc;
    }
}
