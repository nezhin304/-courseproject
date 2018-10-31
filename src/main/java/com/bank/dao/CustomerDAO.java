package com.bank.dao;

import com.bank.entity.Customer;

public interface CustomerDAO {

    long save(Customer customer);

    long getId(Customer customer);

    Customer getCustomer(long id);

}
