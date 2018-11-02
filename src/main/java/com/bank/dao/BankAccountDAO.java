package com.bank.dao;

import com.bank.entity.BankAccount;
import com.bank.entity.Card;

public interface BankAccountDAO {

    void save(BankAccount bankAccount);

    long getId(BankAccount bankAccount);

    void disable(Card card);

    BankAccount getBankAccount(Card card);

}
