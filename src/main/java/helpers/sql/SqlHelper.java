package helpers.sql;

import common.ReadProperties;

import java.sql.*;

public class SqlHelper {

    public void executeQuery(String query){
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        String connectionUrl = ReadProperties.getInstanceFromResources("testsetting").getProperty("sqlConnection");

        try (Connection connection = DriverManager.getConnection(connectionUrl);
             Statement statement = connection.createStatement();) {

            statement.executeQuery(query);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
