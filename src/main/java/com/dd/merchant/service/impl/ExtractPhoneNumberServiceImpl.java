package com.dd.merchant.service.impl;

import com.dd.merchant.constants.PhoneType;
import com.dd.merchant.exception.InvalidInputException;
import com.dd.merchant.model.PhoneNumber;
import com.dd.merchant.model.ui.UIRequest;
import com.dd.merchant.service.ExtractPhoneNumberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

/**
 * To extract and validate the input
 */
@Service
@Slf4j
public class ExtractPhoneNumberServiceImpl implements ExtractPhoneNumberService {

    public static final String HOME = "(Home)";
    public static final String CELL = "(Cell)";
    public static final int SIX = 6;
    public static final String HYPHEN = "-";
    public static final String EMPTY = "";

    /**
     * Extracting and validating phone numbers
     * @param request request from UI
     * @return list of phone numbers
     * @throws InvalidInputException for invalid inputs
     */
    @Override
    public List<PhoneNumber> extractAndValidatePhoneNumbers(UIRequest request) throws InvalidInputException {
        final String rawNumbers = request.getRawPhoneNumbers();
        final int homeIndex = rawNumbers.indexOf(HOME);
        final int cellIndex = rawNumbers.indexOf(CELL);
        BigInteger homeNum = null;
        BigInteger cellNum = null;
        if (homeIndex == -1 || cellIndex == -1) {
            throw new InvalidInputException("Either home phone or cell phone details not found in the input");
        } else if (homeIndex < cellIndex) {
            homeNum = parseFirstNum(rawNumbers, homeIndex, cellIndex);
            cellNum = parseSecondNum(rawNumbers, cellIndex);
        } else {
            cellNum = parseFirstNum(rawNumbers, cellIndex, homeIndex);
            homeNum = parseSecondNum(rawNumbers, homeIndex);
        }

        return Arrays.asList(
                PhoneNumber.builder().phoneNum(homeNum).phoneType(PhoneType.HOME.getValue()).build(),
                PhoneNumber.builder().phoneNum(cellNum).phoneType(PhoneType.CELL.getValue()).build()
        );
    }

    /**
     * To parse the first number
     * @param rawNumbers raw number string
     * @param index1 first index
     * @param index2 second index
     * @return number
     * @throws InvalidInputException for invalid inputs
     */
    private BigInteger parseFirstNum(final String rawNumbers, int index1, int index2) throws InvalidInputException {
        try {
            return new BigInteger(rawNumbers.substring(index1 + SIX, index2).replaceAll(HYPHEN, EMPTY).trim());
        } catch(Exception e) {
            log.error("Exception : {}", e);
            throw new InvalidInputException("Error while parsing the second number");
        }
    }

    /**
     * To parse the second number
     * @param rawNumbers raw number string
     * @param index last index
     * @return phone number
     * @throws InvalidInputException for invalid input
     */
    private BigInteger parseSecondNum(final String rawNumbers, int index) throws InvalidInputException {
        try {
            return new BigInteger(rawNumbers.substring(index + SIX).replaceAll(HYPHEN, EMPTY).trim());
        } catch(Exception e) {
            log.error("Exception : {}", e);
            throw new InvalidInputException("Error while parsing the second number");
        }
    }
}
