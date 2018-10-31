package dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

class Helper {

   private static Logger log = LoggerFactory.getLogger(Helper.class);

   static void closeStatementResultSet(PreparedStatement statement, ResultSet resultSet) {
       try {
           if (statement != null) {
               statement.close();
           }
           if (resultSet != null) {
               resultSet.close();
           }
       } catch (SQLException e) {
           log.error(e.getMessage());
       }
   }
}
