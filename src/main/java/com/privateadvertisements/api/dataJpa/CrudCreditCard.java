package com.privateadvertisements.api.dataJpa;

import com.privateadvertisements.model.CreditCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface CrudCreditCard extends JpaRepository<CreditCard, Integer> {
}
