package AMQPManagment.DatabaseConnections.Configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Properties;

/**
 * Created by aleksanderr on 18/06/17.
 */
public class PersistanceSecureConfiguration {

    private static final String HIBERNATE_CONNECTION_URL_KEY = "hibernate.connection.url";
    private static final String HIBERNATE_CONNECTION_USERNAME_KEY = "hibernate.connection.username";
    private static final String HIBERNATE_CONNECTION_PASSWORD_KEY = "hibernate.connection.password";

    private static final String DATABASE_URL_DOMAIN_VALUE = "DATABASE_URL_DOMAIN";
    private static final String DATABASE_LOGIN_VALUE = "DATABASE_LOGIN";
    private static final String DATABASE_PASSWORD_VALUE = "DATABASE_PASSWORD";

    public Properties getHibernateCredential() {
        Properties properties = new Properties();

        properties.put(HIBERNATE_CONNECTION_URL_KEY, System.getenv(DATABASE_URL_DOMAIN_VALUE));
        properties.put(HIBERNATE_CONNECTION_USERNAME_KEY, System.getenv(DATABASE_LOGIN_VALUE));
        properties.put(HIBERNATE_CONNECTION_PASSWORD_KEY, System.getenv(DATABASE_PASSWORD_VALUE));

        return properties;
    }
}
