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
        boolean operationSuccess = false;
        BankAccount bankAccount = BankAccountDAOImpl.getInstance().getBalance(card);
        if (!bankAccount.getState()) {
            return false;
        }

        final String UPDATE_QUERY = "UPDATE bank_accounts SET deposit = ?, credit = ? WHERE account = ?";
        BigDecimal newSumDeposit = null;
        BigDecimal newSumCredit = null;

        if (bankAccount.getDeposit().compareTo(money) >= 0) {

            newSumDeposit = bankAccount.getDeposit().subtract(money);
            newSumCredit = bankAccount.getCredit();
        }

        if (bankAccount.getDeposit().compareTo(money) < 0){

            newSumDeposit = new BigDecimal(0.0);
            newSumCredit = bankAccount.getCredit().add(money.subtract(bankAccount.getDeposit()));
        }

            try (Connection connection = Pool.getConnection()) {
                statement = connection
                        .prepareStatement( UPDATE_QUERY );
                statement.setBigDecimal(1, newSumDeposit);
                statement.setBigDecimal(2, newSumCredit);
                statement.setString(3, bankAccount.getAccount());
                operationSuccess = statement.execute();
                connection.commit();

            } catch (SQLException e) {

                log.error(e.getMessage(), e);

            } finally {
                Helper.closeStatementResultSet(statement, null);
            }


        return operationSuccess;
    }

    @Override
    public void addMoney(Card card, BigDecimal money) {

    }
}
