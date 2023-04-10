package ua.mario.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import ua.mario.utils.Debug;

import java.io.UnsupportedEncodingException;
import java.sql.*;

import static ua.mario.http.messages.AdminMessage;
import static ua.mario.jdbc.dbsettings.*;


public class UserInfo {
    private ua.mario.utils.Debug LOG = new Debug();
    @JsonProperty("id")
    private long id;
    @JsonProperty("first_name")
    private String first_name = "";
    @JsonProperty("last_name")
    private String last_name = "";
    @JsonProperty("user_name")
    private String user_name = "";
    @JsonProperty("name")
    private String name = "";
    @JsonProperty("surname")
    private String surname = "";
    @JsonProperty("fname")
    private String fname = "";
    @JsonProperty("id_user")
    private Long id_user = Long.valueOf(0);
    @JsonProperty("id_chat")
    private Long id_chat = Long.valueOf(0);
    @JsonProperty("type")
    private String type = "";
    @JsonProperty("phone")
    private String phone = "";
    @JsonProperty("phone1")
    private String phone1 = "";
    @JsonProperty("e_mail")
    private String e_mail = "";
    @JsonProperty("int_phone")
    private Integer int_phone = 0;
    @JsonProperty("position")
    private String position = "";
    @JsonProperty("pass")
    private String pass = "202102";
    @JsonProperty("access")
    private byte access = 10;
    @JsonProperty("last_access")
    private Long last_access = Long.valueOf(0);
    @JsonProperty("req_count")
    private Long req_count = Long.valueOf(1);
    @JsonProperty("serch_ok")
    private byte serch_ok = 10;
    @JsonProperty("access_chat")
    private byte access_chat = 10;
    @JsonProperty("access_serch")
    private byte access_serch = 10;
    @JsonProperty("access_it")
    private byte access_it = 10;
    @JsonProperty("access_support")
    private byte access_support = 10;
    @JsonProperty("access_1c")
    private byte access_1c = 10;
    @JsonProperty("access_vlan")
    private byte access_vlan = 10;
    @JsonProperty("access_menu")
    private byte access_menu = 9;
    @JsonProperty("access_ip")
    private byte access_ip = 10;
    @JsonProperty("access_admin")
    private byte access_admin = 10;
    @JsonProperty("access_mat")
    private byte access_mat = 10;
    @JsonProperty("language")
    private String language = "ua";

    @JsonProperty("id")
    public void setId(Long id) {    this.id = id;   }
    @JsonProperty("first_name")
    public void setFirst_name(String first_name) {  this.first_name = first_name;  }
    @JsonProperty("last_name")
    public void setLast_name(String last_name) {  this.last_name = last_name;   }
    @JsonProperty("user_name")
    public void setUser_name(String user_name) {  this.user_name = user_name;  }
    @JsonProperty("name")
    public void setName(String name) {   this.name = name;   }
    @JsonProperty("surname")
    public void setSurname(String surname) {  this.surname = surname;   }
    @JsonProperty("fname")
    public void setFname(String fname) {  this.fname = fname;  }
    @JsonProperty("id_user")
    public void setId_user(Long id_user) {  this.id_user = id_user;  }
    @JsonProperty("id_chat")
    public void setId_chat(Long id_chat) {  this.id_chat = id_chat;  }
    @JsonProperty("type")
    public void setType(String type) {   this.type = type;   }
    @JsonProperty("phone")
    public void setPhone(String phone) {  this.phone = phone;  }
    @JsonProperty("phone1")
    public void setPhone1(String phone1) {  this.phone1 = phone1;  }
    @JsonProperty("e_mail")
    public void setE_mail(String e_mail) {  this.e_mail = e_mail;  }
    @JsonProperty("int_phone")
    public void setInt_phone(Integer int_phone) {  this.int_phone = int_phone;  }
    @JsonProperty("position")
    public void setPosition(String position) {   this.position = position;   }
    @JsonProperty("pass")
    public void setPass(String pass) {   this.pass = pass;  }
    @JsonProperty("access")
    public void setAccess(Byte access) {   this.access = access;   }
    @JsonProperty("last_access")
    public void setLast_access(Long last_access) {   this.last_access = last_access;   }
    @JsonProperty("req_count")
    public void setReq_count(Long req_count) {   this.req_count = req_count;   }
    @JsonProperty("serch_ok")
    public void setSerch_ok(Byte serch_ok) {  this.serch_ok = serch_ok;   }
    @JsonProperty("access_chat")
    public void setAccess_chat(Byte access_chat) {  this.access_chat = access_chat;  }
    @JsonProperty("access_serch")
    public void setAccess_serch(Byte access_serch) {   this.access_serch = access_serch;   }
    @JsonProperty("access_it")
    public void setAccess_it(Byte access_it) {   this.access_it = access_it;   }
    @JsonProperty("access_support")
    public void setAccess_support(Byte access_support) {   this.access_support = access_support;   }
    @JsonProperty("access_1c")
    public void setAccess_1c(Byte access_1c) {   this.access_1c = access_1c;   }
    @JsonProperty("access_vlan")
    public void setAccess_vlan(Byte access_vlan) {   this.access_vlan = access_vlan;    }
    @JsonProperty("access_menu")
    public void setAccess_menu(Byte access_menu) {   this.access_menu = access_menu;  }
    @JsonProperty("access_ip")
    public void setAccess_ip(Byte access_ip) {   this.access_ip = access_ip;  }
    @JsonProperty("access_admin")
    public void setAccess_admin(Byte access_admin) {   this.access_admin = access_admin;  }
    @JsonProperty("access_mat")
    public void setAccess_mat(Byte access_mat) {  this.access_mat = access_mat;   }
    @JsonProperty("language")
    public void setLanguage(String language) {  this.language = language;  }

