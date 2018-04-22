package chess.database.configuration;

import chess.utils.settings.Settings;

import java.util.Properties;

/**
 * Created by aleksanderr on 18/06/17.
 */
public class PersistanceSecureConfiguration {
    private static final String HIBERNATE_CONNECTION_URL_KEY = "hibernate.connection.url";
    private static final String HIBERNATE_CONNECTION_USERNAME_KEY = "hibernate.connection.username";
    private static final String HIBERNATE_CONNECTION_PASSWORD_KEY = "hibernate.connection.password";

    public Properties getHibernateCredential() {
        Properties properties = new Properties();

        properties.put(HIBERNATE_CONNECTION_URL_KEY, Settings.getPostgresDBUrl());
        properties.put(HIBERNATE_CONNECTION_USERNAME_KEY, Settings.getPostgresDBUsername());
        properties.put(HIBERNATE_CONNECTION_PASSWORD_KEY, Settings.getPostgresDBPassword());

        return properties;
    }
}
