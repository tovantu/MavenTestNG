package SqlHelper;

import UiHelper.ReadProperties;

import java.sql.*;

public class SqlHelper {

    public void executeQuery(String query){
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        String connectionUrl = ReadProperties.getInstance("TestSetting").getProperty("sqlConnection");;

        try (Connection connection = DriverManager.getConnection(connectionUrl);
             Statement statement = connection.createStatement();) {

            statement.executeQuery(query);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
