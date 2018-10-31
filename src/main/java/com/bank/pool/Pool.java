package pool;

import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class Pool implements AutoCloseable {

    private Logger logger = LoggerFactory.getLogger(Pool.class);

    private static HikariDataSource dataSource;

    private Pool() {



        Properties properties = new Properties();
        try {
            InputStream inputStream = new FileInputStream("./src/main/resources/META-INF/property.config");
            properties.load(inputStream);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }

        dataSource = new HikariDataSource();
        dataSource.setDriverClassName(properties.getProperty("DriverClassName"));
        dataSource.setJdbcUrl(properties.getProperty("JdbcUrl"));
        dataSource.setUsername(properties.getProperty("Username"));
        dataSource.setPassword(properties.getProperty("Password"));
        dataSource.setMinimumIdle(5);
        dataSource.setMaximumPoolSize(10);
        dataSource.setAutoCommit(false);
        try {
            dataSource.setLoginTimeout(3);
        } catch (SQLException e) {
            throw new PoolException(e.getMessage(), e);
        }
    }

    private static class PoolHelper {
        private final static Pool pool = new Pool();
    }

    private static Pool getInstance() {
        return PoolHelper.pool;
    }

    public static Connection getConnection() throws SQLException {
        return PoolHelper.pool.dataSource.getConnection();
    }

    @Override
    public void close() throws IOException {
        dataSource.close();
    }
}