package com.dd.merchant.dao.impl;

import com.dd.merchant.constants.PhoneType;
import com.dd.merchant.model.PhoneNumber;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import java.math.BigInteger;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PhoneNumberDAOImplTest {

    @InjectMocks
    private PhoneNumberDAOImpl phoneNumberDAO;

    @Mock
    private EntityManager entityManager;

    @Mock
    private Query query;

    @Test
    void findByPhoneNumberAndTypeTest() {
        when(entityManager.createQuery(PhoneNumberDAOImpl.FIND_NUM_SQL)).thenReturn(query);
        PhoneNumber num = PhoneNumber.builder().build();
        when(query.getSingleResult()).thenReturn(num);
        Assert.assertEquals(num, phoneNumberDAO.findByPhoneNumberAndType(BigInteger.ONE, PhoneType.HOME.getValue()));
    }

    @Test
    void findByPhoneNumberAndTypeNotFoundTest() {
        when(entityManager.createQuery(PhoneNumberDAOImpl.FIND_NUM_SQL)).thenReturn(query);
        when(query.getSingleResult()).thenThrow(NoResultException.class);
        Assert.assertEquals(null, phoneNumberDAO.findByPhoneNumberAndType(BigInteger.ONE, PhoneType.HOME.getValue()));
    }

    @Test
    void updatePhoneNumberTest() {
        when(entityManager.createQuery(PhoneNumberDAOImpl.UPDATE_NUM_SQL)).thenReturn(query);
        when(query.executeUpdate()).thenReturn(1);
        Assert.assertEquals(1, phoneNumberDAO.updatePhoneNumber(BigInteger.ONE, PhoneType.CELL.getValue()));
    }

    @Test
    void updatePhoneNumberFailTest() {
        when(entityManager.createQuery(PhoneNumberDAOImpl.UPDATE_NUM_SQL)).thenReturn(query);
        when(query.executeUpdate()).thenReturn(0);
        Assert.assertEquals(0, phoneNumberDAO.updatePhoneNumber(BigInteger.ONE, PhoneType.CELL.getValue()));
    }

    @Test
    void savePhoneNumberTest() {
        Mockito.doNothing().when(entityManager).persist(any());
        PhoneNumber num = PhoneNumber.builder().phoneNum(BigInteger.ONE).phoneType(PhoneType.CELL.getValue()).build();
        phoneNumberDAO.savePhoneNumber(num);
    }

    @Test
    void savePhoneNumberErrorTest() {
        Mockito.doThrow(DataIntegrityViolationException.class).when(entityManager).persist(any());
        PhoneNumber num = PhoneNumber.builder().phoneNum(BigInteger.ONE).phoneType(PhoneType.CELL.getValue()).build();
        Assertions.assertThrows(DataIntegrityViolationException.class, () -> {
            phoneNumberDAO.savePhoneNumber(num);
        });
    }

}
