package com.bank.dao;

import com.bank.entity.BankAccount;
import com.bank.entity.Card;
import com.bank.pool.Pool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TransactionDAOImpl extends AbstractDAO implements TransactionDAO {

    private Logger log = LoggerFactory.getLogger(TransactionDAOImpl.class);

    private static class Holder {
        static final TransactionDAO TRANSACTION_DAO = new TransactionDAOImpl();
    }

    public static TransactionDAO getInstance() {

        return Holder.TRANSACTION_DAO;
    }


    @Override
    public boolean getMoney(Card card, BigDecimal money) {

        PreparedStatement statement = null;
        ResultSet resultSet = null;
        BankAccount bankAccount = BankAccountDAOImpl.getInstance().getBalance(card);
        boolean operationSuccess = false;

        if (bankAccount.getDeposit().compareTo(money) == 1) {

            BigDecimal newSum = bankAccount.getDeposit().subtract(money);

            try (Connection connection = Pool.getConnection()) {
                statement = connection
                        .prepareStatement("UPDATE bank_accounts SET deposit = ?");
                statement.setBigDecimal(1, newSum);
                operationSuccess = statement.execute();
                connection.commit();

            } catch (SQLException e) {

                log.error(e.getMessage(), e);

            } finally {
                Helper.closeStatementResultSet(statement, null);
            }
        }

        return operationSuccess;
    }

    @Override
    public void addMoney(Card card, BigDecimal money) {

    }
}
