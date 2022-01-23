package com.dd.merchant.util;

import com.dd.merchant.constants.PhoneType;
import org.junit.jupiter.api.Test;
import org.junit.Assert;

import java.math.BigInteger;

public class MerchantUtilTest {

    @Test
    public void getIDTest() {
        Assert.assertEquals("1-home",MerchantUtil.getID(BigInteger.ONE, PhoneType.HOME.getValue()));
    }

    @Test
    public void getErrorResponseTest() {
        Assert.assertEquals("msg",MerchantUtil.getErrorResponse("msg").getError());
    }
}
