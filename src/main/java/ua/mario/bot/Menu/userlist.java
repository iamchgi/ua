package ua.mario.bot.Menu;

import org.json.JSONObject;
import ua.mario.objects.Dictionary;

public class userlist extends DefaultMenu {
 @Override
 public void CreateMenu() {
  MenuCaptionText = "<b>"+Dictionary.Translate("a list of users",Language)+"</b>\n";
     if (jsonObjMenu.isEmpty()) {
      JSONObject jsonObjButton7 = new JSONObject();
      jsonObjButton7.put("text", "\uD83D\uDC48\uD83C\uDFFF " + Dictionary.Translate("Back", this.Language));
      jsonObjButton7.put("callback_data", "it");
      navigation0.put(jsonObjButton7);
      jsonObjMenu.append("inline_keyboard", navigation0);
     }
 }
}
