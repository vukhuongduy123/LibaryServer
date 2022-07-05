package SQLConnection;

import java.sql.Connection;
import java.sql.DriverManager;

public class SQLConnection {
    private static final String JDBC_DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    private static final String JDBC_URL = "jdbc:sqlserver://localhost:1433; databaseName=Library;integratedSecurity=true; " +
            "encrypt=false; trustServerCertificate=true; " +
            "trustStore= C:/Program Files/Microsoft/jdk-17.0.2.8-hotspot/lib/security/cacerts;" +
            "trustStorePassword=changeit";
    private static final String JDBC_USERNAME = "sa";
    private static final String JDBC_PASSWORD = "123abc";
    private static Connection connection;

    public static void initConnection() {
        connection = null;
        try {
            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static Connection getConnection() {
        return connection;
    }
}
