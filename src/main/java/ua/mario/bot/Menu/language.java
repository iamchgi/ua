package ua.mario.bot.Menu;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import ua.mario.objects.Dictionary;
import ua.mario.res;

public class language extends DefaultMenu {
@Override
  public void CreateMenu() {
    MenuCaptionText = "<b>"+Dictionary.Translate("Language",Language)+"</b>\n";
    try {
        JSONArray jsonArrayAll = new JSONArray();
        JSONArray jsonArrayLine = new JSONArray();
        JSONObject jsonObjButton1 = new JSONObject();
        JSONObject jsonObjButton2 = new JSONObject();
        JSONObject jsonObjButton3 = new JSONObject();
        JSONObject jsonObjButton4 = new JSONObject();

        jsonObjButton1.put("text", "\ud83c\uddfa\ud83c\udde6 "+ Dictionary.Translate("Ukrainian",this.Language));
        jsonObjButton1.put("callback_data","ua");
        jsonArrayLine.put(jsonObjButton1);

        jsonObjButton2.put("text", res.GetFlag("ru") + " " + Dictionary.Translate("Russian",this.Language));
        jsonObjButton2.put("callback_data","ru");
        jsonArrayLine.put(jsonObjButton2);

        jsonObjButton3.put("text", "\uD83C\uDDEC\uD83C\uDDE7 "+Dictionary.Translate("English",this.Language));
        jsonObjButton3.put("callback_data","en");
        navigation0.put(jsonObjButton3);

        jsonObjButton4.put("text", "\uD83D\uDC48\uD83C\uDFFF "+Dictionary.Translate("Back",this.Language));;
        jsonObjButton4.put("callback_data","settings");
        navigation0.put(jsonObjButton4);

        jsonArrayAll.put(jsonArrayLine);
        jsonObjMenu.append("inline_keyboard",jsonArrayLine);
        jsonObjMenu.append("inline_keyboard",navigation0);
        System.out.println(jsonObjMenu);
    } catch (JSONException e) {
        e.printStackTrace();
    }

  }
}
