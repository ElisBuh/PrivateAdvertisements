package com.privateadvertisements.api.dataJpa;

import com.privateadvertisements.model.CreditCard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CrudCreditCard extends JpaRepository<CreditCard, Integer> {
}
