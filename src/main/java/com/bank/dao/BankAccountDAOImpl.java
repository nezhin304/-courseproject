package com.bank.dao;

import com.bank.entity.BankAccount;
import com.bank.entity.Card;
import com.bank.pool.Pool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

public class BankAccountDAOImpl extends AbstractDAO implements BankAccountDAO {

    private Logger log = LoggerFactory.getLogger(BankAccountDAOImpl.class);

     private static class Holder {
        static final BankAccountDAO BANK_ACCOUNT_DAO = new BankAccountDAOImpl();
    }

    public static BankAccountDAO getInstance() {
        return Holder.BANK_ACCOUNT_DAO;
    }

    @Override
    public void save(BankAccount bankAccount) {

        PreparedStatement statement = null;
        ResultSet resultSet = null;
        long id = 0;

        long cardId = CardDAOImpl.getInstance().save(bankAccount.getCard());


        try (Connection connection = Pool.getConnection()) {
            statement = connection
                    .prepareStatement("INSERT INTO bank_accounts (account, deposit, credit, state, card_id) VALUES (?,?,?,?,?)");
            statement.setString(1, bankAccount.getAccount());
            statement.setBigDecimal(2, bankAccount.getDeposit());
            statement.setBigDecimal(3, bankAccount.getCredit());
            statement.setBoolean(4, bankAccount.getState());
            statement.setLong(5, cardId);
            statement.executeUpdate();
            connection.commit();

        } catch (SQLException e) {

                            log.error(e.getMessage(), e);

        } finally {
            Helper.closeStatementResultSet(statement, resultSet);
        }
    }

    @Override
    public long getId(BankAccount bankAccount) {

        PreparedStatement statement = null;
        ResultSet resultSet = null;
        long id = 0;

        try (Connection connection = Pool.getConnection()) {
            statement = connection
                    .prepareStatement("SELECT id FROM bank_accounts WHERE account = ?");
            statement.setString(1, bankAccount.getAccount());
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
    public BankAccount getBalance(Card card) {

        BankAccount bankAccount = new BankAccount();
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try (Connection connection = Pool.getConnection()) {
            statement = connection
                    .prepareStatement("SELECT account, deposit, credit, state FROM bank_accounts WHERE account = ?");
            statement.setString(1, card.getBankAccount().getAccount());
            resultSet = statement.executeQuery();
            connection.commit();
            resultSet.next();

            bankAccount.setAccount(resultSet.getString(1));
            bankAccount.setDeposit(resultSet.getBigDecimal(2));
            bankAccount.setCredit(resultSet.getBigDecimal(3));
            bankAccount.setState(resultSet.getBoolean(4));

        } catch (SQLException e) {
            log.error(e.getMessage(), e);
        } finally {
            Helper.closeStatementResultSet(statement, resultSet);
        }

        return bankAccount;

    }

    @Override
    public void disable(Card card) {

         PreparedStatement statement = null;

        try (Connection connection = Pool.getConnection()) {
            statement = connection
                    .prepareStatement("UPDATE bank_accounts SET state = false WHERE account = ?");
            statement.setString(1, card.getBankAccount().getAccount());
            statement.executeUpdate();
            connection.commit();

        } catch (SQLException e) {
            log.error(e.getMessage(), e);
        } finally {
            Helper.closeStatementResultSet(statement, null);
        }

    }

    @Override
    public boolean deleteAccount(BankAccount bankAccount) {

        PreparedStatement statement = null;
        boolean result = false;

        try (Connection connection = Pool.getConnection()) {
            statement = connection
                    .prepareStatement("DELETE FROM bank_accounts WHERE account = ?");
            statement.setString(1, bankAccount.getAccount());
            result = statement.execute();
            connection.commit();

        } catch (SQLException e) {
            log.error(e.getMessage(), e);
        } finally {
            Helper.closeStatementResultSet(statement, null);
        }

        return result;
    }
}
