package com.dd.merchant.controller;

import com.dd.merchant.constants.PhoneType;
import com.dd.merchant.exception.MerchantException;
import com.dd.merchant.model.ui.Response;
import com.dd.merchant.model.ui.UIRequest;
import com.dd.merchant.model.ui.UIResponse;
import com.dd.merchant.service.impl.PhoneNumberReqHandlerServiceImpl;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PhoneControllerTest {

    @InjectMocks
    PhoneController controller;

    @Mock
    PhoneNumberReqHandlerServiceImpl phoneNumberReqHandlerService;

    @Test
    void updatePhoneNumbersTest() throws MerchantException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        final String phoneNum = "1";
        final String phoneType1 = PhoneType.CELL.getValue();
        final String phoneType2 = PhoneType.HOME.getValue();
        List<Response> phoneNumberList = Arrays.asList(
                Response.builder().phoneNumber(phoneNum).phoneType(phoneType1).occurrences(BigInteger.ONE).build(),
                Response.builder().phoneNumber(phoneNum).phoneType(phoneType2).occurrences(BigInteger.ONE).build()
        );
        UIRequest req = UIRequest.builder().rawPhoneNumbers("(Home)415-415-4155 (Cell) 415-123-4567").build();
        UIResponse response = UIResponse.builder().results(phoneNumberList).build();

        when(phoneNumberReqHandlerService.updatePhoneNumbers(req)).thenReturn(response);

        Assert.assertEquals(2, controller.updatePhoneNumbers(req).getResults().size());

    }

    @Test
    void updatePhoneNumbersExceptionTest() throws MerchantException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        UIRequest req = UIRequest.builder().rawPhoneNumbers("(Home)415-415-4155 (Cell) 415-123-4567").build();
        when(phoneNumberReqHandlerService.updatePhoneNumbers(req)).thenThrow(MerchantException.class);

        UIResponse response = controller.updatePhoneNumbers(req);
        Assert.assertNotNull(response);
    }
}
