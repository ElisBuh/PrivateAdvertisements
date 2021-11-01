package com.privateadvertisements.datatest;

import com.privateadvertisements.model.CreditCard;
import com.privateadvertisements.model.TypeCreditCard;

import java.math.BigDecimal;

public class CreditCartDataTest {

    public static final CreditCard CREDIT_CARD_1 = new CreditCard(100008, TypeCreditCard.VISA, new BigDecimal(4000111122223333L));
    public static final CreditCard CREDIT_CARD_2 = new CreditCard(100009, TypeCreditCard.MASTERCARD, new BigDecimal(5000444455556666L));
    public static final CreditCard NEW_CREDIT_CARD = new CreditCard(100009, TypeCreditCard.MASTERCARD, new BigDecimal(5000444455556666L));
}
