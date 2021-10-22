package com.privateadvertisements.api.repository;

import com.privateadvertisements.model.CreditCard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;

public interface CrudCreditCard extends JpaRepository<CreditCard, Integer> {

    CreditCard getByNumber(BigDecimal number);
}
