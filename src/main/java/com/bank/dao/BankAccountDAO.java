package com.bank.dao;

import com.bank.entity.BankAccount;
import com.bank.entity.Card;

public interface BankAccountDAO {

    long save(BankAccount bankAccount);

    long getId(BankAccount bankAccount);

    BankAccount getBalance(Card card);

    void disable(Card card);

    boolean deleteAccount(BankAccount bankAccount);

}