    public Long getId() {  return this.id;   }
    public String getFirst_name() {  return this.first_name;   }
    public String getLast_name() {   return this.last_name;  }
    public String getUser_name() {   return this.user_name;  }
    public String getName() {  return this.name;   }
    public String getSurname() {  return this.surname;  }
    public String getFname() {  return this.fname;   }
    public Long getId_user() {  return this.id_user;   }
    public Long getId_chat() {  return this.id_chat;  }
    public String getType() {   return this.type;   }
    public String getPhone() {  return this.phone;   }
    public String getPhone1() {  return this.phone1;   }
    public String getE_mail() {  return this.e_mail;   }
    public Integer getInt_phone() {  return this.int_phone;  }
    public String getPosition() {  return this.position; }
    public String getPass() {  return this.pass;  }
    public Byte getAccess() {  return this.access;  }
    public Long getLast_access() {  return this.last_access;  }
    public Long getReq_count() {  return this.req_count;   }
    public Byte getSerch_ok() {  return this.serch_ok;   }
    public Byte getAccess_chat() {  return this.access_chat;   }
    public Byte getAccess_serch() {  return this.access_serch;  }
    public Byte getAccess_it() { return this.access_it;  }
    public Byte getAccess_support() { return this.access_support;  }
    public Byte getAccess_1c() { return this.access_1c;  }
    public Byte getAccess_vlan() { return this.access_vlan;  }
    public Byte getAccess_menu() {  return this.access_menu; }
    public Byte getAccess_ip() { return this.access_ip;  }
    public Byte getAccess_admin() { return this.access_admin; }
    public Byte getAccess_mat() {  return this.access_mat; }
    public String getLanguage() { return this.language;  }

