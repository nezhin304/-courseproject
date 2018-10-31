package com.bank.dao;

import com.bank.entity.BankAccount;
import com.bank.entity.Card;
import com.bank.entity.Customer;
import com.bank.pool.Pool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CardDAOImpl extends AbstractDAO implements CardDAO {

    private Logger log = LoggerFactory.getLogger(BankAccountDAOImpl.class);

     private static class Holder {
        static final CardDAO CARD_DAO = new CardDAOImpl();
    }

    public static CardDAO getInstance() {
        return Holder.CARD_DAO;
    }

    @Override
    public void save(Card card) {

        PreparedStatement statement = null;

        BankAccount bankAccount = card.getBankAccount();
        Customer customer = card.getCustomer();


        long bankAccountId = BankAccountDAOImpl.getInstance().save(bankAccount);
        long customerId = CustomerDAOImpl.getInstance().save(customer);

        try (Connection connection = Pool.getConnection()){

            statement = connection
                    .prepareStatement("INSERT INTO cards (number, customer_id, bank_account_id) VALUES (?,?,?)");
            statement.setString(1, card.getNumber().replaceAll("\\D",""));
            statement.setLong(2, customerId);
            statement.setLong(3, bankAccountId);
            statement.execute();
            connection.commit();

        } catch (SQLException e) {
            log.error(e.getMessage(), e);
        } finally {
            Helper.closeStatementResultSet(statement, null);
        }

    }
}
