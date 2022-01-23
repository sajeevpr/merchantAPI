package com.dd.merchant.exception;

/**
 * DataPersistantException object to hold any persistance error
 */
public class DataPersistantException extends MerchantException{

    /**
     * Constructor for DataPersistantException
     * @param errDesc error description
     */
    public DataPersistantException(String errDesc) {
        super(errDesc);
    }
}
