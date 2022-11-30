package ua.mario.bot.Menu;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import ua.mario.objects.Dictionary;

public class main1c extends DefaultMenu {
    @Override
    public void CreateMenu() {
        //    super.CreateMenu();
        MenuCaptionText = "<b>"+ Dictionary.Translate("1c",Language)+"</b>";
        try {
            JSONArray jsonArrayAll = new JSONArray();
            JSONArray jsonArrayLine = new JSONArray();
            JSONArray jsonArrayLine2 = new JSONArray();
            JSONObject jsonObjButton1 = new JSONObject();
            JSONObject jsonObjButton2 = new JSONObject();
            JSONObject jsonObjButton3 = new JSONObject();
            JSONObject jsonObjButton6 = new JSONObject();

            jsonObjButton1.put("text", "\uD83D\uDCD6\uD83D\uDD0D 1c "+ Dictionary.Translate("Search",this.Language));
            jsonObjButton1.put("callback_data","1c_search");
            jsonArrayLine.put(jsonObjButton1);

            jsonObjButton2.put("text","☢ 1c " + Dictionary.Translate("XZ",this.Language));
            jsonObjButton2.put("callback_data","1c_XZ");
            jsonArrayLine.put(jsonObjButton2);

            jsonObjButton3.put("text","☢ 1c " + Dictionary.Translate("else",this.Language));
            jsonObjButton3.put("callback_data","1с_else");
            jsonArrayLine.put(jsonObjButton3);

            jsonObjButton6.put("text", "\uD83D\uDC48\uD83C\uDFFF "+Dictionary.Translate("Back",this.Language));
            jsonObjButton6.put("callback_data","services");
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
