package ua.mario.bot.Menu;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import ua.mario.objects.Dictionary;

import java.util.Iterator;

public class user extends DefaultMenu{
    @Override
    public void CreateMenu() {
    //    super.CreateMenu();
        MenuCaptionText = Dictionary.Translate("User Info",Language)+"\n";
        JSONArray jsonArrayLine = new JSONArray();
        try {
            if (!jsonObjMenuSourse.isEmpty()) {
                JSONObject jsonObjButton1 = new JSONObject();
                JSONObject jsonObjButton2 = new JSONObject();

                JSONObject user_info = new JSONObject();
                user_info = jsonObjMenuSourse.getJSONObject("user_info");

                jsonObjButton1.put("text", "✉ " + Dictionary.Translate("Send message", this.Language));
                jsonObjButton1.put("callback_data", "user_sndmsg_" + user_info.getLong("id_user"));
                jsonArrayLine.put(jsonObjButton1);

                MenuCaptionText = MenuCaptionText + "<b>" + Dictionary.Translate("Name",Language) + "</b> : " + user_info.getString("name") + "\n";
                MenuCaptionText = MenuCaptionText + "<b>" + Dictionary.Translate("Patronymic",Language) + "</b> : " + user_info.getString("fname") + "\n";
                MenuCaptionText = MenuCaptionText + "<b>" + Dictionary.Translate("Surname",Language) + "</b> : " + user_info.getString("surname");

                if (jsonObjMenuSourse.getString("show").equals("show")) {

                    jsonObjButton2.put("text", "\uD83D\uDDC2 " + Dictionary.Translate("Full info", this.Language));
                    jsonObjButton2.put("callback_data", "user_full_" + user_info.getLong("id"));
                    jsonArrayLine.put(jsonObjButton2);
                } else {
                    jsonObjButton2.put("text", "\uD83D\uDDC2 " + Dictionary.Translate("Short info", this.Language));
                    jsonObjButton2.put("callback_data", "user_show_" + user_info.getLong("id"));
                    jsonArrayLine.put(jsonObjButton2);

                    MenuCaptionText = MenuCaptionText + "\n<b>" + Dictionary.Translate("User ID",Language) + "</b> : " + user_info.getLong("id_user") + "\n";
                    MenuCaptionText = MenuCaptionText + "<b>" + Dictionary.Translate("Chat ID",Language) + "</b> : " + user_info.getLong("id_chat") + "\n";
                    MenuCaptionText = MenuCaptionText + "<b>" + Dictionary.Translate("Phone",Language) + "</b> : " + user_info.getString("phone") + "\n";
                    MenuCaptionText = MenuCaptionText + "<b>" + Dictionary.Translate("E-mail",Language) + "</b> : " + user_info.getString("e_mail") + "\n";
                    MenuCaptionText = MenuCaptionText + "<b>" + Dictionary.Translate("Internal phone",Language) + "</b> : " + user_info.getString("int_phone") + "\n";
                    MenuCaptionText = MenuCaptionText + "<b>" + Dictionary.Translate("Position",Language) + "</b> : " + user_info.getString("position") + "\n";
                    MenuCaptionText = MenuCaptionText + "<b>" + Dictionary.Translate("Language",Language) + "</b> : " + user_info.getString("language") + "\n";
                    MenuCaptionText = MenuCaptionText + "<b>" + Dictionary.Translate("Access 1",Language) + "</b> : " + user_info.getInt("access") + "\n";
                    MenuCaptionText = MenuCaptionText + "<b>" + Dictionary.Translate("Access to search",Language) + "</b> : " + user_info.getInt("access_serch") + "\n";
                    MenuCaptionText = MenuCaptionText + "<b>" + Dictionary.Translate("Access to IT",Language) + "</b> : " + user_info.getInt("access_it") + "\n";
                    MenuCaptionText = MenuCaptionText + "<b>" + Dictionary.Translate("Access to support",Language) + "</b> : " + user_info.getInt("access_support") + "\n";
                    MenuCaptionText = MenuCaptionText + "<b>" + Dictionary.Translate("Access count",Language) + "</b> : " + user_info.getLong("req_count") + "\n";
                    MenuCaptionText = MenuCaptionText + "<b>" + Dictionary.Translate("DB item actived",Language) + "</b> : " + user_info.getInt("serch_ok") + "\n";
                    MenuCaptionText = MenuCaptionText + "<b>" + Dictionary.Translate("T name",Language) + "</b> : " + user_info.getString("first_name") + "\n";
                    MenuCaptionText = MenuCaptionText + "<b>" + Dictionary.Translate("T last name",Language) + "</b> : " + user_info.getString("last_name") + "\n";
                    MenuCaptionText = MenuCaptionText + "<b>" + Dictionary.Translate("T user name",Language) + "</b> : " + user_info.getString("user_name") + "\n";
                    MenuCaptionText = MenuCaptionText + "<b>" + Dictionary.Translate("Type",Language) + "</b> : " + user_info.getString("type") + "\n";
                    MenuCaptionText = MenuCaptionText + "<b>" + Dictionary.Translate("Phone 1",Language) + "</b> : " + user_info.getString("phone1") + "\n";
                    MenuCaptionText = MenuCaptionText + "<b>" + Dictionary.Translate("Last access",Language) + "</b> : " + user_info.getLong("last_access") + "\n";
                    MenuCaptionText = MenuCaptionText + "<b>" + Dictionary.Translate("pass",Language) + "</b> : " + user_info.getString("pass") + "\n";
                    MenuCaptionText = MenuCaptionText + "<b>" + Dictionary.Translate("DB id",Language) + "</b> : " + user_info.getLong("id") + "\n";
                    MenuCaptionText = MenuCaptionText + "----------SESSION---------\n";
                    JSONObject session = new JSONObject();
                    session = jsonObjMenuSourse.getJSONObject("session");
                    if (!session.isEmpty()) {
                        MenuCaptionText = MenuCaptionText + "<b>" + Dictionary.Translate("DB Session id", Language) + "</b> : " + session.getLong("id") + "\n";
                        MenuCaptionText = MenuCaptionText + "<b>" + Dictionary.Translate("Mode", Language) + "</b> : " + session.getString("mode") + "\n";
                        MenuCaptionText = MenuCaptionText + "<b>" + Dictionary.Translate("ModeText", Language) + "</b> : " + session.getString("modetext") + "\n";
                        MenuCaptionText = MenuCaptionText + "<b>" + Dictionary.Translate("ID Menu Message", Language) + "</b> : " + session.getLong("id_m_m") + "\n";
                        MenuCaptionText = MenuCaptionText + "<b>" + Dictionary.Translate("ID ", Language) + "</b> : " + session.getLong("id_m_s") + "\n";
                        MenuCaptionText = MenuCaptionText + "<b>" + Dictionary.Translate("Old message text", Language) + "</b> : " + session.getString("old_message_text") + "\n";
                    }
                    MenuCaptionText = MenuCaptionText + "--------------------------\n";
    //                Iterator<String> iterator = jsonObjMenu.keys();
    //            iterator = user_info.keys();
    //            while (iterator.hasNext()) {
    //                String key = iterator.next();
    //                String value = user_info.get(key).toString();
    //                MenuCaptionText = MenuCaptionText + "<b>" + key + "</b> : " + value + "\n";
    //            }

   //             iterator = session.keys();
    //            while (iterator.hasNext()) {
    //                String key = iterator.next();
     //               String value = session.get(key).toString();
     //               MenuCaptionText = MenuCaptionText + "<b>" + key + "</b> : " + value + "\n";
      //          }
                }
            }
        }catch (JSONException e) { e.printStackTrace(); }


        JSONObject jsonObjButton7 = new JSONObject();
        jsonObjButton7.put("text", "\uD83D\uDC48\uD83C\uDFFF " + Dictionary.Translate("Back", this.Language));
        jsonObjButton7.put("callback_data", jsonObjMenuSourse.getString("callback_data"));
        navigation0.put(jsonObjButton7);
        jsonObjMenu.append("inline_keyboard",jsonArrayLine);
        jsonObjMenu.append("inline_keyboard", navigation0);
    }
}
