package com.bank.dao;

import com.bank.entity.BankAccount;
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

                id = CustomerDAOImpl.getInstance().getId(customer);

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
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        final String EXECUTE_QUERY = "SELECT cust.name, cust.surname, cust.phone, card.number, ba.account, ba.deposit, ba.credit, ba.state".concat(
                " FROM customers cust JOIN cards card ON cust.id = card.customer_id JOIN  bank_accounts ba ON card.id = ba.card_id");
        Customer customer = new Customer();

        try (Connection connection = Pool.getConnection()) {

            statement = connection.prepareStatement(EXECUTE_QUERY);
            resultSet = statement.executeQuery();
            connection.commit();
            resultSet.next();

            customer.setName(resultSet.getString(1));
            customer.setSurname(resultSet.getString(2));
            customer.setPhone(resultSet.getString(3));

            Card card = new Card();
            card.setNumber(resultSet.getString(4));

            BankAccount bankAccount = new BankAccount();
            bankAccount.setAccount(resultSet.getString(5));
            bankAccount.setDeposit(resultSet.getBigDecimal(6));
            bankAccount.setCredit(resultSet.getBigDecimal(7));
            bankAccount.setState(resultSet.getBoolean(8));

            bankAccount.setCard(card);
            card.setBankAccount(bankAccount);
            card.setCustomer(customer);
            customer.setCard(card);

        } catch (SQLException e) {
            log.error(e.getMessage(), e);
        } finally {
            Helper.closeStatementResultSet(statement, resultSet);
        }

        return customer;
    }

    @Override
    public Collection<Customer> getAll() {

        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Collection<Customer> customers = new ArrayList<>();

        try (Connection connection = Pool.getConnection()) {

            statement = connection.prepareStatement("SELECT cust.name, cust.surname, cust.phone FROM customers cust");
            resultSet = statement.executeQuery();
            connection.commit();

            while (resultSet.next()) {

                Customer customer = new Customer();
                customer.setName(resultSet.getString(1));
                customer.setSurname(resultSet.getString(2));
                customer.setPhone(resultSet.getString(3));
                customer.setCards(CardDAOImpl.getInstance().getCards(customer));
                customers.add(customer);
            }

        } catch (SQLException e) {
            log.error(e.getMessage(), e);
        } finally {
            Helper.closeStatementResultSet(statement, resultSet);
        }

        return customers;
    }

    @Override
    public void deleteCustomer(Customer customer) {

        PreparedStatement statement = null;

        try (Connection connection = Pool.getConnection()) {
            statement = connection
                    .prepareStatement("DELETE FROM customers WHERE phone = ?");
            statement.setString(1, customer.getPhone());
            statement.execute();
            connection.commit();

        } catch (SQLException e) {
            log.error(e.getMessage(), e);
        } finally {
            Helper.closeStatementResultSet(statement, null);
        }
    }
}
