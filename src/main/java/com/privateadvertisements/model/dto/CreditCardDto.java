package com.privateadvertisements.model.dto;

import com.privateadvertisements.model.TypeCreditCard;
import com.privateadvertisements.model.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class CreditCardDto {

    private TypeCreditCard typeCreditCard;
    private BigDecimal number;
}
