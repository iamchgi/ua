package ua.mario.bot.Menu;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import ua.mario.objects.Dictionary;

public class settings extends DefaultMenu {

@Override
  public void CreateMenu() {
        MenuCaptionText = "<b>"+Dictionary.Translate("Settings",Language)+"</b>";
   //     super.CreateMenu();
      try {
          JSONArray jsonArrayAll = new JSONArray();
          JSONArray jsonArrayLine = new JSONArray();
          JSONArray jsonArrayLine2 = new JSONArray();
          JSONObject jsonObjButton1 = new JSONObject();
          JSONObject jsonObjButton2 = new JSONObject();
          JSONObject jsonObjButton3 = new JSONObject();

          jsonObjButton1.put("text", "\uD83C\uDF0D "+ Dictionary.Translate("Language",this.Language));
          jsonObjButton1.put("callback_data","language");
          jsonArrayLine.put(jsonObjButton1);

          jsonObjButton2.put("text", "\uD83D\uDDFF "+Dictionary.Translate("My Info",this.Language));
          jsonObjButton2.put("callback_data","userinfo");
          jsonArrayLine.put(jsonObjButton2);

          jsonObjButton3.put("text", "\uD83D\uDC48\uD83C\uDFFF "+Dictionary.Translate("Back",this.Language));
          jsonObjButton3.put("callback_data","main");
          jsonArrayLine2.put(jsonObjButton3);

          jsonArrayAll.put(jsonArrayLine);

          jsonObjMenu.append("inline_keyboard",jsonArrayLine);
          jsonObjMenu.append("inline_keyboard",jsonArrayLine2);
          System.out.println(jsonObjMenu);
      } catch (JSONException e) {
          e.printStackTrace();
      }
  }
}
