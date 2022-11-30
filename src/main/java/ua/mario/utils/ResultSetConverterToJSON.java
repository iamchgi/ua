package ua.mario.utils;
import com.fasterxml.jackson.core.JsonGenerator;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
public class ResultSetConverterToJSON {
  public static JSONArray convert( ResultSet rs )  throws SQLException, JSONException {
     JSONArray json = new JSONArray();
     ResultSetMetaData rsmd = rs.getMetaData();
     while(rs.next()) {
          int numColumns = rsmd.getColumnCount();
          JSONObject obj = new JSONObject();
          for (int i=1; i<numColumns+1; i++) {
              String column_name = rsmd.getColumnName(i);
              if(rsmd.getColumnType(i)==java.sql.Types.ARRAY){
                obj.put(column_name, rs.getArray(column_name));
              }
              else if(rsmd.getColumnType(i)==java.sql.Types.BIGINT){
                      obj.put(column_name, rs.getInt(column_name));
                   }
              else if(rsmd.getColumnType(i)==java.sql.Types.BOOLEAN){
                      obj.put(column_name, rs.getBoolean(column_name));
                   }
              else if(rsmd.getColumnType(i)==java.sql.Types.BLOB){
                     obj.put(column_name, rs.getBlob(column_name));
                   }
              else if(rsmd.getColumnType(i)==java.sql.Types.DOUBLE){
                        obj.put(column_name, rs.getDouble(column_name));
                   }
              else if(rsmd.getColumnType(i)==java.sql.Types.FLOAT){
                        obj.put(column_name, rs.getFloat(column_name));
                   }
              else if(rsmd.getColumnType(i)==java.sql.Types.INTEGER){
                        obj.put(column_name, rs.getInt(column_name));
                   }
              else if(rsmd.getColumnType(i)==java.sql.Types.NVARCHAR){
                        obj.put(column_name, rs.getNString(column_name));
                   }
              else if(rsmd.getColumnType(i)==java.sql.Types.VARCHAR){
                        obj.put(column_name, (rs.getString(column_name) != null ? rs.getString(column_name) : " "));
                   }
              else if(rsmd.getColumnType(i)==java.sql.Types.TINYINT){
                       obj.put(column_name, rs.getInt(column_name));
                   }
              else if(rsmd.getColumnType(i)==java.sql.Types.SMALLINT){
                      obj.put(column_name, rs.getInt(column_name));
                   }
              else if(rsmd.getColumnType(i)==java.sql.Types.DATE){
                        obj.put(column_name, rs.getDate(column_name));
                   }
              else if(rsmd.getColumnType(i)==java.sql.Types.TIMESTAMP){
                       obj.put(column_name, rs.getTimestamp(column_name));
                   }
              else{
                        obj.put(column_name, rs.getObject(column_name));
                   }
          }
          json.put(obj);
     }
     return json;
  }

public static JSONObject ResultSetconvertToJSONObject(ResultSet resultSet) throws SQLException, JSONException{
   JSONObject obj = new JSONObject();
   ResultSetMetaData rsmd = resultSet.getMetaData();
   while (resultSet.next()) {
       int total_rows = rsmd.getColumnCount();
       for (int i = 1; i < total_rows+1; i++) {
           String column_name = rsmd.getColumnName(i);
           if(rsmd.getColumnType(i)==java.sql.Types.ARRAY){
               obj.put(column_name, resultSet.getArray(column_name));
           }
           else if(rsmd.getColumnType(i)==java.sql.Types.BIGINT){
               obj.put(column_name, resultSet.getInt(column_name));
           }
           else if(rsmd.getColumnType(i)==java.sql.Types.BOOLEAN){
               obj.put(column_name, resultSet.getBoolean(column_name));
           }
           else if(rsmd.getColumnType(i)==java.sql.Types.BLOB){
               obj.put(column_name, resultSet.getBlob(column_name));
           }
           else if(rsmd.getColumnType(i)==java.sql.Types.DOUBLE){
               obj.put(column_name, resultSet.getDouble(column_name));
           }
           else if(rsmd.getColumnType(i)==java.sql.Types.FLOAT){
               obj.put(column_name, resultSet.getFloat(column_name));
           }
           else if(rsmd.getColumnType(i)==java.sql.Types.INTEGER){
               obj.put(column_name, resultSet.getInt(column_name));
           }
           else if(rsmd.getColumnType(i)==java.sql.Types.NVARCHAR){
               obj.put(column_name, resultSet.getNString(column_name));
           }
           else if(rsmd.getColumnType(i)==java.sql.Types.VARCHAR){
               obj.put(column_name, (resultSet.getString(column_name) != null ? resultSet.getString(column_name) : " "));
           }
           else if(rsmd.getColumnType(i)==java.sql.Types.TINYINT){
               obj.put(column_name, resultSet.getInt(column_name));
           }
           else if(rsmd.getColumnType(i)==java.sql.Types.SMALLINT){
               obj.put(column_name, resultSet.getInt(column_name));
           }
           else if(rsmd.getColumnType(i)==java.sql.Types.DATE){
               obj.put(column_name, resultSet.getDate(column_name));
           }
           else if(rsmd.getColumnType(i)==java.sql.Types.TIMESTAMP){
               obj.put(column_name, resultSet.getTimestamp(column_name));
           }
           else{
               obj.put(column_name, resultSet.getObject(column_name));
           }
     //      obj.put(resultSet.getMetaData().getColumnLabel(i + 1).toLowerCase(), resultSet.getObject(i + 1));
            }
       }
 return obj;
}

public static JSONArray ResultSetconvertToJSONArray(ResultSet resultSet) throws SQLException {
    JSONArray jsonArray = new JSONArray();
    while (resultSet.next()) {
          JSONObject obj = new JSONObject();
          int total_rows = resultSet.getMetaData().getColumnCount();
          for (int i = 0; i < total_rows; i++) {
              obj.put(resultSet.getMetaData().getColumnLabel(i + 1).toLowerCase(), (resultSet.getObject(i + 1) != null ? resultSet.getObject(i + 1) : "-"));
      //        System.out.print(resultSet.getMetaData().getColumnLabel(i + 1).toLowerCase()+" "+(resultSet.getObject(i + 1) != null ? resultSet.getObject(i + 1) : "-")+" ");
          }
          System.out.println("");
          jsonArray.put(obj);
      }
  return jsonArray;
 }

 private static void writeResultSetToJson(final ResultSet rs, final JsonGenerator jg) throws SQLException, IOException {
        final var rsmd = rs.getMetaData();
        final var columnCount = rsmd.getColumnCount();
        jg.writeStartArray();
        while (rs.next()) {
            jg.writeStartObject();
            for (var i = 1; i <= columnCount; i++) {
                jg.writeObjectField(rsmd.getColumnName(i), rs.getObject(i));
            }
            jg.writeEndObject();
        }
        jg.writeEndArray();
 }
}
