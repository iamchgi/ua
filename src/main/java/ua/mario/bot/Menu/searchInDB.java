package ua.mario.bot.Menu;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import ua.mario.objects.Dictionary;

public class searchInDB extends DefaultMenu{
 @Override
public void CreateMenu() {
     MenuCaptionText = "<b>" + Dictionary.Translate("Search", Language) + "</b>\n";
     try {
         JSONArray jsonArrayLine = new JSONArray();
         JSONArray jsonArrayLine1 = new JSONArray();
         JSONObject jsonObjButton = new JSONObject();
         if (!this.UserMenuText.isEmpty()){
             JSONObject jsonObjButton1 = new JSONObject();
             jsonObjButton1.put("text", "\uD83D\uDCE7 " + Dictionary.Translate("Send to my e-mail", this.Language));
             jsonObjButton1.put("callback_data", "sem_search");
             jsonArrayLine1.put(jsonObjButton1);
             jsonObjMenu.append("inline_keyboard", jsonArrayLine1);
         }
         jsonObjButton.put("text", "\uD83D\uDC48\uD83C\uDFFF " + Dictionary.Translate("Back", this.Language));
         jsonObjButton.put("callback_data", "services");
         jsonArrayLine.put(jsonObjButton);

         jsonObjMenu.append("inline_keyboard", jsonArrayLine);
     } catch (JSONException e) {
         e.printStackTrace();
     }
}
}
