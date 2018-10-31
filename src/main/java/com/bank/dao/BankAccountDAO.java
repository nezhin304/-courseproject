package com.bank.dao;

import com.bank.entity.BankAccount;

public interface BankAccountDAO {

    long save(BankAccount bankAccount);

    long getId(BankAccount bankAccount);



}
