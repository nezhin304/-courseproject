package com.bank.dao;

import com.bank.entity.Card;
import com.bank.entity.Customer;
import com.bank.pool.Pool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

public class CustomerDAOImpl extends AbstractDAO implements CustomerDAO {

    private Logger log = LoggerFactory.getLogger(CustomerDAOImpl.class);

    private static class Holder {
        static final CustomerDAO CUSTOMER_DAO = new CustomerDAOImpl();
    }

    public static CustomerDAO getInstance() {
        return Holder.CUSTOMER_DAO;
    }

    @Override
    public long save(Customer customer) {

        PreparedStatement statement = null;
        ResultSet resultSet = null;
        long id = 0;

        try (Connection connection = Pool.getConnection()) {
            statement = connection
                    .prepareStatement("INSERT INTO customers (name, surname, phone) VALUES (?,?,?)", Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, customer.getName());
            statement.setString(2, customer.getSurname());
            statement.setString(3, customer.getPhone());
            statement.executeUpdate();
            connection.commit();
            resultSet = statement.getGeneratedKeys();
            resultSet.next();
            id = resultSet.getLong(1);

        } catch (SQLException e) {

            if (e.getMessage().contains("duplicate key value")) {

                id = getId(customer);

            } else {
                log.error(e.getMessage(), e);
            }
        } finally {
            Helper.closeStatementResultSet(statement, resultSet);
        }


        return id;

    }

    @Override
    public long getId(Customer customer) {

        PreparedStatement statement = null;
        ResultSet resultSet = null;
        long id = 0;

        try (Connection connection = Pool.getConnection()) {
            statement = connection
                    .prepareStatement("SELECT id FROM customers WHERE phone = ?");
            statement.setString(1, customer.getPhone());
            resultSet = statement.executeQuery();
            connection.commit();
            resultSet.next();
            id = resultSet.getLong(1);

        } catch (SQLException e) {
            log.error(e.getMessage(), e);
        } finally {
            Helper.closeStatementResultSet(statement, resultSet);
        }

        return id;
    }

    @Override
    public Customer getCustomer(String phone) {
        return null;
    }


    @Override
    public Collection<Customer> getAll() {

        Collection<Card> cards = CardDAOImpl.getInstance().getAll();
        Collection<Customer> customers = new ArrayList<>();

        for (Card card : cards) {

            Customer customer = card.getCustomer();
            customers.add(customer);
        }


        return customers;
    }

    @Override
    public boolean deleteCustomer(Customer customer) {

        PreparedStatement statement = null;
        boolean result = false;
        long id = 0;

        try (Connection connection = Pool.getConnection()) {
            statement = connection
                    .prepareStatement("DELETE FROM customers WHERE phone = ?");
            statement.setString(1, customer.getPhone());
            result = statement.execute();
            connection.commit();


        } catch (SQLException e) {
            log.error(e.getMessage(), e);
        } finally {
            Helper.closeStatementResultSet(statement, null);
        }

        return result;
    }

    @Override
    public void deleteAll() {

        Collection<Customer> customers = getAll();
        for (Customer customer : customers) {

            Collection<Card> cards = CardDAOImpl.getInstance().getCards(customer);

            for (Card card : cards ) {

                CardDAOImpl.getInstance().deleteCard(card);
            }

            for (Card card : cards) {

                BankAccountDAOImpl.getInstance().deleteAccount(card.getBankAccount());
            }

            CustomerDAOImpl.getInstance().deleteCustomer(customer);

        }
    }
}
