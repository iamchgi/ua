package ua.mario.jdbc;

import java.sql.*;
import static ua.mario.jdbc.dbsettings.*;

public class DBtest {
 public static boolean DBOK() {
        String Query = "SHOW TABLES";
        boolean Reet = false;
        try {
            Connection connection = DriverManager.getConnection(dburl, dbusername, dbpassword);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(Query);
            int rows = 0;
            while (resultSet.next()){
                rows = rows + 1;
        //        System.out.println(resultSet.getString(1));
            }
            resultSet.close();
            statement.close();
            connection.close();
            if (rows > 5) {  Reet = true;  }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Reet;
    }
}
