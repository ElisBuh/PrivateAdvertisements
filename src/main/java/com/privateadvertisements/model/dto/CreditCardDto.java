package com.privateadvertisements.model.dto;

import com.privateadvertisements.model.TypeCreditCard;
import com.sun.istack.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.CreditCardNumber;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class CreditCardDto {

    @NotNull
    private TypeCreditCard typeCreditCard;
    @CreditCardNumber
    private BigDecimal number;
}
