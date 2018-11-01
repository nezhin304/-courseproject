package com.bank.dao;

import com.bank.entity.Card;

import java.math.BigDecimal;

public interface TransactionDAO {

    boolean getMoney(Card card, BigDecimal money);

    void addMoney(Card card, BigDecimal money);
}
