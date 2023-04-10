package ua.mario.bot;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mysql.cj.jdbc.exceptions.MySQLQueryInterruptedException;
import org.intellij.lang.annotations.Language;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import ua.mario.http.messages;
import ua.mario.objects.Dictionary;
import ua.mario.objects.UserInfo;
import ua.mario.objects.UserSession;
import javax.management.Query;
import javax.print.attribute.IntegerSyntax;
import java.io.IOException;
import java.sql.*;
import java.util.Iterator;

import static ua.mario.http.messages.deleteMessage;
import static ua.mario.jdbc.dbsettings.*;
import static ua.mario.utils.ResultSetConverterToJSON.*;

public class DefaultUser {
    public  UserInfo UIF = new UserInfo();
    public  UserSession US = new UserSession();
    public  messages DUMessage = new messages();

    private String UserMenuText = "";
    public  String MessageText = "";
    private Long UserMessageId = Long.valueOf(0);
    public  String Data = "";
    String[] ModeArrOfStr;
    String[] ModeTextArrOfStr;
    private Connection DBConnection;
    private Statement DBStatement;
    private ResultSet DBResultSet;
    private String CurrentDateString;
    private String MessageTextOut;
    private Long CurrentMenuMessageId = Long.valueOf(0);

 public Statement getDBStatement(){
     return this.DBStatement;
 }

 public Connection getDBConnection(){
        return this.DBConnection;
 }

 public void SendMessage (String text,String parse_mode,Integer StillAliveSeconds) {
   DUMessage.SendMyMessage(UIF.getId_user(),UIF.getId_chat(),text,parse_mode);
   if (!StillAliveSeconds.equals(0)) {
       Long StillAlive = DUMessage.getMessageDate() + Long.valueOf(StillAliveSeconds);
       SetIdMessageToDeleteToDB(UIF.getId_user(),UIF.getId_chat(),DUMessage.getIdMessage(),StillAlive);
   }
 }

 public  void SetIdMessageToDeleteToDB (Long idChat,Long idUser,Long idMessage,Long DateOfDelete) {
        String SQLInsert = "INSERT INTO `m_to_del`(`id_message`, `id_user`, `id_chat`, `date`) VALUES ('"+idMessage+"','"+idChat+"','"+idUser+"','"+DateOfDelete+"')";
        try {
       //          System.out.println(SQLInsert);
            if (DBStatement.executeUpdate(SQLInsert) == 1) {
                //       AdminMessage("Message " + idMessage + " добавлен in Delete");
            }
         } catch (SQLException e) { e.printStackTrace();   }
 }

 public void DeleteOldMessage(Long date) {
     String SQLQuery = "SELECT `id_message` FROM `m_to_del` WHERE `id_user` = "+UIF.getId_user()+" and `id_chat` = "+UIF.getId_chat()+" and `date` < "+date;
     try {
         DBResultSet = DBStatement.executeQuery(SQLQuery);
         while (DBResultSet.next()) {
             deleteMessage(UIF.getId_chat(),DBResultSet.getLong(1));
         }
         SQLQuery = "DELETE FROM `m_to_del` WHERE `id_user` = "+UIF.getId_user()+" and `id_chat` = "+UIF.getId_chat()+" and `date` < "+date;
         DBStatement.execute(SQLQuery);
     } catch (SQLException e) {
         e.printStackTrace();
     }
 }

