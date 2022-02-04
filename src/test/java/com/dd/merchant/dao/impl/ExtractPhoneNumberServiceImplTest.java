package com.dd.merchant.dao.impl;

import com.dd.merchant.constants.PhoneType;
import com.dd.merchant.exception.InvalidInputException;
import com.dd.merchant.model.PhoneNumber;
import com.dd.merchant.model.ui.UIRequest;
import com.dd.merchant.service.impl.ExtractPhoneNumberServiceImpl;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigInteger;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class ExtractPhoneNumberServiceImplTest {

    @InjectMocks
    private ExtractPhoneNumberServiceImpl extractPhoneNumberService;

    @Test
    void extractAndValidatePhoneNumbersTest() throws InvalidInputException {
        UIRequest request = UIRequest.builder().rawPhoneNumbers("(Home)415-415-4155 (Cell) 415-123-4567").build();
        List<PhoneNumber> phoneNumberList = extractPhoneNumberService.extractAndValidatePhoneNumbers(request);
        Assert.assertEquals(BigInteger.valueOf(4154154155L), phoneNumberList.get(0).getPhoneNum());
        Assert.assertEquals(PhoneType.HOME.getValue(), phoneNumberList.get(0).getPhoneType());
        Assert.assertEquals(BigInteger.valueOf(4151234567L), phoneNumberList.get(1).getPhoneNum());
        Assert.assertEquals(PhoneType.CELL.getValue(), phoneNumberList.get(1).getPhoneType());
    }

    @Test
    void extractAndValidatePhoneNumbersReverseTest() throws InvalidInputException {
        UIRequest request = UIRequest.builder().rawPhoneNumbers("(Cell) 415-123-4567 (Home)415-415-4155 ").build();
        List<PhoneNumber> phoneNumberList = extractPhoneNumberService.extractAndValidatePhoneNumbers(request);
        Assert.assertEquals(BigInteger.valueOf(4154154155L), phoneNumberList.get(0).getPhoneNum());
        Assert.assertEquals(PhoneType.HOME.getValue(), phoneNumberList.get(0).getPhoneType());
        Assert.assertEquals(BigInteger.valueOf(4151234567L), phoneNumberList.get(1).getPhoneNum());
        Assert.assertEquals(PhoneType.CELL.getValue(), phoneNumberList.get(1).getPhoneType());
    }

    @Test
    void extractAndValidateInvalidInputTest() throws InvalidInputException {
        UIRequest request = UIRequest.builder().rawPhoneNumbers("abcd").build();
        Assert.assertThrows(InvalidInputException.class, () -> {
                extractPhoneNumberService.extractAndValidatePhoneNumbers(request);
        });
    }

    @Test
    void extractAndValidateInvalidInput2Test() throws InvalidInputException {
        UIRequest request = UIRequest.builder().rawPhoneNumbers("(Cell1) 415-123-4567 (Home1)415-415-4155 ").build();
        Assert.assertThrows(InvalidInputException.class, () -> {
            extractPhoneNumberService.extractAndValidatePhoneNumbers(request);
        });
    }
}
