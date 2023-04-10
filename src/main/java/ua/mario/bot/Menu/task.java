package ua.mario.bot.Menu;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import ua.mario.objects.Dictionary;

public class task extends DefaultMenu {
    @Override
    public void CreateMenu() {
      String callback_data = "support";
      MenuCaptionText = Dictionary.Translate("TASK",Language)+"\n";
      JSONObject NewJsonObjMenu = new JSONObject();
     //   super.CreateMenu();
        System.out.println("task    "+jsonObjMenu);
        if (!jsonObjMenu.isEmpty() || jsonObjMenu.getBoolean("ok")) {
            try {
                 callback_data = jsonObjMenu.getString("callback_data");
                 JSONObject task_data = new JSONObject();
                 task_data = jsonObjMenu.getJSONObject("task_data");
                 JSONArray comments = new JSONArray();
                 comments = jsonObjMenu.getJSONArray("comments");
                 System.out.println(task_data);
                 System.out.println(comments);
                 MenuCaptionText = MenuCaptionText + "№ "+task_data.getInt("id")+"\n"+task_data.getString("task")+"\n"+task_data.getString("date_str") ;
                 if (comments.length() > 0){
                     MenuCaptionText = MenuCaptionText +"\n -------- "+Dictionary.Translate("COMMENTS FOR TASK",Language)+" ----------------------";
                     for (int i = 0; i < comments.length(); i++) {
                         JSONObject com = new JSONObject();
                         com = comments.getJSONObject(i);
                         MenuCaptionText = MenuCaptionText + "\n" + com.getString("comment") + "\n"+com.getString("date");
                     }
                 }
                JSONObject jsonObjButton1 = new JSONObject();
                JSONObject jsonObjButton2 = new JSONObject();
                JSONObject jsonObjButton3 = new JSONObject();
                JSONArray jsonArrayLine = new JSONArray();
                 if (!task_data.getBoolean("del")) {
                     if (!jsonObjMenu.getString("yesno").equals("yesno")) {
                         jsonObjButton1.put("text", "\uD83D\uDCD6\uD83D\uDD0D " + Dictionary.Translate("Edit", this.Language));
                         jsonObjButton1.put("callback_data", "task_edit_" + task_data.getInt("id"));
                         jsonArrayLine.put(jsonObjButton1);

                         jsonObjButton2.put("text", "\uD83D\uDDA5 " + Dictionary.Translate("Add comment", this.Language));
                         jsonObjButton2.put("callback_data", "task_add_" + task_data.getInt("id"));
                         jsonArrayLine.put(jsonObjButton2);

                         jsonObjButton3.put("text", "\uD83C\uDF7A " + Dictionary.Translate("Complete the task", this.Language));
                         jsonObjButton3.put("callback_data", "task_complite_" + task_data.getInt("id"));
                         jsonArrayLine.put(jsonObjButton3);
                     } else {
                         jsonObjButton1.put("text", "✅ " + Dictionary.Translate("Yes", this.Language));
                         jsonObjButton1.put("callback_data", "task_compliteyes_" + task_data.getInt("id"));
                         jsonArrayLine.put(jsonObjButton1);

                         jsonObjButton2.put("text", "\uD83D\uDE45\u200D♀️ " + Dictionary.Translate("No", this.Language));
                         jsonObjButton2.put("callback_data", callback_data);
                         jsonArrayLine.put(jsonObjButton2);
                     }

                 } else {
                     jsonObjButton1.put("text", "\uD83D\uDCD6\uD83D\uDD0D " + Dictionary.Translate("Activate agane this task", this.Language));
                     jsonObjButton1.put("callback_data", "task_activate_" + task_data.getInt("id"));
                     jsonArrayLine.put(jsonObjButton1);
                 }
                NewJsonObjMenu.append("inline_keyboard", jsonArrayLine);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        //    JSONArray jsonArrayAll = new JSONArray();

    //    JSONObject jsonObjButton4 = new JSONObject();
        JSONObject jsonObjButton7 = new JSONObject();

     //   jsonObjButton4.put("text", "\uD83E\uDD21 " + Dictionary.Translate("help", this.Language));
     //   jsonObjButton4.put("callback_data", "task_help");
     //   navigation0.put(jsonObjButton4);

        jsonObjButton7.put("text", "\uD83D\uDC48\uD83C\uDFFF " + Dictionary.Translate("Back", this.Language));
        jsonObjButton7.put("callback_data", callback_data);
        navigation0.put(jsonObjButton7);

        //     jsonArrayAll.put(jsonArrayLine);

        NewJsonObjMenu.append("inline_keyboard", navigation0);
      this.jsonObjMenu = NewJsonObjMenu;
    }
}
