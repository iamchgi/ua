package ua.mario.bot.Menu;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import ua.mario.objects.Dictionary;

public class it extends DefaultMenu {
@Override
 public void CreateMenu() {
  //      super.CreateMenu();
 MenuCaptionText = "<b>"+Dictionary.Translate("IT",Language)+"</b>\n";
 try {
      JSONArray jsonArrayAll = new JSONArray();
      JSONArray jsonArrayLine = new JSONArray();
      JSONArray jsonArrayLine2 = new JSONArray();
   //   JSONObject jsonObjButton1 = new JSONObject();
      JSONObject jsonObjButton2 = new JSONObject();
  //    JSONObject jsonObjButton3 = new JSONObject();
      JSONObject jsonObjButton4 = new JSONObject();

     //   jsonObjButton1.put("text","☢ " + Dictionary.Translate("L D Converter",this.Language));
     //   jsonObjButton1.put("callback_data","ldconverter");
     //   jsonArrayLine.put(jsonObjButton1);

        jsonObjButton2.put("text", "\uD83D\uDDA5 "+Dictionary.Translate("a list of users",this.Language));
        jsonObjButton2.put("callback_data","user_list_1");
        jsonArrayLine.put(jsonObjButton2);

       // jsonObjButton3.put("text", "\uD83C\uDF7A "+Dictionary.Translate("IT3",this.Language));
     //   jsonObjButton3.put("callback_data","it3");
    //    jsonArrayLine.put(jsonObjButton3);

        jsonObjButton4.put("text", "\uD83D\uDC48\uD83C\uDFFF "+Dictionary.Translate("Back",this.Language));;
        jsonObjButton4.put("callback_data","mode");
        jsonArrayLine2.put(jsonObjButton4);

        jsonArrayAll.put(jsonArrayLine);
        jsonObjMenu.append("inline_keyboard",jsonArrayLine);
        jsonObjMenu.append("inline_keyboard",jsonArrayLine2);
        System.out.println(jsonObjMenu);
    } catch (JSONException e) {
        e.printStackTrace();
    }

 }
}
