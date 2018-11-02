package com.bank.dao;

import com.bank.entity.Card;
import com.bank.entity.Customer;
import com.bank.pool.Pool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

public class CardDAOImpl extends AbstractDAO implements CardDAO {

    private Logger log = LoggerFactory.getLogger(BankAccountDAOImpl.class);

    private static class Holder {
        static final CardDAO CARD_DAO = new CardDAOImpl();
    }

    public static CardDAO getInstance() {
        return Holder.CARD_DAO;
    }

    @Override
    public long save(Card card) {

        PreparedStatement statement = null;
        ResultSet resultSet = null;

        long customerId = CustomerDAOImpl.getInstance().save(card.getCustomer());
        long cardId = 0;

        try (Connection connection = Pool.getConnection()) {

            statement = connection
                    .prepareStatement("INSERT INTO cards (number, customer_id) VALUES (?,?)", Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, card.getNumber().replaceAll("\\D", ""));
            statement.setLong(2, customerId);
            statement.execute();
            connection.commit();
            resultSet = statement.getGeneratedKeys();
            resultSet.next();
            cardId = resultSet.getLong(1);

        } catch (SQLException e) {

            if (e.getMessage().contains("duplicate key value")) {

                cardId = CardDAOImpl.getInstance().getCardId(card);

            } else {

                log.error(e.getMessage(), e);
            }
        } finally {
            Helper.closeStatementResultSet(statement, resultSet);
        }

        return cardId;
    }

    @Override
    public void deleteCard(Card card) {

        PreparedStatement statement = null;

        try (Connection connection = Pool.getConnection()) {

            statement = connection
                    .prepareStatement("DELETE FROM cards WHERE number = ?");
            statement.setString(1, card.getNumber());
            statement.execute();
            connection.commit();

        } catch (SQLException e) {
            log.error(e.getMessage(), e);
        } finally {
            Helper.closeStatementResultSet(statement, null);
        }
    }

    @Override
    public Collection<Card> getCards(Customer customer) {

        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Collection<Card> cards = new ArrayList<>();

        try (Connection connection = Pool.getConnection()) {

            statement = connection
                    .prepareStatement("SELECT number FROM cards WHERE customer_id = ?");
            statement.setLong(1, CustomerDAOImpl.getInstance().getId(customer));
            resultSet = statement.executeQuery();
            connection.commit();

            while (resultSet.next()) {

                Card card = new Card();
                card.setNumber(resultSet.getString(1));
                card.setBankAccount(BankAccountDAOImpl.getInstance().getBankAccount(card));
                cards.add(card);
            }

        } catch (SQLException e) {
            log.error(e.getMessage(), e);
        } finally {
            Helper.closeStatementResultSet(statement, resultSet);
        }

        return cards;
    }

    @Override
    public long getCardId(Card card) {

        PreparedStatement statement = null;
        ResultSet resultSet = null;
        long cardId = 0;

        try (Connection connection = Pool.getConnection()) {

            statement = connection.prepareStatement("SELECT id FROM cards WHERE number = ?");
            statement.setString(1, card.getNumber());
            resultSet = statement.executeQuery();
            connection.commit();
            resultSet.next();
            cardId = resultSet.getLong(1);

        } catch (SQLException e) {
            log.error(e.getMessage(), e);
        } finally {
            Helper.closeStatementResultSet(statement, resultSet);
        }

        return cardId;
    }
}
