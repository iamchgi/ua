package ua.mario.jdbc;

import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.*;

import static ua.mario.http.messages.AdminMessage;
import static ua.mario.jdbc.dbsettings.*;
import static ua.mario.utils.ResultSetConverterToJSON.convert;

public class GetResultSetJSON {
 public static JSONObject GetBotResToJSON (){
    JSONObject BotRes = new JSONObject();
    BotRes.put("ok",false);
    String Query = "SELECT * FROM bots_list where `active` = 1  LIMIT 1";
    try {
         Connection connection = DriverManager.getConnection(dburl, dbusername, dbpassword);
         Statement statement = connection.createStatement();
         ResultSet resultSet = statement.executeQuery(Query);
         while (resultSet.next()) {
            BotRes.put("botname",resultSet.getString("botname"));
            BotRes.put("bottoken",resultSet.getString("bottoken"));
            BotRes.put("bottype",resultSet.getString("bottype"));
            BotRes.put("bot_function",resultSet.getString("bot_function"));
            BotRes.put("log_to_term",resultSet.getBoolean("log_to_term"));
            BotRes.put("log_to_file",resultSet.getBoolean("log_to_file"));
            BotRes.put("log_filename",resultSet.getString("log_filename"));
            BotRes.put("ok",true);
         }
         resultSet.close();
         statement.close();
         connection.close();
     } catch (SQLException e) {
         e.printStackTrace();
     }
    return BotRes;
 }

 public static JSONArray GetResultSetJSONMessageToDelete(Long idChat,Long idUser, Long currentDate) {
     JSONArray list = new JSONArray();
     String Query = "SELECT `id_message`, `id_user`, `id_chat`, `date` FROM `m_to_del` WHERE `id_user``id_chat``date`";
     try {
         Connection connection = DriverManager.getConnection(dburl, dbusername, dbpassword);
         Statement statement = connection.createStatement();
         ResultSet resultSet = statement.executeQuery(Query);
         JSONArray ja;
         ja = convert(resultSet);
         if (!ja.isEmpty()) {
             list = ja;
         }
         resultSet.close();
         statement.close();
         connection.close();
     } catch (
             SQLException e) {
         e.printStackTrace();
     }
     return list;
 }
}
