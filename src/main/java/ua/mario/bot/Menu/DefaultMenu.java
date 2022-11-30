package ua.mario.bot.Menu;

import ua.mario.utils.Debug;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import ua.mario.objects.Dictionary;
import ua.mario.res;
import ua.mario.utils.Debug;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import static ua.mario.http.messages.deleteMessage;

public class DefaultMenu {
    Long idNewMenuMessage = Long.valueOf(0);
    Long idOldMenuMessage = Long.valueOf(0);
    Long idUser = Long.valueOf(0);
    Long idChat = Long.valueOf(0);
    private ua.mario.utils.Debug LOG = new Debug();

    static String BaseURL = "https://api.telegram.org/bot";
    static String MidlURL = "/sendMessage?chat_id=";
    static String PastURL = "&text=";
    static String Token = "758577117:AAGZnoqoX-iWF7ZFJvd9noe-AHTJaYU4xUg";

    static String MenuCaptionText;
    static String UserMenuText;
    JSONObject jsonObjMenu = new JSONObject();
    JSONObject jsonObjMenuSourse = new JSONObject();
    JSONArray navigation0 = new JSONArray();
    String Language = "ua";

 public Long DrowMenu (String Language,Long idChat,Long idUser,Long id_M_M,String UserModeText) {
     setLanguage(Language);
     setIdChat(idChat);
     setIdUser(idUser);
     setIdOldMenuMessage(id_M_M);
     setUserModeText(UserModeText);
     LOG.Debug("Drow NEW MENU "+Language+" "+idChat+" "+idUser+" "+id_M_M+" "+UserModeText);
     CreateMenu();
     idNewMenuMessage = SendMenu();
     return idNewMenuMessage;
 }
 public void EditMenuCaption (String Language,Long idChat,Long idUser,Long id_M_M,String UserModeText) {
     setLanguage(Language);
     setIdChat(idChat);
     setIdUser(idUser);
     setUserModeText(UserModeText);
     CreateMenu();
     String urlString = "https://api.telegram.org/bot%s/editMessageText?chat_id=%s&text=%s&parse_mode=%s&message_id=%s&reply_markup=%s";
     try {
         urlString = String.format(urlString, Token, this.idChat, res.GetFlag(this.Language)+" "+URLEncoder.encode(this.MenuCaptionText+this.UserMenuText, "UTF-8"), "HTML",id_M_M,jsonObjMenu.toString());
         URL url = new URL(urlString);
         URLConnection conn = url.openConnection();
         StringBuilder sb = new StringBuilder();
         InputStream is = new BufferedInputStream(conn.getInputStream());
         BufferedReader br = new BufferedReader(new InputStreamReader(is));
         String inputLine = "";
         while ((inputLine = br.readLine()) != null) {
             sb.append(inputLine);
         }
         String response = sb.toString();
              LOG.Debug("обновляем menu: "+response);
     } catch (MalformedURLException e) {
         e.printStackTrace();
     } catch (IOException e) {
         e.printStackTrace();
     }
 }

   public void CreateMenu () {    }

//  URLEncoder.encode(urlString, "UTF-8") URLEncoder.encode("\n", "UTF-8") %3A %0A %20
    public Long SendMenu ()  {
        Long message_Id = Long.valueOf(0);
        String urlString = "https://api.telegram.org/bot%s/sendMessage?chat_id=%s&text=%s&parse_mode=%s&reply_markup=%s";
        try {
      //      urlString = String.format(urlString, Token, this.idChat, res.GetFlag(this.Language)+" "+URLEncoder.encode(this.MenuCaptionText+this.UserMenuText, "UTF-8"), "HTML",jsonObjMenu.toString());
            urlString = String.format(urlString, Token, this.idChat, URLEncoder.encode(this.MenuCaptionText, "UTF-8"), "HTML",URLEncoder.encode(jsonObjMenu.toString(), "UTF-8"));

            URL url = new URL(urlString);
            System.err.println(url);
            URLConnection conn = url.openConnection();
            StringBuilder sb = new StringBuilder();
            InputStream is = new BufferedInputStream(conn.getInputStream());
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String inputLine = "";
            while ((inputLine = br.readLine()) != null) {
                sb.append(inputLine);
            }
            String response = sb.toString();
            LOG.Debug("Вешаем меню: "+response);
            JSONObject jsonresponse = new JSONObject(response.toString());
            if (jsonresponse.getBoolean("ok") == true) {
                JSONObject jsonresult;
                jsonresult = jsonresponse.getJSONObject("result");
                message_Id = jsonresult.getLong("message_id");
                setIdNewMenuMessage(message_Id);
                DelOldMenu();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return message_Id;
    }

    public void DelOldMenu ()  {
        if (getIdOldMenuMessage() != Long.valueOf(0)) {
            deleteMessage(this.idChat,getIdOldMenuMessage());
        }
    }

    public Long getIdNewMenuMessage() {  return this.idNewMenuMessage;   }
    public void setIdNewMenuMessage (Long idUser) {   this.idNewMenuMessage = idUser;   }

    public Long getIdOldMenuMessage() { return this.idOldMenuMessage; }
    public void setIdOldMenuMessage (Long id_m_m) {  this.idOldMenuMessage = id_m_m; }

    public Long getIdUser() {  return this.idUser;   }
    public void setIdUser (Long idUser) {   this.idUser = idUser;   }

    public Long getIdChat() {  return this.idChat;   }
    public void setIdChat (Long idChat) {   this.idChat = idChat;   }

    public String getLanguage() {  return this.Language;   }
    public void setLanguage (String Language) {   this.Language = Language;   }

    public String getUserModeText() {  return this.UserMenuText;   }
    public void setUserModeText (String UserModeText) {
      //  this.UserModeText = "<b>"+Dictionary.Translate("Current mode", this.Language) + ":</b> " + Dictionary.Translate(UserModeText, this.Language);
     this.UserMenuText = UserModeText;
    }
    public JSONObject getJsonObjMenu() {
     return this.jsonObjMenu;
    }
    public void setJsonObjMenu(JSONObject jsonObjMenu) {
        this.jsonObjMenu = jsonObjMenu;
    }

    public JSONObject getJsonObjMenuSourse() {
        return this.jsonObjMenuSourse;
    }
    public void setJsonObjMenuSourse(JSONObject jsonObjMenuSourse) {
        this.jsonObjMenuSourse = jsonObjMenuSourse;
    }
}
