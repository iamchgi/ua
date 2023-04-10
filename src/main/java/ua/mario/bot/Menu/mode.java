package ua.mario.bot.Menu;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import ua.mario.objects.Dictionary;

public class mode extends DefaultMenu{
@Override
 public void CreateMenu() {
    MenuCaptionText = "<b>"+Dictionary.Translate("Mode",Language)+"</b>";
    try {
        JSONArray jsonArrayAll = new JSONArray();
        JSONArray jsonArrayLine = new JSONArray();
        JSONArray jsonArrayLine2 = new JSONArray();
    //    JSONObject jsonObjButton1 = new JSONObject();
        JSONObject jsonObjButton2 = new JSONObject();
    //    JSONObject jsonObjButton3 = new JSONObject();
        JSONObject jsonObjButton4 = new JSONObject();
        JSONObject jsonObjButton5 = new JSONObject();
        JSONObject jsonObjButton6 = new JSONObject();

    //    jsonObjButton1.put("text", "\uD83D\uDCD6\uD83D\uDD0D "+ Dictionary.Translate("Search",this.Language));
    //    jsonObjButton1.put("callback_data","usearch");
    //    jsonArrayLine.put(jsonObjButton1);

        jsonObjButton2.put("text", "\uD83D\uDDA5 "+Dictionary.Translate("IT",this.Language));
        jsonObjButton2.put("callback_data","it");
        jsonArrayLine.put(jsonObjButton2);

    //    jsonObjButton3.put("text", "\uD83C\uDF7A "+Dictionary.Translate("Chat",this.Language));
    //    jsonObjButton3.put("callback_data","chat");
    //    jsonArrayLine.put(jsonObjButton3);

        jsonObjButton4.put("text", "\uD83E\uDDD9\uD83C\uDFFF\u200D♂️ "+Dictionary.Translate("Technical support",this.Language));
        jsonObjButton4.put("callback_data","support");
        jsonArrayLine.put(jsonObjButton4);

        jsonObjButton5.put("text", "\uD83D\uDD29 "+Dictionary.Translate("User Services",this.Language));
        jsonObjButton5.put("callback_data","services");
        jsonArrayLine2.put(jsonObjButton5);

        jsonObjButton6.put("text", "\uD83D\uDC48\uD83C\uDFFF "+Dictionary.Translate("Back",this.Language));
        jsonObjButton6.put("callback_data","main");
        jsonArrayLine2.put(jsonObjButton6);

        jsonArrayAll.put(jsonArrayLine);
        jsonObjMenu.append("inline_keyboard",jsonArrayLine);
        jsonObjMenu.append("inline_keyboard",jsonArrayLine2);
        System.out.println(jsonObjMenu);
    } catch (JSONException e) {
        e.printStackTrace();
    }
 }
}
