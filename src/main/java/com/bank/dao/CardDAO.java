package com.bank.dao;

import com.bank.entity.Card;
import com.bank.entity.Customer;

import java.util.Collection;

public interface CardDAO {

    void save(Card card);

    void deleteCard(Card card);

    Collection<Card> getCards(Customer customer);

    Collection<Card> getAll();

}