    public boolean GetUserInfoFromDB(long idUser, long idChat) {
        String Query = "SELECT * FROM users where `id_user` = " + idUser + " and `id_chat` = " + idChat + " LIMIT 1";
        boolean Reet = false;
        try {
            Connection connection = DriverManager.getConnection(dburl, dbusername, dbpassword);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(Query);
            while (resultSet.next()) {
                setId(resultSet.getLong(1));
                setFirst_name(resultSet.getString(2));
                setLast_name(resultSet.getString(3));
                setUser_name(resultSet.getString(4));
                setName(resultSet.getString(5));
                setSurname(resultSet.getString(6));
                setFname(resultSet.getString(7));
                setId_user(resultSet.getLong(8));
                setId_chat(resultSet.getLong(9));
                setType(resultSet.getString(10));
                setPhone(resultSet.getString(11));
                setPhone1(resultSet.getString(12));
                setE_mail(resultSet.getString(13));
                setInt_phone(resultSet.getInt(14));
                setPosition(resultSet.getString(15));
                setPass(resultSet.getString(16));
                setAccess(resultSet.getByte(17));
                setLast_access(resultSet.getLong(18));
                setReq_count(resultSet.getLong(19));
                setSerch_ok(resultSet.getByte(20));
                setAccess_chat(resultSet.getByte(21));
                setAccess_serch(resultSet.getByte(22));
                setAccess_it(resultSet.getByte(23));
                setAccess_support(resultSet.getByte(24));
                setAccess_1c(resultSet.getByte("access_1c"));
                setAccess_vlan(resultSet.getByte(26));
                setAccess_menu(resultSet.getByte(27));
                setAccess_ip(resultSet.getByte(28));
                setAccess_admin(resultSet.getByte(29));
                setAccess_mat(resultSet.getByte(30));
                setLanguage(resultSet.getString(31));
                Reet = true;
            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
          }
       if (!Reet) {
          AdminMessage("Пользователь " +idUser+ " НЕ найден в БИДЕ!");
          this.id_user = idUser;
          this.id_chat = idChat;
          Reet = InsertNewUserToDB();
       }
        return Reet;
    }

 public boolean InsertNewUserToDB() {
     boolean Reet = false;
     try {
         String Query = "INSERT INTO `users` (`first_name`,`last_name`,`user_name`,`name`,`surname`,`fname`,`id_user`,`id_chat`, `type`, `phone`, `phone1`, `e_mail`, `int_phone`, `position`, `pass`, `access`, `last_access`, `req_count`, `serch_ok`, `access_chat`, `access_serch`, `access_it`,`access_support`, `access_1c`, `access_vlan`, `access_menu`, `access_ip`, `access_admin`, `access_mat`, `language`) ";
         Query = Query + "VALUES('" + getFirst_name() + "','" + getLast_name() + "','" + getUser_name() + "','" + getName() + "','" + getSurname() + "','" + getFname() + "'," + getId_user() + "," + getId_chat() + ",'" + getType() + "','" + getPhone() + "','" + getPhone1() + "','" + getE_mail() + "','" + getInt_phone() + "','" + getPosition() + "','" + getPass() + "'," + getAccess() + "," + getLast_access() + "," + getReq_count() + "," + getSerch_ok() + "," + getAccess_chat() + "," + getAccess_serch() + "," + getAccess_it() + ","+ getAccess_support() + ","+getAccess_1c()+"," + getAccess_vlan() + "," + getAccess_menu() + "," + getAccess_ip() + "," + getAccess_admin() + "," + getAccess_mat() + ",'" + getLanguage() + "');";
         //        System.out.println(Query);
         Connection connection = DriverManager.getConnection(dburl, dbusername, dbpassword);
         Statement statement = connection.createStatement();
         if (statement.executeUpdate(Query) == 1) {
             AdminMessage("Пользователь " + this.id_user + " добавлен в БИДЕ");
             Reet = true;
         }
         statement.close();
         connection.close();
     } catch (SQLException e) {
         e.printStackTrace();
     }
     return Reet;
 }

    public boolean UpdateUserInDB() {
        boolean Reet = false;
        try {
            String Query = "UPDATE `users` SET `first_name` = ?, `last_name` = ?, `user_name` = ?, `name` = ?, `surname` = ?, `fname` = ?, `type` = ?, `phone` = ?, `phone1` = ?, `e_mail` = ?, `int_phone` = ?, `position` = ?, `pass` = ?, `access` = ?, `last_access` = ?, `req_count` = ?, `serch_ok` = ?, `access_chat` = ?, `access_serch` = ?, `access_it` = ?,`access_support` = ?,`access_1c` = ?, `access_vlan` = ?, `access_menu` = ?, `access_ip` = ?, `access_admin` = ?, `access_mat` = ?, `language` = ? WHERE `users`.`id_user` = ? and `users`.`id_chat` = ?;";
            Connection connection= DriverManager.getConnection(dburl, dbusername, dbpassword);
            PreparedStatement statement = connection.prepareStatement(Query);
            statement.setString(1,getFirst_name());
            statement.setString(2,getLast_name());
            statement.setString(3,getUser_name());
            statement.setString(4,getName());
            statement.setString(5,getSurname());
            statement.setString(6,getFname());
            statement.setString(7,getType());
            statement.setString(8,getPhone());
            statement.setString(9,getPhone1());
            statement.setString(10,getE_mail());
            statement.setInt(11,getInt_phone());
            statement.setString(12,getPosition());
            statement.setString(13,getPass());
            statement.setByte(14,getAccess());
            statement.setLong(15,getLast_access());
            statement.setLong(16,getReq_count());
            statement.setByte(17,getSerch_ok());
            statement.setByte(18,getAccess_chat());
            statement.setByte(19,getAccess_serch());
            statement.setByte(20,getAccess_it());
            statement.setByte(21,getAccess_support());
            statement.setByte(22,getAccess_1c());
            statement.setByte(23,getAccess_vlan());
            statement.setByte(24,getAccess_menu());
            statement.setByte(25,getAccess_ip());
            statement.setByte(26,getAccess_admin());
            statement.setByte(27,getAccess_mat());
            statement.setString(28,getLanguage());

            statement.setLong(29,getId_user());
            statement.setLong(30,getId_chat());
            LOG.Debug("UPDATE USER INFO \n"+statement.toString());
            if (statement.executeUpdate() == 1) {
                Reet = true;
                LOG.Debug("OK");
            }
            statement.close();
            connection.close();
        } catch (SQLException e) { e.printStackTrace();   }
        return Reet;
    }


  public void onCreate() {
    System.out.println("Obj created <======================================>");
    this.id = 0;
    this.first_name = "";
    this.last_name = "";
    this.user_name = "";
    this.name = "";
    this.surname = "";
    this.fname = "";
    this.id_user = Long.valueOf(0);
    this.id_chat = Long.valueOf(0);
    this.type = "";
    this.phone = "";
    this.phone1 = "";
    this.e_mail = "";
    this.int_phone = 0;
    this.position = "";
    this.pass = "";
    this.access = 10;
    this.last_access = Long.valueOf(0);
    this.serch_ok = 10;
    this.access_chat = 10;
    this.access_serch = 10;
    this.access_it = 10;
    this.access_support = 10;
    this.access_1c = 10;
    this.access_vlan = 10;
    this.access_menu = 10;
    this.access_ip = 10;
    this.access_admin = 10;
    this.access_mat = 10;
    this.language = "ua";
    System.out.println("<=================================================>");
  }
}
