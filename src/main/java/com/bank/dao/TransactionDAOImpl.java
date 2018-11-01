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
    public void getMoney(Card card, BigDecimal money) {

    }

    @Override
    public void addMoney(Card card, BigDecimal money) {

    }
}
