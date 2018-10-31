package com.bank.dao;

import com.bank.pool.Pool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;

public class AbstractDAO {

    private Logger log = LoggerFactory.getLogger(AbstractDAO.class);

    Connection getConnection(){

        Connection connection = null;

        try {
            connection = Pool.getConnection();
        } catch (SQLException e) {
            log.error(e.getMessage());
        }

        return connection;
    }
}
