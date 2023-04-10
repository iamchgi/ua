package ua.mario.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import ua.mario.utils.Debug;

import java.sql.*;

import static ua.mario.http.messages.AdminMessage;
import static ua.mario.jdbc.dbsettings.*;

public class UserSession {
    private ua.mario.utils.Debug LOG = new Debug();
    private String[] ModeArrOfStr;
    private String[] ModeTextArrOfStr;

    @JsonProperty("id")
    private long id;
    @JsonProperty("id_user")
    private Long id_user = Long.valueOf(0);
    @JsonProperty("id_chat")
    private Long id_chat = Long.valueOf(0);
    @JsonProperty("mode")
    private String mode = "demo";
    @JsonProperty("modetext")
    private String modetext = "";
    @JsonProperty("id_m_m")
    private Long id_m_m = Long.valueOf(0);
    @JsonProperty("old_message_text")
    private String old_message_text = "";

    @JsonProperty("id")
    public void setId(Long id) { this.id = id; }
    @JsonProperty("id_user")
    public void setId_user(Long id_user) {   this.id_user = id_user;   }
    @JsonProperty("id_chat")
    public void setId_chat(Long id_chat) {  this.id_chat = id_chat;  }
    @JsonProperty("mode")
    public void setMode(String mode) { this.mode = mode; }
    @JsonProperty("modetext")
    public void setModeText(String modetext) { this.modetext = modetext; }
    @JsonProperty("id_m_m")
    public void setId_m_m(Long id_m_m) { this.id_m_m = id_m_m; }
    @JsonProperty("old_message_text")
    public void setOld_message_text(String old_message_text) { this.old_message_text = old_message_text; }

    public Long getId() { return this.id;   }
    public Long getId_user() {  return this.id_user;    }
    public Long getId_chat() { return this.id_chat;   }
    public String getMode() {   return this.mode;   }
    public String getModeText() {   return this.modetext;   }
    public Long getId_m_m() {   return this.id_m_m;   }
    public String getOld_massage_text() {   return this.old_message_text;   }

    public String getModeArrOfStr(Integer i) {  return this.ModeArrOfStr[i];    }
    public String getModeTextArrOfStr(Integer i) {   return this.ModeTextArrOfStr[i];  }

    public boolean getUserSessionFromDB(Long idUser, Long idChat,Statement DBStatement){
        String Query = "SELECT `id`,`id_user`,`id_chat`,`mode`,`modetext`, `id_m_m`, `old_message_text` FROM `sessions` WHERE `id_user` = "+idUser+" and `id_chat` = "+ idChat +" LIMIT 1";
        boolean Reet = false;
        try {
        //    Connection connection= DriverManager.getConnection(dburl, dbusername, dbpassword);
        //    Statement statement=connection.createStatement();
            ResultSet resultSet=DBStatement.executeQuery(Query);
            while (resultSet.next()){
               setId(resultSet.getLong(1));
               setId_user(resultSet.getLong(2));
               setId_chat(resultSet.getLong(3));
               setMode(resultSet.getString(4));
               setModeText(resultSet.getString(5));
               this.ModeArrOfStr = this.mode.split("_", 5);
               this.ModeTextArrOfStr = this.modetext.split("_", 5);
               setId_m_m(resultSet.getLong(6));
               setOld_message_text(resultSet.getString(7));
               Reet = true;
            }
            resultSet.close();
       //     statement.close();
       //     connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (!Reet) {
           this.id_user = idUser;
           this.id_chat = idChat;
           AdminMessage("Пользователь " +this.id_user+ " НЕ найден в Session!");
           Reet = insertUserSessionInDB(DBStatement);
        }
        return Reet;
    }

    public boolean insertUserSessionInDB(Statement DBStatement){
        boolean Reet = false;
        try {
            String Query = "INSERT INTO `sessions` (`id_user`,`id_chat`, `mode`,`modetext`,`old_message_text`) ";
            Query = Query + "VALUES('"+this.id_user+"','"+this.id_chat+"','"+this.mode+"','"+this.modetext+"','"+this.old_message_text+"')";
                    System.out.println(Query);
      //      Connection connection= DriverManager.getConnection(dburl, dbusername, dbpassword);
       //   Statement statement=connection.createStatement();
            if (DBStatement.executeUpdate(Query) == 1) {
                AdminMessage("Пользователь " + this.id_user + " добавлен в Session");
                Reet = true; }
        //    statement.close();
       //     connection.close();
        } catch (SQLException e) { e.printStackTrace();   }
        return Reet;
    }

    public boolean updateUserSessionInDB(Connection DBConnection){
        boolean Reet = false;
        try {
            String Query = "UPDATE `sessions` SET  `mode` = ? ,`modetext` = ? ,`id_m_m`= ? ,`old_message_text`= ? WHERE `sessions`.`id_user` = ? and `sessions`.`id_chat` = ?;";
        //    System.out.println(Query);
        //    Connection connection= DriverManager.getConnection(dburl, dbusername, dbpassword);
            PreparedStatement statement = DBConnection.prepareStatement(Query);
            statement.setString(1,this.mode);
            statement.setString(2,this.modetext);
            statement.setLong(3,this.id_m_m);
            statement.setString(4,this.old_message_text);
            statement.setLong(5,this.id_user);
            statement.setLong(6,this.id_chat);
            LOG.Debug("UPDATE USER SESSION \n"+statement.toString());
            if (statement.executeUpdate() == 1) {
                Reet = true;
                LOG.Debug("OK");
            }
            statement.close();
        //    connection.close();
        } catch (SQLException e) { e.printStackTrace();   }
        return Reet;
    }
}
