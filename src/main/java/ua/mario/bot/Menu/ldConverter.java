package ua.mario.bot.Menu;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import ua.mario.objects.Dictionary;

public class ldConverter extends DefaultMenu{
 @Override
 public void CreateMenu() {
     MenuCaptionText = "<b>" + Dictionary.Translate("Converting Linux date.", Language) + "</b>\n";
     try {
         JSONArray jsonArrayAll = new JSONArray();
         JSONArray jsonArrayLine = new JSONArray();
         JSONObject jsonObjButton = new JSONObject();
         jsonObjButton.put("text", "\uD83D\uDC48\uD83C\uDFFF " + Dictionary.Translate("Back", this.Language));
         jsonObjButton.put("callback_data", "services");
         jsonArrayLine.put(jsonObjButton);
         jsonArrayAll.put(jsonArrayLine);
         jsonObjMenu.append("inline_keyboard", jsonArrayLine);
         System.out.println(jsonObjMenu);
     } catch (JSONException e) {
         e.printStackTrace();
     }
 }
}
