package ua.mario.objects;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static ua.mario.jdbc.dbsettings.*;
import static ua.mario.utils.ResultSetConverterToJSON.convert;

public class Dictionary {

public static JSONArray SQLDictionary;

public static String Translate (String Patern, String Language) {
  String MyTranslate = Patern;
  try {
      for (int i = 0; i < SQLDictionary.length(); i++) {
          //    System.out.println(SQLDictionary.length() +" "+i +" "+ MyTranslate);
          if (SQLDictionary.getJSONObject(i).getString("en").equals(Patern)) {
              MyTranslate = SQLDictionary.getJSONObject(i).getString(Language);
              i = SQLDictionary.length() + 1;
          }
      }
  }catch (JSONException e) {
      e.printStackTrace();
      MyTranslate = Patern;
  }
 // System.out.println(Patern+" - "+MyTranslate);
  return MyTranslate;
}

public static List<String> getValuesForGivenKey(JSONArray jsonArray, String key) {
 //JSONArray jsonArray = new JSONArray(jsonArrayStr);
 return IntStream.range(0, jsonArray.length())
         .mapToObj(index -> ((JSONObject)jsonArray.get(index)).optString(key))
         .collect(Collectors.toList());
 }

public static boolean GetDictionary() {
    String Query = "SELECT `en`,`ua`,`ru` FROM `multi_language`";
    boolean Reet = false;
    try {
        Connection connection= DriverManager.getConnection(dburl, dbusername, dbpassword);
        Statement statement=connection.createStatement();
        ResultSet resultSet=statement.executeQuery(Query);
        JSONArray ja;
        ja = convert(resultSet);
        if (!ja.isEmpty()) {
            SQLDictionary = ja;
            Reet = true;
        }
        resultSet.close();
        statement.close();
        connection.close();
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return Reet;
}

}
