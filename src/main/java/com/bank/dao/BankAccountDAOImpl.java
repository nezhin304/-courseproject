package dao;

import entity.BankAccount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pool.Pool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BankAccountDAOImpl implements BankAccountDAO {

    Logger log = LoggerFactory.getLogger(BankAccountDAOImpl.class);

    @Override
    public void save(BankAccount bankAccount) {

        PreparedStatement statement = null;

        try (Connection connection = Pool.getConnection()) {



        } catch (SQLException e) {
            log.error(e.getMessage(), e);
        }

    }
}
