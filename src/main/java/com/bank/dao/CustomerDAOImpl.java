package com.bank.dao;

import com.bank.entity.Customer;
import com.bank.pool.Pool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

public class CustomerDAOImpl extends AbstractDAO implements CustomerDAO {

    Logger log = LoggerFactory.getLogger(CustomerDAOImpl.class);

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
    public Customer getCustomer(long id) {
        return null;
    }
}
