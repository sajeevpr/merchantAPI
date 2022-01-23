package com.dd.merchant.dao.impl;

import com.dd.merchant.constants.PhoneType;
import com.dd.merchant.exception.DataPersistantException;
import com.dd.merchant.exception.InvalidInputException;
import com.dd.merchant.exception.MerchantException;
import com.dd.merchant.model.PhoneNumber;
import com.dd.merchant.model.ui.UIRequest;
import com.dd.merchant.service.impl.ExtractPhoneNumberServiceImpl;
import com.dd.merchant.service.impl.PhoneNumberReqHandlerServiceImpl;
import com.dd.merchant.service.impl.PhoneNumberServiceImpl;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PhoneNumberReqHandlerServiceImplTest {

    @InjectMocks
    PhoneNumberReqHandlerServiceImpl phoneNumberReqHandlerService;

    @Mock
    PhoneNumberServiceImpl phoneNumberService;

    @Mock
    ExtractPhoneNumberServiceImpl extractPhoneNumberService;

    @Test
    void updatePhoneNumbersTest() throws MerchantException {
        final BigInteger phoneNum = BigInteger.ONE;
        final String phoneType1 = PhoneType.CELL.getValue();
        final String phoneType2 = PhoneType.HOME.getValue();
        List<PhoneNumber> phoneNumberList = Arrays.asList(
                PhoneNumber.builder().phoneNum(phoneNum).phoneType(phoneType1).occurrences(BigInteger.ONE).build(),
                PhoneNumber.builder().phoneNum(phoneNum).phoneType(phoneType2).occurrences(BigInteger.ONE).build()
        );
        UIRequest request = UIRequest.builder().rawPhoneNumbers("(Home)415-415-4155 (Cell) 415-123-4567").build();
        when(extractPhoneNumberService.extractAndValidatePhoneNumbers(request)).thenReturn(phoneNumberList);
        when(phoneNumberService.updateNumbers(phoneNumberList)).thenReturn(phoneNumberList);

        Assert.assertEquals(phoneNumberReqHandlerService.updatePhoneNumbers(request).getResults().size(), 2);
    }

    @Test
    void updatePhoneNumbersInvalidInputTest() throws MerchantException {
        UIRequest request = UIRequest.builder().rawPhoneNumbers("abc").build();
        when(extractPhoneNumberService.extractAndValidatePhoneNumbers(request)).thenThrow(InvalidInputException.class);
        Assert.assertThrows(InvalidInputException.class, () -> {
            phoneNumberReqHandlerService.updatePhoneNumbers(request);
        });
    }

    @Test
    void updatePhoneNumbersUpdatedFailedTest() throws MerchantException {
        final BigInteger phoneNum = BigInteger.ONE;
        final String phoneType1 = PhoneType.CELL.getValue();
        final String phoneType2 = PhoneType.HOME.getValue();
        List<PhoneNumber> phoneNumberList = Arrays.asList(
                PhoneNumber.builder().phoneNum(phoneNum).phoneType(phoneType1).occurrences(BigInteger.ONE).build(),
                PhoneNumber.builder().phoneNum(phoneNum).phoneType(phoneType2).occurrences(BigInteger.ONE).build()
        );
        UIRequest request = UIRequest.builder().rawPhoneNumbers("(Home)415-415-4155 (Cell) 415-123-4567").build();
        when(extractPhoneNumberService.extractAndValidatePhoneNumbers(request)).thenReturn(phoneNumberList);
        when(phoneNumberService.updateNumbers(phoneNumberList)).thenThrow(DataPersistantException.class);

        Assert.assertThrows(DataPersistantException.class, () -> {
            phoneNumberReqHandlerService.updatePhoneNumbers(request);
        });
    }
}
