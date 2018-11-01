package com.bank.dao;

import com.bank.entity.BankAccount;
import com.bank.entity.Card;

import java.math.BigDecimal;

public interface Transaction {

    BankAccount getBalance(Card card);

    void getMoney(Card card, BigDecimal money);

    void addMoney(Card card, BigDecimal money);
}
