package com.dd.merchant.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigInteger;

/**
 * PhoneNumber entity class
 */
@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PhoneNumber {

    /**
     * id of the entity
     */
    @Id
    private String id;

    /**
     * PhoneNumber
     */
    private BigInteger phoneNum;

    /**
     * phoneType
     */
    private String phoneType;

    /**
     * occurences
     */
    private BigInteger occurrences;
}
