package abozhik;

import org.postgresql.Driver;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DbConnection {

    private static final Properties properties = new Properties();
    private static Connection connection;

    static {
        File file = new File(System.getProperty("user.dir") + "/src/main/resources/db.properties");
        try {
            properties.load(new FileReader(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        if (connection == null) {
            try {
                DriverManager.registerDriver(new Driver());
                connection = DriverManager.getConnection(
                        (String) properties.get("url"),
                        (String) properties.get("user"),
                        (String) properties.get("password"));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return connection;
    }

}
