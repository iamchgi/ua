package ua.mario.bot.Menu;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import ua.mario.objects.Dictionary;

public class export extends DefaultMenu{
    @Override
    public void CreateMenu() {
            //    super.CreateMenu();
            MenuCaptionText = "<b>"+ Dictionary.Translate("Choise your region",Language)+"</b>";
            try {
                JSONArray jsonArrayAll = new JSONArray();
                JSONArray jsonArrayLine = new JSONArray();
                JSONArray jsonArrayLine1 = new JSONArray();
                JSONArray jsonArrayLine2 = new JSONArray();
                JSONArray jsonArrayLine3 = new JSONArray();

                JSONArray jsonArrayLineL = new JSONArray();

                JSONObject jsonObjButton1 = new JSONObject();
                JSONObject jsonObjButton2 = new JSONObject();
                JSONObject jsonObjButton3 = new JSONObject();
                JSONObject jsonObjButton4 = new JSONObject();

                JSONObject jsonObjButtonL = new JSONObject();

                jsonObjButton1.put("text", "\uD83C\uDDEC\uD83C\uDDE7 "+ Dictionary.Translate("Great Britain",this.Language));
                jsonObjButton1.put("callback_data","export_GB");
                jsonArrayLine.put(jsonObjButton1);

                jsonObjButton2.put("text","\uD83C\uDDF5\uD83C\uDDF1 " + Dictionary.Translate("Poland",this.Language));
                jsonObjButton2.put("callback_data","export_PL");
                jsonArrayLine1.put(jsonObjButton2);

                jsonObjButton3.put("text","\uD83C\uDDEA\uD83C\uDDF8 " + Dictionary.Translate("Espania",this.Language));
                jsonObjButton3.put("callback_data","export_ES");
                jsonArrayLine2.put(jsonObjButton3);

                jsonObjButton4.put("text", "\uD83C\uDDEE\uD83C\uDDF9 "+Dictionary.Translate("Italy",this.Language));
                jsonObjButton4.put("callback_data","export_IT");
                jsonArrayLine3.put(jsonObjButton4);

                jsonObjButtonL.put("text", "\uD83C\uDDFA\uD83C\uDDF3 "+Dictionary.Translate("Else",this.Language));
                jsonObjButtonL.put("callback_data","export_ELSE");
                jsonArrayLineL.put(jsonObjButtonL);

                jsonArrayAll.put(jsonArrayLine);
                jsonObjMenu.append("inline_keyboard",jsonArrayLine);
                jsonObjMenu.append("inline_keyboard",jsonArrayLine1);
                jsonObjMenu.append("inline_keyboard",jsonArrayLine2);
                jsonObjMenu.append("inline_keyboard",jsonArrayLine3);
                jsonObjMenu.append("inline_keyboard",jsonArrayLineL);
                System.out.println(jsonObjMenu);
            } catch (JSONException e) {
                e.printStackTrace();
            }
    }
}
