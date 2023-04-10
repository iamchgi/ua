package ua.mario.bot.Menu;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import ua.mario.objects.Dictionary;

public class main extends DefaultMenu {
 @Override
    public void CreateMenu () {
        MenuCaptionText = "<b>"+Dictionary.Translate("Main menu",Language)+"</b>";
        try {
            JSONArray jsonArrayLine = new JSONArray();

            JSONObject jsonObjButton1 = new JSONObject();
            JSONObject jsonObjButton2 = new JSONObject();
            JSONObject jsonObjButton3 = new JSONObject();
            JSONObject jsonObjButton4 = new JSONObject();
            JSONObject jsonObjButton5 = new JSONObject();

            jsonObjButton1.put("text", "\uD83D\uDEE0 "+Dictionary.Translate("Mode",this.Language));
            jsonObjButton1.put("callback_data","mode");
 //           jsonObjButton1.put("url","https://google.com");
            jsonArrayLine.put(jsonObjButton1);

            jsonObjButton2.put("text", "⚙ "+Dictionary.Translate("Settings",this.Language));
            jsonObjButton2.put("callback_data","settings");
            jsonArrayLine.put(jsonObjButton2);

            jsonObjButton3.put("text", "\uD83D\uDEAA "+Dictionary.Translate("Exit",this.Language));
            jsonObjButton3.put("callback_data","exit");
            jsonArrayLine.put(jsonObjButton3);

            jsonObjButton4.put("text", "⚠ "+Dictionary.Translate("info",this.Language));
            jsonObjButton4.put("callback_data","info");
            navigation0.put(jsonObjButton4);

            jsonObjButton5.put("text", "❓ "+Dictionary.Translate("help",this.Language));
            jsonObjButton5.put("callback_data","help");
            navigation0.put(jsonObjButton5);

       //     jsonArrayAll.put(jsonArrayLine);
       //     jsonArrayAll.put(jsonArrayLine);
     //       jsonObjMain.append("inline_keyboard",jsonArrayLine);
            jsonObjMenu.append("inline_keyboard",jsonArrayLine);
            jsonObjMenu.append("inline_keyboard",navigation0);
    //        jsonObjMain.put("inline_keyboard",jsonArrayAll);
            System.out.println(jsonObjMenu);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
