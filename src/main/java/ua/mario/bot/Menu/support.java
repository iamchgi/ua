package ua.mario.bot.Menu;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import ua.mario.objects.Dictionary;

public class support extends DefaultMenu{
    @Override
    public void CreateMenu() {
     //   super.CreateMenu();
        MenuCaptionText = "<b>"+Dictionary.Translate("Technical support",Language)+"</b>";
        try {
            //     JSONArray jsonArrayAll = new JSONArray();
            JSONArray jsonArrayLine = new JSONArray();
            JSONArray jsonArrayLine2 = new JSONArray();
            JSONObject jsonObjButton1 = new JSONObject();
            JSONObject jsonObjButton2 = new JSONObject();
            JSONObject jsonObjButton3 = new JSONObject();
            JSONObject jsonObjButton4 = new JSONObject();

            jsonObjButton1.put("text", "\uD83D\uDCDD "+ Dictionary.Translate("new task",this.Language));
            jsonObjButton1.put("callback_data","task_new");
            //           jsonObjButton1.put("url","https://google.com");
            jsonArrayLine.put(jsonObjButton1);

            jsonObjButton2.put("text", "\uD83D\uDCC3 "+Dictionary.Translate("my active tasks",this.Language));
            jsonObjButton2.put("callback_data","myittasks_0");
            jsonArrayLine.put(jsonObjButton2);

            jsonObjButton3.put("text", "\uD83D\uDDDE "+Dictionary.Translate("my old tasks",this.Language));
            jsonObjButton3.put("callback_data","myittasks_1");
            jsonArrayLine2.put(jsonObjButton3);

            jsonObjButton4.put("text", "\uD83D\uDC48\uD83C\uDFFF "+Dictionary.Translate("Back",this.Language));
            jsonObjButton4.put("callback_data","mode");
            jsonArrayLine2.put(jsonObjButton4);
            //     jsonArrayAll.put(jsonArrayLine);
            //     jsonArrayAll.put(jsonArrayLine);
            //       jsonObjMain.append("inline_keyboard",jsonArrayLine);
            jsonObjMenu.append("inline_keyboard",jsonArrayLine);
            jsonObjMenu.append("inline_keyboard",jsonArrayLine2);
            //        jsonObjMain.put("inline_keyboard",jsonArrayAll);
            System.out.println(jsonObjMenu);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
