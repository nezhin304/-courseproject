package com.bank.dao;

import com.bank.entity.Card;
import com.bank.entity.Customer;

import java.util.Collection;

public interface CardDAO {

    long save(Card card);

    long getCardId(Card card);

    Collection<Card> getCards(Customer customer);

    void deleteCard(Card card);

}
