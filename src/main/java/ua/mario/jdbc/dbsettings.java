package ua.mario.jdbc;
import java.sql.*;

import static ua.mario.http.messages.AdminMessage;

public class dbsettings {
    public static String  dbHost = "localhost";
    public static String  dbPort = "3306";
    public static String  dbName = "jtelega";
    public static String  dburl="jdbc:mysql://"+dbHost+":"+dbPort+"/"+dbName+"?serverTimezone=UTC&characterEncoding=utf8&useUnicode=true";
    public static String  dbusername="jtelega";
    public static String  dbpassword="jtelega%$#@!";

  public static void setDBSettings (String dbhost,String dbport,String dbname,String DBusername,String DBpassword) {
        dbHost = dbhost;
        dbPort = dbport;
        dbName = dbname;
        dbusername = DBusername;
        dbpassword = DBpassword;
  }

  public static void updateDBUrl () {
     dburl = "jdbc:mysql://"+dbHost+":"+dbPort+"/"+dbName+"?serverTimezone=UTC&characterEncoding=utf8&useUnicode=true";
  }

}
