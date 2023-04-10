package ua.mario.bot.Menu;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import ua.mario.objects.Dictionary;

public class helpmenu extends DefaultMenu{
 @Override
 public void CreateMenu() {
     MenuCaptionText = "Help Menu";
     try {
         JSONArray jsonArrayAll = new JSONArray();
         JSONArray jsonArrayLine = new JSONArray();
         JSONArray jsonArrayLine2 = new JSONArray();
         JSONObject jsonObjButton1 = new JSONObject();
         JSONObject jsonObjButton2 = new JSONObject();
         JSONObject jsonObjButton3 = new JSONObject();
         JSONObject jsonObjButton4 = new JSONObject();

         jsonObjButton1.put("text",Dictionary.Translate("H1",this.Language));
         jsonArrayLine.put(jsonObjButton1);

         jsonObjButton2.put("text", Dictionary.Translate("H2",this.Language));
         jsonArrayLine.put(jsonObjButton2);

     //    jsonObjButton3.put("text", "\uD83C\uDF7A "+Dictionary.Translate("H33",this.Language));
     //    jsonArrayLine.put(jsonObjButton3);

         jsonObjButton4.put("text", "\uD83D\uDC48\uD83C\uDFFF "+Dictionary.Translate("Back",this.Language));;
         jsonArrayLine2.put(jsonObjButton4);

         jsonArrayAll.put(jsonArrayLine);
    //     jsonObjMenu.put("resize_keyboard",true);
   //      jsonObjMenu.put("one_time_keyboard",true);
         jsonObjMenu.put("remove_keyboard",true);
   //      jsonObjMenu.append("keyboard",jsonArrayLine);
     //    jsonObjMenu.append("keyboard",jsonArrayLine2);
         System.out.println(jsonObjMenu);
     } catch (JSONException e) {
         e.printStackTrace();
     }
    }
}
