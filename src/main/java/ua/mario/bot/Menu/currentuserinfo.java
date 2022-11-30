package ua.mario.bot.Menu;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import ua.mario.objects.Dictionary;

import java.util.Iterator;

public class currentuserinfo extends DefaultMenu{
 @Override
 public void CreateMenu() {
     MenuCaptionText = Dictionary.Translate("My Info",Language)+"\n";
     try {
         JSONArray jsonArrayLine = new JSONArray();
         JSONObject jsonObjButton = new JSONObject();
         jsonObjButton.put("text", "\uD83D\uDC48\uD83C\uDFFF " + Dictionary.Translate("Back", this.Language));
         jsonObjButton.put("callback_data", "settings");
         jsonArrayLine.put(jsonObjButton);
         jsonObjMenu.append("inline_keyboard", jsonArrayLine);
     } catch (JSONException e) {
         e.printStackTrace();
     }
 }
}
