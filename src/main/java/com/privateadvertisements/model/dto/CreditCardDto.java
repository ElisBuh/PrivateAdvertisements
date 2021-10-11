package com.privateadvertisements.model.dto;

import com.privateadvertisements.model.TypeCreditCard;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class CreditCardDto {

    private TypeCreditCard typeCreditCard;
    private BigDecimal number;
}