 public boolean OpenDBConnection () {
    try {
        DBConnection = DriverManager.getConnection(dburl, dbusername, dbpassword);
        DBStatement = DBConnection.createStatement();
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
    return true;
 }

 public boolean CloseDBConnection() {
     try {
         DBResultSet.close();
         DBStatement.close();
         DBConnection.close();
     } catch (SQLException e) {
         e.printStackTrace();
         return false;
     }
   return true;
 }
 public String CurrentUserInfo () {
  String CurrentUserInfo;
  CurrentUserInfo = Dictionary.Translate("There is something about me",UIF.getLanguage())+"...\n";
     try {
         ObjectMapper mapper = new ObjectMapper();
         String JsonString = mapper.writeValueAsString(UIF);
         JSONObject json = new JSONObject(JsonString);
         Iterator<String> iterator = json.keys();
         while (iterator.hasNext()) {
             String key = iterator.next();
             String value = json.get(key).toString();
             CurrentUserInfo = CurrentUserInfo + "<b>"+ key+"</b> : "+value +"\n";
         }
         JsonString = mapper.writeValueAsString(US);
         json = new JSONObject(JsonString);
         iterator = json.keys();
         while (iterator.hasNext()) {
             String key = iterator.next();
             String value = json.get(key).toString();
             CurrentUserInfo = CurrentUserInfo + "<b>"+ key+"</b> : "+value +"\n";
         }
     } catch (JsonProcessingException e) {
         e.printStackTrace();
     }
  return CurrentUserInfo;
 }

 public String getUserMenuText (){ return this.UserMenuText; }

 public void setUserMenuText (String UserMenuText) { this.UserMenuText = UserMenuText; }

 public String getUserModeText() {
     String Reet = US.getMode();
     String SQLQuery = "SELECT  `text` FROM `mode` WHERE `mode` = '"+Reet+"' LIMIT 1";
     try {
         DBResultSet = DBStatement.executeQuery(SQLQuery);
         while (DBResultSet.next()) {
            Reet = DBResultSet.getString(1);
         }
     } catch (SQLException e) {
         e.printStackTrace();
     }
     return Reet;
 }

 public void ParseDataString() {
        this.ModeArrOfStr = this.Data.split("_", 5);
//        for (String a : this.ModeArrOfStr)
//            System.out.println(a);
 }

 public String getUserNameById (Long id_user) {
  String UserName = "";
  String SQLQuery = "SELECT `name`,`surname` FROM `users` WHERE `id_user` = "+id_user+" LIMIT 1";
     try {
         DBResultSet = DBStatement.executeQuery(SQLQuery);
         while (DBResultSet.next()) {
             UserName = DBResultSet.getString(1) + " " + DBResultSet.getString(2);
         }
     } catch (SQLException e) {
         e.printStackTrace();
     }
  return UserName;
 }

 public boolean NewITTask() {
   Boolean Reet = false;
   String SQLQuery = "INSERT INTO `ittask`(`id_user`, `id_chat`, `task`, `date_str`, `del`) " +
           "VALUES ("+UIF.getId_user()+","+UIF.getId_chat()+",'"+this.MessageText+"','"+this.CurrentDateString+"',"+false+")";
   System.out.println(SQLQuery);
   try {
       if (DBStatement.executeUpdate(SQLQuery) == 1) {
           Reet = true;
           System.out.println("NEW TASK IT - OK");
       }
   } catch (SQLException e) {
       e.printStackTrace();
   }
     return Reet;
 }

 public boolean TaskStatus (Integer idTask,Boolean Status) {
   Boolean Reet = false;
     String SQLQueryUpdate = "UPDATE `ittask` SET `del`= %s WHERE `id` = " + idTask;
   if (Status) { SQLQueryUpdate = String.format(SQLQueryUpdate,"true");
   }else { SQLQueryUpdate = String.format(SQLQueryUpdate,"false"); }
   try {
    //   String SQLQueryUpdate = "UPDATE `ittask` SET `del`= true WHERE `id` = " + idTask;
       PreparedStatement DBStatement = DBConnection.prepareStatement(SQLQueryUpdate);
       if (DBStatement.executeUpdate() == 1) {
           Reet = true;
       }
   } catch (SQLException e) { e.printStackTrace();   }
   return Reet;
 }

 public JSONObject ItTaskShow() {
  JSONObject jsonTask = new JSONObject("{\"ok\":false,\"yesno\":\"noyes\"}");
  String SQLQueryTask ="SELECT `id`,`task`,`date_str`, `del` FROM `ittask` WHERE `id` = "+this.ModeArrOfStr[2]+" LIMIT 1";
  String SQLQueryComment = "SELECT `id`, `id_task`, `comment`, `date` FROM `taskcomments` WHERE `id_task` = '"+this.ModeArrOfStr[2]+"'";
     try {
         DBResultSet = DBStatement.executeQuery(SQLQueryTask);
         JSONObject jsonTaskData = new JSONObject();
         jsonTaskData = ResultSetconvertToJSONObject(DBResultSet);
         jsonTask.put("task_data",jsonTaskData);
         DBResultSet = DBStatement.executeQuery(SQLQueryComment);
         JSONArray jsonComments = new JSONArray();
         jsonComments = ResultSetconvertToJSONArray(DBResultSet);
         jsonTask.put("comments",jsonComments);
         jsonTask.put("ok",true);
     } catch (SQLException e) {
         e.printStackTrace();
     }
     return jsonTask;
 }

 public JSONObject getUserInfo(){
   JSONObject jsonUserInfo = new JSONObject("{\"callback_data\":\""+this.US.getMode()+"\",\"ok\":false}");
   String SQLQueryUserInfo = "SELECT `id`,`first_name`,`last_name`,`user_name`,`name`,`surname`,`fname`,`id_user`," +
           "`id_chat`,`type`,`phone`,`phone1`,`e_mail`,`int_phone`,`position`,`pass`,`access`,`last_access`,`req_count`," +
           "`serch_ok`,`access_chat`,`access_serch`,`access_it`,`access_support`,`access_vlan`,`access_printers`,`access_ip`," +
           "`access_admin`,`access_mat`,`language` FROM `users` WHERE `id` = %s LIMIT 1";
   SQLQueryUserInfo = String.format(SQLQueryUserInfo,this.ModeArrOfStr[2]);
   try {
        DBResultSet = DBStatement.executeQuery(SQLQueryUserInfo);
        jsonUserInfo.put("user_info",ResultSetconvertToJSONObject(DBResultSet));
        String SQLQueryUserSession = "SELECT `id`,`id_user`,`mode`,`modetext`,`id_m_m`,`id_m_s`,`old_message_text`,`it_ip`,`it_vlan`,`it_mac`," +
                "`it_groupdevice`,`it_device`,`it_group1c`,`it_user1c`,`it_groupplace`,`it_place` FROM `sessions` WHERE `id_user` = %s and `id_chat` = %s LIMIT 1";
        SQLQueryUserSession = String.format(SQLQueryUserSession,jsonUserInfo.getJSONObject("user_info").getLong("id_user"),jsonUserInfo.getJSONObject("user_info").getLong("id_chat"));
        DBResultSet = DBStatement.executeQuery(SQLQueryUserSession);
        jsonUserInfo.put("session",ResultSetconvertToJSONObject(DBResultSet));
        jsonUserInfo.put("ok",true);
   }catch (SQLException e) { e.printStackTrace();}
   catch (JSONException e) { e.printStackTrace();}
   return jsonUserInfo;
 }

 public JSONObject getUserList(){
  JSONObject jsonUserList = new JSONObject();
  JSONArray jsonList = new JSONArray();
  Integer HawMachIs;
  Integer ItemMax = 10;
  Integer start = 0;
  Integer end = 0;
  Integer startl = 0;
  Integer endl = 0;
  Integer startr = 0;
  Integer endr = 0;
  int[] R = new int[2];
  JSONObject jsonObjMenu = new JSONObject();
  JSONArray jsonArrayLine0 = new JSONArray();
  JSONArray jsonArrayLineB = new JSONArray();
  JSONObject NavigationButton0 = new  JSONObject();
  String SQLQueryUserList = "SELECT `id`,`first_name`,`last_name`,`user_name`,`name`,`surname`,`fname`,`id_user`," +
          "`id_chat`,`type`,`phone`,`phone1`,`e_mail`,`int_phone`,`position`,`pass`,`access`,`last_access`,`req_count`," +
          "`serch_ok`,`access_chat`,`access_serch`,`access_it`,`access_support`,`access_vlan`,`access_printers`,`access_ip`," +
          "`access_admin`,`access_mat`,`language` FROM `users` WHERE `serch_ok` = 1";
  SQLQueryUserList = "SELECT `id`,`name`,`surname`,`fname` FROM `users` WHERE `serch_ok` = 1 ORDER BY `surname`";
  try {
       DBResultSet = DBStatement.executeQuery(SQLQueryUserList);
       jsonList = convert(DBResultSet);
       HawMachIs = jsonList.length();
      if (HawMachIs == 0) {
          this.UserMenuText = Dictionary.Translate("Sorry, but have no one user found.",UIF.getLanguage());
      } else {

          if (this.ModeArrOfStr.length == 3) {
              start = 0;
              end = HawMachIs - 1;
              if (end > ItemMax) { end = ItemMax - 1;}
          } else {
              start = Integer.valueOf(this.ModeArrOfStr[3]);
              end = Integer.valueOf(this.ModeArrOfStr[4]);
          }
          if (start < 0) { start = 0; }
          if (end > HawMachIs -1) { end = HawMachIs - 1;}


     //  Navigation LEFT
          R[0] = start / ItemMax;
          R[1] = start % ItemMax;
          if  ((R[0]  > 0) || (R[1] > 0)) {
              JSONObject NavigationButton1 = new  JSONObject();
              endl = start - 1;
              if (R[0] > 0) { startl = start - ItemMax; }
              else{ startl = start - R[1];}
              NavigationButton1.put("text","◀ "+ Dictionary.Translate("Else",UIF.getLanguage()) +String.format(" %s - %s",startl+1,endl+1));
              NavigationButton1.put("callback_data",String.format("user_list_%s_%s_%s",this.ModeArrOfStr[2],startl, endl));

              if (startl != 0) {    // Navigation Sosoo LEFT
                  JSONObject NavigationButtonSL = new JSONObject();
                  NavigationButtonSL.put("text", "\u23EA " + Dictionary.Translate("To", UIF.getLanguage()) + String.format(" %s - %s", 1, ItemMax + 1));
                  NavigationButtonSL.put("callback_data", String.format("user_list_%s_%s_%s", this.ModeArrOfStr[2], 0, ItemMax));
                  jsonArrayLine0.put(NavigationButtonSL);
              }
              jsonArrayLine0.put(NavigationButton1);
          }

     // Navigation RIGHT
          R[0] = (HawMachIs - 1 - end) / ItemMax;
          R[1] = (HawMachIs -1 - end) % ItemMax;
          if   ((R[0] > 0 ) || (R[1] > 0)){
              JSONObject NavigationButton2 = new  JSONObject();
              startr = end + 1;
              if (R[0] == 0) {
                  endr = end + R[1];
              } else {endr = end + ItemMax;}
              NavigationButton2.put("text","▶ " + Dictionary.Translate("Else",UIF.getLanguage()) + String.format(" %s - %s",startr+1,endr+1));
              NavigationButton2.put("callback_data",String.format("user_list_%s_%s_%s",this.ModeArrOfStr[2],startr, endr));
              jsonArrayLine0.put(NavigationButton2);
              //  Navigation Soso RIGHT
              if (HawMachIs != endr+1)  {
                  JSONObject NavigationButtonSR = new  JSONObject();
                  NavigationButtonSR.put("text","\u23E9" + Dictionary.Translate("To",UIF.getLanguage()) + String.format(" %s - %s",HawMachIs-ItemMax,HawMachIs));
                  NavigationButtonSR.put("callback_data",String.format("user_list_%s_%s_%s",this.ModeArrOfStr[2],HawMachIs-ItemMax-1, HawMachIs-1));
                  jsonArrayLine0.put(NavigationButtonSR);
              }
          }

     //   Main BUTTON
          for (int i = start; i < end+1; i++) {
              JSONArray jsonArrayLine = new JSONArray();
              JSONObject jsonObjButton1 = new JSONObject();
              JSONObject jsonUser = jsonList.getJSONObject(i);
              jsonObjButton1.put("text", jsonUser.getString("surname")+" "+jsonUser.getString("name")+" "+jsonUser.getString("fname"));
              jsonObjButton1.put("callback_data","user_show_"+jsonUser.get("id").toString());
              jsonArrayLine.put(jsonObjButton1);
              jsonObjMenu.append("inline_keyboard",jsonArrayLine);
          }
      }
      //

      NavigationButton0.put("text", "\uD83D\uDC48\uD83C\uDFFF "+Dictionary.Translate("Back",UIF.getLanguage()));
      NavigationButton0.put("callback_data","it");
      jsonArrayLineB.put(NavigationButton0);
      jsonObjMenu.append("inline_keyboard",jsonArrayLine0);
      jsonObjMenu.append("inline_keyboard",jsonArrayLineB);
      jsonUserList = jsonObjMenu;

      UserMenuText = Dictionary.Translate("Found",UIF.getLanguage())+": "+HawMachIs.toString()+" "+Dictionary.Translate("user(s)",UIF.getLanguage());

      jsonUserList.put("list",jsonList);
     }catch (SQLException e) { e.printStackTrace();}
      catch (JSONException e) { e.printStackTrace();}
  return jsonUserList;
 }

 public JSONObject MyItMessageList() {
   JSONObject Reet = new JSONObject();
   Integer HawMachIs;
   Integer ItemMax = 5;
   Integer start = 0;
   Integer end = 0;
   Integer startl = 0;
   Integer endl = 0;
   Integer startr = 0;
   Integer endr = 0;
   int[] R = new int[2];
   JSONObject jsonObjMenu = new JSONObject();
   JSONArray jsonArrayLine0 = new JSONArray();
   JSONObject NavigationButton0 = new  JSONObject();

   String SQLCount = "SELECT COUNT(*) FROM `ittask` WHERE (`id_user` = "+UIF.getId_user()+" and `id_chat` ="+UIF.getId_chat()+") and `del` = "+this.ModeArrOfStr[1];
   String SQLQuery ="SELECT `id`, `task`, `date_str` FROM `ittask` WHERE (`id_user` = "+UIF.getId_user()+" and `id_chat` = "+UIF.getId_chat()+") and `del` = "+this.ModeArrOfStr[1]+" ORDER BY `id`";
   try {
       DBResultSet = DBStatement.executeQuery(SQLCount);
       if (DBResultSet.getMetaData().getColumnCount() == 1) {
           DBResultSet.next();
           HawMachIs = DBResultSet.getInt("COUNT(*)");
           if (HawMachIs == 0) {
               this.UserMenuText = Dictionary.Translate("Sorry, but you have no one  task.",UIF.getLanguage());
           } else {
               DBResultSet = DBStatement.executeQuery(SQLQuery);
               JSONArray jsonArrayItTask = convert(DBResultSet);

               JSONObject NavigationButton1 = new  JSONObject();
               JSONObject NavigationButton2 = new  JSONObject();

               if (this.ModeArrOfStr.length == 2) {
                   start = 0;
                   end = HawMachIs - 1;
                   if (end > ItemMax) { end = ItemMax - 1;}
               } else {
                       start = Integer.valueOf(this.ModeArrOfStr[2]);
                       end = Integer.valueOf(this.ModeArrOfStr[3]);
               }
               if (start < 0) { start = 0; }
               if (end > HawMachIs -1) { end = HawMachIs - 1;}
               R[0] = start / ItemMax;
               R[1] = start % ItemMax;
               if  ((R[0]  > 0) || (R[1] > 0)) {
                   endl = start - 1;
                   if (R[0] > 0) { startl = start - ItemMax; }
                               else{ startl = start - R[1];}
                   NavigationButton1.put("text","◀ "+ Dictionary.Translate("Else",UIF.getLanguage()) +String.format(" %s - %s",startl+1,endl+1));
                   NavigationButton1.put("callback_data",String.format("myittasks_%s_%s_%s",this.ModeArrOfStr[1],startl, endl));
                   jsonArrayLine0.put(NavigationButton1);
               }
               R[0] = (HawMachIs - 1 - end) / ItemMax;
               R[1] = (HawMachIs -1 - end) % ItemMax;
     //          System.err.println(end);
       //        System.err.println(R[0]+" "+R[1]);
           //    if   ((ItemMax < HawMachIs) && (R[1] > 0)){
               if   ((R[0] > 0 ) || (R[1] > 0)){
                   startr = end + 1;
                   if (R[0] == 0) {
                       endr = end + R[1];
                   } else {endr = end + ItemMax;}
                   NavigationButton2.put("text","▶ " + Dictionary.Translate("Else",UIF.getLanguage()) + String.format(" %s - %s",startr+1,endr+1));
                   NavigationButton2.put("callback_data",String.format("myittasks_%s_%s_%s",this.ModeArrOfStr[1],startr, endr));
                   jsonArrayLine0.put(NavigationButton2);
                }
               for (int i = start; i < end+1; i++) {
                   JSONArray jsonArrayLine = new JSONArray();
                   JSONObject jsonObjButton1 = new JSONObject();
                   JSONObject itTask = jsonArrayItTask.getJSONObject(i);
                   jsonObjButton1.put("text", itTask.getString("task")+" "+itTask.getString("date_str"));
                   jsonObjButton1.put("callback_data","task_show_"+itTask.get("id").toString());
                   jsonArrayLine.put(jsonObjButton1);
                   jsonObjMenu.append("inline_keyboard",jsonArrayLine);
               }
           }
           NavigationButton0.put("text", "\uD83D\uDC48\uD83C\uDFFF "+Dictionary.Translate("Back",UIF.getLanguage()));
           NavigationButton0.put("callback_data","support");
           jsonArrayLine0.put(NavigationButton0);
           jsonObjMenu.append("inline_keyboard",jsonArrayLine0);
           Reet = jsonObjMenu;
           String status;
           if (this.ModeArrOfStr[1].equals("1")) {status = Dictionary.Translate("deactivated", UIF.getLanguage());
           }else { status = Dictionary.Translate("active", UIF.getLanguage());
           }
           UserMenuText = Dictionary.Translate("Found",UIF.getLanguage())+": "+HawMachIs.toString()+" "+status+" "+Dictionary.Translate("item(s)",UIF.getLanguage());
       }
   }catch (SQLException e) {
           e.printStackTrace();}
   return Reet;
 }

 public boolean AddTaskComment (String id_task) {
     Boolean Reet = false;
     String SQLQuery = "INSERT INTO `taskcomments`(`id_task`, `comment`, `date`) VALUES ("+id_task+",'"+this.MessageText+"','"+this.CurrentDateString+"')";
     try {
         if (DBStatement.executeUpdate(SQLQuery) == 1) {
            Reet = true;
         }
     } catch (SQLException e) {
         e.printStackTrace();
     }
     return Reet;
 }

 public void LogToDB()  {
    String SQLQuery = "INSERT INTO `log`(`id_user`, `id_chat`, `text_in`, `menu_in`, `text_out`, `date_str`, `date`) " +
            "VALUES ("+UIF.getId_user()+","+UIF.getId_chat()+",'"+this.MessageText+"','"+this.Data+"','"+this.MessageTextOut+"','"+this.CurrentDateString+"',"+UIF.getLast_access()+")";
    System.out.println(SQLQuery);
    try {
       if (DBStatement.executeUpdate(SQLQuery) == 1) {
           System.out.println("LOG OK");
       }
    } catch (SQLException e) {
        e.printStackTrace();
    }
 }
 public String getCurrentDateString (){  return this.CurrentDateString; }
 public void setCurrentDateString (String CurrentDateString) { this.CurrentDateString = CurrentDateString; }
 public String getMessageTextOut (){  return this.MessageTextOut; }
 public void setMessageTextOut (String MessageTextOut) { this.MessageTextOut = MessageTextOut; }
 public Long getCurrentMenuMessageId (){  return this.CurrentMenuMessageId; }
 public void setCurrentMenuMessageId (Long CurrentMenuMessageId) { this.CurrentMenuMessageId = CurrentMenuMessageId; }
 public Long getUserMessageId (){  return this.UserMessageId; }
 public void setUserMessageId (Long UserMessageId) { this.UserMessageId = UserMessageId; }

    public String toJSON(UserInfo user) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(user);
        return jsonString;
    }
}
