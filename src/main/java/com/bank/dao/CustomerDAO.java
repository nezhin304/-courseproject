package com.bank.dao;

import com.bank.entity.Customer;
import javafx.print.Collation;

import java.util.Collection;

public interface CustomerDAO {

    long save(Customer customer);

    long getId(Customer customer);

    Customer getCustomer(String phone);

    void deleteAll();

    boolean deleteCustomer(Customer customer);

    Collection<Customer> getAll();

}
