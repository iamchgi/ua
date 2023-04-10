package ua.mario.bot.Menu;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import ua.mario.objects.Dictionary;

public class myittasks extends DefaultMenu{
 @Override
 public void CreateMenu() {
 //       super.CreateMenu();
     MenuCaptionText = Dictionary.Translate("List of my tasks",Language)+"\n";
     if (jsonObjMenu.isEmpty()) {
         try {
             JSONArray jsonArrayAll = new JSONArray();
             JSONArray jsonArrayLine = new JSONArray();
             JSONArray jsonArrayLine2 = new JSONArray();
             JSONArray jsonArrayLine3 = new JSONArray();
             JSONObject jsonObjButton1 = new JSONObject();
             JSONObject jsonObjButton2 = new JSONObject();
             JSONObject jsonObjButton3 = new JSONObject();
             JSONObject jsonObjButton4 = new JSONObject();
             JSONObject jsonObjButton5 = new JSONObject();
             JSONObject jsonObjButton6 = new JSONObject();
             JSONObject jsonObjButton7 = new JSONObject();

             jsonObjButton1.put("text", "\uD83D\uDCD6\uD83D\uDD0D " + Dictionary.Translate("<<", this.Language));
             jsonObjButton1.put("callback_data", "myittasks_999_888_777");
             jsonArrayLine.put(jsonObjButton1);

             jsonObjButton2.put("text", "\uD83D\uDDA5 " + Dictionary.Translate("<", this.Language));
             jsonObjButton2.put("callback_data", "myittasks_0_10_10");
             jsonArrayLine.put(jsonObjButton2);

             jsonObjButton3.put("text", "\uD83C\uDF7A " + Dictionary.Translate("Del", this.Language));
             jsonObjButton3.put("callback_data", "myittasks_222_333_444");
             jsonArrayLine.put(jsonObjButton3);

             jsonObjButton4.put("text", "\uD83E\uDD21 " + Dictionary.Translate("Edit", this.Language));
             ;
             jsonObjButton4.put("callback_data", "myittasks_555_666_777");
             jsonArrayLine2.put(jsonObjButton4);

             jsonObjButton5.put("text", "\uD83C\uDF7A " + Dictionary.Translate(">", this.Language));
             jsonObjButton5.put("callback_data", "myittasks_100_100_01");
             jsonArrayLine2.put(jsonObjButton5);

             jsonObjButton6.put("text", "\uD83E\uDD21 " + Dictionary.Translate(">>", this.Language));
             ;
             jsonObjButton6.put("callback_data", "myittasks_0_2_10");
             jsonArrayLine2.put(jsonObjButton6);

             jsonObjButton7.put("text", "\uD83D\uDC48\uD83C\uDFFF " + Dictionary.Translate("Back", this.Language));
             ;
             jsonObjButton7.put("callback_data", "support");
             jsonArrayLine3.put(jsonObjButton7);

             jsonArrayAll.put(jsonArrayLine);
             jsonObjMenu.append("inline_keyboard", jsonArrayLine);
             jsonObjMenu.append("inline_keyboard", jsonArrayLine2);
             jsonObjMenu.append("inline_keyboard", jsonArrayLine3);
             System.out.println(jsonObjMenu);
         } catch (JSONException e) {
             e.printStackTrace();
         }
     }
 }
}
