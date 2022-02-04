package com.dd.merchant.service.impl;

import com.dd.merchant.constants.PhoneType;
import com.dd.merchant.dao.impl.PhoneNumberDAOImpl;
import com.dd.merchant.exception.DataPersistantException;
import com.dd.merchant.model.PhoneNumber;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PhoneNumberServiceImplTest {

    @Mock
    PhoneNumberDAOImpl phoneNumDAO;

    @InjectMocks
    PhoneNumberServiceImpl phoneNumberService;

    @Test
    void updatePhoneNumberTest() throws DataPersistantException {
        final BigInteger phoneNum = BigInteger.ONE;
        final String phoneType = PhoneType.CELL.getValue();
        final PhoneNumber phoneNumber = PhoneNumber.builder().phoneNum(phoneNum).phoneType(phoneType).occurrences(BigInteger.ONE).build();
        when(phoneNumDAO.findByPhoneNumberAndType(phoneNum,phoneType)).thenReturn(phoneNumber);
        when(phoneNumDAO.updatePhoneNumber(phoneNum,phoneType)).thenReturn(1);

        Assert.assertEquals(BigInteger.valueOf(2L), phoneNumberService.updatePhoneNumber(phoneNum, phoneType).getOccurrences());
    }

    @Test
    void updatePhoneNumberInsertTest() throws DataPersistantException {
        final BigInteger phoneNum = BigInteger.ONE;
        final String phoneType = PhoneType.CELL.getValue();
        final PhoneNumber phoneNumber = PhoneNumber.builder().phoneNum(phoneNum).phoneType(phoneType).occurrences(BigInteger.ONE).build();
        when(phoneNumDAO.findByPhoneNumberAndType(phoneNum,phoneType)).thenReturn(null);
        doNothing().when(phoneNumDAO).savePhoneNumber(any());

        Assert.assertEquals(BigInteger.valueOf(1L), phoneNumberService.updatePhoneNumber(phoneNum, phoneType).getOccurrences());
    }

    @Test
    void updatePhoneNumberAlreadyInsertedTest() throws DataPersistantException {
        final BigInteger phoneNum = BigInteger.ONE;
        final String phoneType = PhoneType.CELL.getValue();
        when(phoneNumDAO.findByPhoneNumberAndType(phoneNum,phoneType)).thenReturn(null);
        doThrow(DataIntegrityViolationException.class).when(phoneNumDAO).savePhoneNumber(any());
        when(phoneNumDAO.updatePhoneNumber(phoneNum,phoneType)).thenReturn(1);

        Assert.assertEquals(BigInteger.valueOf(2L), phoneNumberService.updatePhoneNumber(phoneNum, phoneType).getOccurrences());
    }

    @Test
    void updateMultipleNumbersTest() throws DataPersistantException {
        final BigInteger phoneNum = BigInteger.ONE;
        final String phoneType = PhoneType.CELL.getValue();
        List<PhoneNumber> phoneNumberList = Arrays.asList(
                PhoneNumber.builder().phoneNum(phoneNum).phoneType(phoneType).occurrences(BigInteger.ONE).build(),
                PhoneNumber.builder().phoneNum(phoneNum).phoneType(phoneType).occurrences(BigInteger.ONE).build()
        );
        when(phoneNumDAO.findByPhoneNumberAndType(phoneNum,phoneType)).thenReturn(phoneNumberList.get(0));
        when(phoneNumDAO.updatePhoneNumber(phoneNum,phoneType)).thenReturn(1);

        Assert.assertEquals(2, phoneNumberService.updateNumbers(phoneNumberList).size());

    }
}
