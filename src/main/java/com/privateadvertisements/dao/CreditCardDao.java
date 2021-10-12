package com.privateadvertisements.dao;

import com.privateadvertisements.api.dao.IAbstractDao;
import com.privateadvertisements.api.dataJpa.CrudCreditCard;
import com.privateadvertisements.model.CreditCard;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CreditCardDao implements IAbstractDao<CreditCard> {

    private final CrudCreditCard crudCreditCard;

    public CreditCardDao(CrudCreditCard crudCreditCard) {
        this.crudCreditCard = crudCreditCard;
    }

    @Override
    public CreditCard save(CreditCard creditCard) {
        return crudCreditCard.save(creditCard);
    }

    @Override
    public void delete(Integer id) {
        crudCreditCard.deleteById(id);
    }

    @Override
    public CreditCard get(Integer id) {
        return crudCreditCard.getById(id);
    }

    @Override
    public List<CreditCard> getAll() {
        return crudCreditCard.findAll();
    }
}
