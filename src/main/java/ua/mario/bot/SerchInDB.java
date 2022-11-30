package ua.mario.bot;

import ua.mario.objects.Dictionary;

import java.sql.*;
import static ua.mario.jdbc.dbsettings.*;

public class SerchInDB {

@org.jetbrains.annotations.NotNull
public static String SerchUser (String Patern,String Language,Byte MaxItem) {
   String SQLCount = "SELECT COUNT(*) FROM users WHERE (name like '%"+Patern+"%' or surname like '%"+Patern+"%' or fname like '%"+Patern+"%' or e_mail like '%"+Patern+"%' or phone like '%"+Patern+"%' or int_phone = '"+Patern+"' or position like '%"+Patern+"%') and serch_ok = True";
   String SQLQuery = "select `name`,`surname`,`fname`,`phone`,`e_mail`,`int_phone`,`position` from users where (name like '%"+Patern+"%' or surname like '%"+Patern+"%' or fname like '%"+Patern+"%' or e_mail like '%"+Patern+"%' or phone like '%"+Patern+"%' or int_phone = '"+Patern+"' or position like '%"+Patern+"%') and serch_ok = True";
   String Reet = "";
   try {
        Connection connection= DriverManager.getConnection(dburl, dbusername, dbpassword);
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(SQLCount);
     //   System.out.println(SQLCount);
        if (resultSet.getMetaData().getColumnCount() == 1) {
            resultSet.next();
            int RecordCount = resultSet.getInt("COUNT(*)");
            resultSet.close();
            if (RecordCount == 0) {
                Reet = Dictionary.Translate("Sorry, nothing was found.",Language);
            } else if (RecordCount > MaxItem) {
                       Reet = Dictionary.Translate("Found",Language)+" "+ RecordCount +", "+ Dictionary.Translate("it is more than necessary",Language)+"\n";
            }
            else {
                  resultSet = statement.executeQuery(SQLQuery);
              //    System.out.println(SQLQuery);
                  Reet = Dictionary.Translate("Found",Language)+" "+ RecordCount + " "+Dictionary.Translate("item(s)",Language)+".";
                  String mmm;
                  while (resultSet.next()) {
                      String TS;
                      mmm = "\n<b>%s %s %s</b>";
                      Reet = Reet + String.format(mmm,resultSet.getString("surname"),resultSet.getString("name"),resultSet.getString("fname"));
                      TS = resultSet.getString("phone");
                      if (TS != null) {
                          mmm = "\n %s : <b> %s </b>";
                          Reet = Reet + String.format(mmm, Dictionary.Translate("mob. phone", Language), TS);
                      }
                      TS = resultSet.getString("e_mail");
                      if (TS != null) {
                          mmm = "\n %s : <b> %s </b>";
                          Reet = Reet + String.format(mmm,"e-mail",TS);
                      }
                      TS = resultSet.getString("int_phone");
                      if (TS != null) {
                          mmm = "\n %s : <b> %s </b>";
                          Reet = Reet + String.format(mmm,Dictionary.Translate("internal telephone",Language),TS);
                      }
                      TS = resultSet.getString("position");
                      if (TS != null) {
                          mmm = "\n %s : <b> %s </b>";
                          Reet = Reet + String.format(mmm,Dictionary.Translate("position",Language),TS);
                      }
                      Reet = Reet + "\n ----------------------------------";
                  }
            resultSet.close();
            }
        }
        statement.close();
        connection.close();
   } catch (SQLException e) {
        e.printStackTrace();}
   return Reet;
 }
}
