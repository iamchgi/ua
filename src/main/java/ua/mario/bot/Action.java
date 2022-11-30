package ua.mario.bot;

import org.json.JSONArray;
import org.json.JSONObject;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import org.telegram.telegrambots.meta.api.methods.send.SendContact;

import ua.mario.bot.Menu.*;
import ua.mario.objects.Dictionary;
import ua.mario.utils.Debug;
import ua.mario.utils.EmailSenderSSL;
import ua.mario.utils.EmailSenderTLS;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static ua.mario.bot.SerchInDB.SerchUser;
import static ua.mario.http.messages.deleteMessage;
import static ua.mario.res.getSMessage;

import static ua.mario.jdbc.select.myselect;
import static ua.mario.jdbc.select.myselect2;
import static ua.mario.objects.Dictionary.Translate;
import static ua.mario.objects.Dictionary.getValuesForGivenKey;

public class Action {
   private static ua.mario.utils.Debug LOG = new Debug();
  public static boolean Autorization (Update update)  {
   DefaultUser CU = new DefaultUser();
   if (!CU.OpenDBConnection()){
       LOG.Debug("DB Open error !!!",true);
       return false;
   }
   long idUser = Long.valueOf(0);
   long idChat = Long.valueOf(0);
   long currendDate = Long.valueOf(0);

   if (update.hasMessage()) {
         Message message;
         message = update.getMessage();
     //    currendDate = message.getDate();
         idUser = Long.valueOf(message.getFrom().getId());
         idChat = Long.valueOf(message.getChatId());
         CU.MessageText = message.getText();
         CU.UIF.setFirst_name(message.getFrom().getFirstName());
         CU.UIF.setLast_name(message.getFrom().getLastName());
         CU.UIF.setUser_name(message.getFrom().getUserName());
         CU.setUserMessageId(Long.valueOf(message.getMessageId()));
   }  else if (update.hasCallbackQuery()) {
             CallbackQuery callbackquery;
             callbackquery = update.getCallbackQuery();
       //      idUser = Long.valueOf(callbackquery.getFrom().getId());
             idUser = Long.valueOf(callbackquery.getFrom().getId());
             idChat = Long.valueOf(callbackquery.getMessage().getChatId());
             CU.UIF.setFirst_name(callbackquery.getFrom().getFirstName());
             CU.UIF.setLast_name(callbackquery.getFrom().getLastName());
             CU.UIF.setUser_name(callbackquery.getFrom().getUserName());
             CU.Data = callbackquery.getData();
             CU.setCurrentMenuMessageId(Long.valueOf(callbackquery.getMessage().getMessageId()));
        //     currendDate = callbackquery.getMessage().getDate();
       }
   currendDate = System.currentTimeMillis()/1000L;
  ///////////   CU.UIF.setLast_access(currendDate);
   Date currentTime = new Date(currendDate*1000L);
   SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
   String dateString = formatter.format(currentTime);
   CU.setCurrentDateString(dateString);
   LOG.Debug(dateString + " <-> " + currendDate+ " ID_USER: " +idUser + " ID_CHAT: "+idChat);
   if ((idUser !=0) & (idChat != 0) & (idChat == idUser)) {
      if ((CU.UIF.GetUserInfoFromDB(idUser, idChat)) & (CU.US.getUserSessionFromDB(idUser, idChat, CU.getDBStatement()))) {
          LOG.Debug("Session "+CU.US.getMode());
   //      CU.setUserModeText();
         CU.UIF.setReq_count(CU.UIF.getReq_count() + 1);
         if (CU.getCurrentMenuMessageId() != Long.valueOf(0) & CU.getCurrentMenuMessageId() != CU.US.getId_m_m()) {
             CU.US.setId_m_m(CU.getCurrentMenuMessageId());
         }
         CU.UIF.setLast_access(currendDate);
         CU.DeleteOldMessage(currendDate);
         if (!CU.getUserMessageId().equals(Long.valueOf(0))) {
             deleteMessage(CU.UIF.getId_chat(),CU.getUserMessageId());
         }
         if ((CU.UIF.getAccess() <= 10) & (CU.UIF.getAccess() > 0)  ) {           //  Глобальный доступ !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
            if (CU.MessageText != "") {
                ParseMessageText(CU);}
            else if (CU.Data != "") {
                    CU.ParseDataString();
                    ParseMenu(CU);
                 } else {
                          CU.DUMessage.UserMessage(CU.UIF.getId_user(), Translate("And what are we going to do?", CU.UIF.getLanguage()));
                        }
         } else {
                CU.DUMessage.UserMessage(CU.UIF.getId_user(), Translate("Good unknown time of day.", CU.UIF.getLanguage()) + " \n А доступа у Вас - нет \n " + Translate("Contact the Administrator", CU.UIF.getLanguage()) + ".");
                }
             CU.UIF.UpdateUserInDB();
             CU.US.updateUserSessionInDB(CU.getDBConnection());
      } else {
              LOG.Debug("DB Autorization error !!!",true);
      }
   } else { LOG.Debug("Not private CHAT, empty message or other Error !!!",true); }
   CU.LogToDB();
   if (!CU.CloseDBConnection()){  LOG.Debug("DB Close error !!!",true); return false; }
   return true;
 }

 public static boolean ParseMenu (DefaultUser BOT) {
     LOG.Debug("menu <-> " + BOT.Data + " <->"+BOT.ModeArrOfStr[0]);
     if (BOT.UIF.getAccess_menu() == 9)
     switch (BOT.ModeArrOfStr[0]) {
         case "main":
             main mMain = new main();
             mMain.EditMenuCaption(BOT.UIF.getLanguage(),BOT.UIF.getId_chat(),BOT.UIF.getId_user(),BOT.US.getId_m_m(),BOT.getUserMenuText());
             break;
         case "settings":
             settings mSettings = new settings();
             mSettings.EditMenuCaption(BOT.UIF.getLanguage(),BOT.UIF.getId_chat(),BOT.UIF.getId_user(),BOT.US.getId_m_m(),BOT.getUserMenuText());
             break;
         case "mode":
             mode mMode = new mode();
             mMode.EditMenuCaption(BOT.UIF.getLanguage(),BOT.UIF.getId_chat(),BOT.UIF.getId_user(),BOT.US.getId_m_m(),BOT.getUserMenuText());
             break;
         case "services":
             BOT.US.setModeText("");
             services mServices = new services();
             mServices.EditMenuCaption(BOT.UIF.getLanguage(),BOT.UIF.getId_chat(),BOT.UIF.getId_user(),BOT.US.getId_m_m(),BOT.getUserMenuText());
             break;
         case "exit":
             deleteMessage(BOT.UIF.getId_chat(),BOT.US.getId_m_m());
             BOT.US.setId_m_m((long) 0);
             break;
         case "help":
             BOT.SendMessage("❓ "+ getSMessage("help",BOT.UIF.getLanguage())+" !!!","HTML",3600);
             break;
         case "info":
             BOT.SendMessage("⚠ "+ getSMessage("info",BOT.UIF.getLanguage()),"HTML",3600);
             break;
         case "language":
             BOT.setUserMenuText("<i>"+Dictionary.Translate("Choose the bot language.",BOT.UIF.getLanguage())+"</i>");
             language mLanguage = new language();
             mLanguage.EditMenuCaption(BOT.UIF.getLanguage(),BOT.UIF.getId_chat(),BOT.UIF.getId_user(),BOT.US.getId_m_m(),BOT.getUserMenuText());
             break;
         case "en":
         case "ru":
         case "ua":
             System.out.println(BOT.ModeArrOfStr[0]);
             if (!BOT.UIF.getLanguage().equals(BOT.ModeArrOfStr[0])) {
                 BOT.UIF.setLanguage(BOT.ModeArrOfStr[0]);
                 BOT.setUserMenuText("<i>"+Dictionary.Translate("Choose the bot language.",BOT.UIF.getLanguage())+"</i>");
                 BOT.UIF.UpdateUserInDB();
                 language mLanguage2 = new language();
                 mLanguage2.EditMenuCaption(BOT.UIF.getLanguage(),BOT.UIF.getId_chat(),BOT.UIF.getId_user(),BOT.US.getId_m_m(),BOT.getUserMenuText());
              } else {
                 BOT.SendMessage("\uD83E\uDD26\uD83C\uDFFB\u200D♀️ " + Dictionary.Translate("Why do you press in vain?",BOT.UIF.getLanguage())+"\n"+
                         Translate( "I already speak English language.",BOT.UIF.getLanguage()),"HTML",1);
             }
             break;
         case "myittasks":
             BOT.US.setMode(BOT.Data);
             JSONObject JJ = BOT.MyItMessageList();
             myittasks mMuItTasks = new myittasks();
             mMuItTasks.setJsonObjMenu(JJ);
             mMuItTasks.EditMenuCaption(BOT.UIF.getLanguage(),BOT.UIF.getId_chat(),BOT.UIF.getId_user(),BOT.US.getId_m_m(),BOT.getUserMenuText());
             break;
         case "sem":
             switch (BOT.ModeArrOfStr[1]) {
                 case "search":
                     if (BOT.UIF.getAccess_serch() == 9) {  // Access to search
                         String  ToEmSearch = SerchUser(BOT.US.getOld_massage_text(), BOT.UIF.getLanguage(), (byte) 5);
                         if (!ToEmSearch.isEmpty()) {
                            EmailSenderTLS tlsSender = new EmailSenderTLS("telegram@mario.ua", "telegunakatateasy$");
                            EmailSenderSSL sslSender = new EmailSenderSSL("telgram@mario.ua", "telegunakatateasy$");
                            if (tlsSender.send("Result of search by " + BOT.US.getOld_massage_text(), ToEmSearch, "telegram@mario.ua", BOT.UIF.getE_mail())) {
                               BOT.SendMessage("mess was send to "+BOT.UIF.getE_mail(),"HTML",60);
                            } else {
                                BOT.DUMessage.AdminMessage("e-mail error to it"+BOT.UIF.getE_mail());
                            }
                         sslSender.send("Result of search by " + BOT.US.getOld_massage_text(), ToEmSearch, "telgram@mario.ua", BOT.UIF.getE_mail());
                         }
                     }
                 break;
                 default:
                     LOG.Debug("Nothing parametr to send by e-mail",true);
                 break;
             }
             break;
         case "1c":
             if (BOT.UIF.getAccess_1c() != 9) { break; }
             switch (BOT.ModeArrOfStr[1]) {
                 case "main":
                     BOT.US.setModeText(BOT.Data);
                     main1c mMain1c = new main1c();
                     mMain1c.EditMenuCaption(BOT.UIF.getLanguage(),BOT.UIF.getId_chat(),BOT.UIF.getId_user(),BOT.US.getId_m_m(),BOT.getUserMenuText());
                     break;
                 default:
                     //  1c #2 unknown
                     BOT.SendMessage("\uD83D\uDEE0 "+ "1c" +Translate("This functionality is under construction",BOT.UIF.getLanguage())+" !!!","HTML",60);
                 break;
             }
             break;
         case "task":
             if (BOT.UIF.getAccess_support() != 9) { break; }
             switch (BOT.ModeArrOfStr[1]) {
                 case "show":
                 if (BOT.ModeArrOfStr[1].equals("show")) {
                     task mTask = new task();
                     JSONObject Temp = new JSONObject();
                     Temp = BOT.ItTaskShow();
                     Temp.put("callback_data",BOT.US.getMode());
                     mTask.setJsonObjMenu(Temp);
                     mTask.EditMenuCaption(BOT.UIF.getLanguage(), BOT.UIF.getId_chat(), BOT.UIF.getId_user(), BOT.US.getId_m_m(), "");
                 }
                 break;
                 case "edit":
                   BOT.US.setModeText(BOT.Data);
                   BOT.US.updateUserSessionInDB(BOT.getDBConnection());
                   BOT.SendMessage("Enter updatet task"+"№ "+BOT.US.getModeArrOfStr(2)+"bellow","HTML",1);
                 break;
                 case "new":
                     if (!BOT.US.getMode().equals(BOT.Data)) {
                         BOT.US.setModeText(BOT.Data);
                         BOT.SendMessage(Dictionary.Translate("Enter text of new task",BOT.UIF.getLanguage()),"HTML",1);
                         //   BOT.setUserModeText();
                         //     support mSupport = new support();
                         //     mSupport.EditMenuCaption(BOT.UIF.getLanguage(),BOT.UIF.getId_chat(),BOT.UIF.getId_user(),BOT.US.getId_m_m(),BOT.getUserModeText());
                     } else {
                         BOT.SendMessage("\uD83E\uDD26\uD83C\uDFFB\u200D♀️ " + Dictionary.Translate("Why do you press in vain?",BOT.UIF.getLanguage())+"\n"+
                                 Translate("Write down task or choise other mode.",BOT.UIF.getLanguage()),"HTML",1);
                     }
                 break;
                 case "complite":
                     task mTask = new task();
                     JSONObject Temp = new JSONObject();
                     Temp = BOT.ItTaskShow();
                     Temp.put("callback_data","task_show_"+BOT.ModeArrOfStr[2]);
                     Temp.put("yesno","yesno");
                     mTask.setJsonObjMenu(Temp);
                     mTask.EditMenuCaption(BOT.UIF.getLanguage(),BOT.UIF.getId_chat(),BOT.UIF.getId_user(),BOT.US.getId_m_m(),"");
                 break;
                 case "compliteyes":
                     if (BOT.TaskStatus(Integer.valueOf(BOT.ModeArrOfStr[2]),true)) {
                         BOT.Data = BOT.US.getMode();
                         BOT.ParseDataString();
                         ParseMenu(BOT);
                         BOT.SendMessage(Dictionary.Translate("TASK Complited", BOT.UIF.getLanguage()), "HTML", 1);
                     }
                 break;
                 case "activate":
                     if (BOT.TaskStatus(Integer.valueOf(BOT.ModeArrOfStr[2]),false)) {
                         BOT.Data = "task_show_"+Integer.valueOf(BOT.ModeArrOfStr[2]);
                         BOT.ParseDataString();
                         ParseMenu(BOT);
                         BOT.SendMessage(Dictionary.Translate("TASK Activated", BOT.UIF.getLanguage()), "HTML", 1);
                     }
                 break;
                 case "add":
                     if (!BOT.US.getMode().equals(BOT.Data)) {
                         System.out.println("NEW TASK Comment");
                         BOT.US.setModeText(BOT.Data);
                         BOT.SendMessage(Dictionary.Translate("Enter comment for task", BOT.UIF.getLanguage())+" № " + BOT.ModeArrOfStr[2], "HTML", 1);
                     } else {
                           BOT.SendMessage("\uD83E\uDD26\uD83C\uDFFB\u200D♀️ " + Dictionary.Translate("Why do you press in vain?",BOT.UIF.getLanguage())+"\n"+
                           Translate("Write down task or choise other mode.",BOT.UIF.getLanguage()),"HTML",1);
                     }
                     break;
                 default:
                     //  task #2 unknown
                    break;
             }
             break;
         case "export":
             if (BOT.UIF.getAccess_mat() == 9)
             switch (BOT.ModeArrOfStr[1]) {
                 case "GB" :
                       BOT.SendMessage("https://t.me/i_am_gringo","HTML",0);
                 break;
                 case "PL" :
                     BOT.SendMessage("@i_am_gringo","HTML",0);
                 break;
                 case "IT" :
                 case "ES" :
                     BOT.SendMessage("https://t.me/i_am_gringo","HTML",0);
                 break;
                 case "ELSE" :
                 default:
                     SendContact sndCon = new SendContact();
                     sndCon.setChatId("645111741");


                     BOT.SendMessage("@i_am_gringo","HTML",0);
                 break;
             }
             break;
         case "it":
             it mIt = new it();
             mIt.EditMenuCaption(BOT.UIF.getLanguage(),BOT.UIF.getId_chat(),BOT.UIF.getId_user(),BOT.US.getId_m_m(),BOT.getUserMenuText());
             break;
         case "user":
             if (BOT.UIF.getAccess_admin() == 9){
               switch (BOT.ModeArrOfStr[1]) {
                 case "list":
                   userlist mUserlist = new userlist();
                   mUserlist.setJsonObjMenu(BOT.getUserList());
                   mUserlist.EditMenuCaption(BOT.UIF.getLanguage(), BOT.UIF.getId_chat(), BOT.UIF.getId_user(), BOT.US.getId_m_m(), BOT.getUserMenuText());
                   BOT.US.setMode(BOT.Data);
                 break;
                 case "show":
                 case "full":
                       user mUser = new user();
                       mUser.setJsonObjMenuSourse(BOT.getUserInfo().put("show",BOT.ModeArrOfStr[1]));
                       mUser.EditMenuCaption(BOT.UIF.getLanguage(), BOT.UIF.getId_chat(), BOT.UIF.getId_user(), BOT.US.getId_m_m(), BOT.getUserMenuText());
                 break;
                 case "sndmsg":
                       BOT.US.setModeText(BOT.Data);
                       BOT.SendMessage(Dictionary.Translate("Enter message text for user",BOT.UIF.getLanguage())+" "+BOT.getUserNameById(Long.valueOf(BOT.ModeArrOfStr[2])),"HTML",30);
                 break;
                 default:
                     System.err.println("eRROr user mode unknown");
                 break;
               }
             } else { BOT.SendMessage("Огооо","HTML",1);}
         break;
         case "support":
             support mSupport = new support();
             mSupport.EditMenuCaption(BOT.UIF.getLanguage(),BOT.UIF.getId_chat(),BOT.UIF.getId_user(),BOT.US.getId_m_m(),BOT.getUserMenuText());
             break;
         case "ldconverter":
             if (!BOT.US.getModeText().equals(BOT.Data)) {
                 BOT.US.setModeText(BOT.Data);
                 ldConverter mldConverter = new ldConverter();
                 mldConverter.EditMenuCaption(BOT.UIF.getLanguage(),BOT.UIF.getId_chat(),BOT.UIF.getId_user(),BOT.US.getId_m_m(),BOT.getUserMenuText());
                 BOT.SendMessage("\uD83D\uDC47 "+Dictionary.Translate("Enter correct Linux date",BOT.UIF.getLanguage()),"HTML",1);
              //   BOT.setUserModeText();
              //   it mIt1 = new it();
              //   mIt1.EditMenuCaption(BOT.UIF.getLanguage(), BOT.UIF.getId_chat(), BOT.UIF.getId_user(), BOT.US.getId_m_m(), BOT.getUserModeText());
             } else {
                 BOT.SendMessage("\uD83E\uDD26\uD83C\uDFFB\u200D♀️ " + Dictionary.Translate("Why do you press in vain?",BOT.UIF.getLanguage())+"\n"+
                         Translate("Input Linux date or choise other mode",BOT.UIF.getLanguage())+"!!!","HTML",1);
             }
             break;
         case "userinfo":
                currentuserinfo mCurrentUserInfo = new currentuserinfo();
                mCurrentUserInfo.EditMenuCaption(BOT.UIF.getLanguage(),BOT.UIF.getId_chat(),BOT.UIF.getId_user(),BOT.US.getId_m_m(),BOT.CurrentUserInfo());
             break;
         case "usearch":
             if (!BOT.US.getModeText().equals(BOT.Data)) {
                 BOT.US.setModeText(BOT.Data);
                 searchInDB mSearchInDB = new searchInDB();
                 mSearchInDB.EditMenuCaption(BOT.UIF.getLanguage(),BOT.UIF.getId_chat(),BOT.UIF.getId_user(),BOT.US.getId_m_m(),BOT.getUserMenuText());
                 BOT.SendMessage("\uD83D\uDC47 "+Dictionary.Translate("Enter search parametrs",BOT.UIF.getLanguage()),"HTML",1);
               //  BOT.setUserModeText();
               //  mode mModeE = new mode();
               //  mModeE.EditMenuCaption(BOT.UIF.getLanguage(), BOT.UIF.getId_chat(), BOT.UIF.getId_user(), BOT.US.getId_m_m(), BOT.getUserModeText());
             } else {
                 BOT.SendMessage("\uD83E\uDD26\uD83C\uDFFB\u200D♀️ "+ Dictionary.Translate("Why do you press in vain?",BOT.UIF.getLanguage())+"\n"+
                         Translate("Do set search parametrs or choise other mode",BOT.UIF.getLanguage())+"!!!","HTML",1);
             }
             break;
         default:
             BOT.SendMessage("\uD83D\uDEE0 "+ Translate("This functionality is under construction",BOT.UIF.getLanguage())+" !!!","HTML",60);
             break;
     }
     return true;
 }

 public static boolean ParseMessageText (DefaultUser BOT) {
   LOG.Debug("Parse TEXT: "+BOT.MessageText);
   switch (BOT.MessageText) {
                 case "/start":

                 case "/region"  :
                     if (BOT.UIF.getAccess() == 10) {
                         BOT.US.setMode("export");
                         export mExport = new export();
                         if (mExport.DrowMenu("en",BOT.UIF.getId_chat(), BOT.UIF.getId_user(), BOT.US.getId_m_m(), BOT.getUserMenuText()) != Long.valueOf(0)) {
                             BOT.US.setId_m_m(mExport.getIdNewMenuMessage());
                         }
                      break;
                     }
                 case "/menu":
                     BOT.US.setMode("main");
                     main mMain = new main();
                     if (mMain.DrowMenu(BOT.UIF.getLanguage(), BOT.UIF.getId_chat(), BOT.UIF.getId_user(), BOT.US.getId_m_m(), BOT.getUserMenuText()) != Long.valueOf(0)) {
                         BOT.US.setId_m_m(mMain.getIdNewMenuMessage());
                     }
                     break;
                 case "/help":
                     BOT.SendMessage( "❓ " + getSMessage("help", BOT.UIF.getLanguage()) + " !!!", "HTML", 3600);
                     break;
                 case "/info":
                     BOT.SendMessage( "⚠ " + getSMessage("info", BOT.UIF.getLanguage()) + " !!!", "HTML", 3600);
                     break;
                 case "/stop":
                     if (BOT.UIF.getAccess_admin() == 9) {
                         BOT.DUMessage.UserMessage(BOT.UIF.getId_user(), Translate("Taken to action", BOT.UIF.getLanguage()) + " !!!");
                         System.exit(0);
                     } else {
                         System.out.println("Forbiten");
                         BOT.DUMessage.UserMessage(BOT.UIF.getId_user(), Translate("Yeah of course", BOT.UIF.getLanguage()) + " !!!");
                     }
                     break;
                 case "/":
                     JSONObject jj = new JSONObject();
                     JSONArray ja;
                     ja = Dictionary.SQLDictionary;
                     System.out.println(ja);
                     jj.put("ferma", ja);
                     System.out.println(jj);
                     System.out.println(jj.getJSONArray("ferma"));
                     List<String> ls;
                     ls = getValuesForGivenKey(ja, "ru");
                     System.out.println(ls);
                     System.out.println(getValuesForGivenKey(ja, "en"));
                     System.out.println(getValuesForGivenKey(ja, "ua"));
                     break;
                 case "/language":
                     language mLanguage = new language();
                     if (mLanguage.DrowMenu(BOT.UIF.getLanguage(), BOT.UIF.getId_chat(), BOT.UIF.getId_user(), BOT.US.getId_m_m(), BOT.getUserMenuText()) != Long.valueOf(0)) {
                         BOT.US.setId_m_m(mLanguage.getIdNewMenuMessage());
                     }
                     break;
                 case "/settings":
                     settings mSettings = new settings();
                  //   mSettings.EditMenuCaption(BOT.UIF.getLanguage(),BOT.UIF.getId_chat(),BOT.UIF.getId_user(),BOT.US.getId_m_m(),BOT.getUserMenuText());
                     if ( mSettings.DrowMenu(BOT.UIF.getLanguage(),BOT.UIF.getId_chat(),BOT.UIF.getId_user(),BOT.US.getId_m_m(),BOT.getUserMenuText()) != Long.valueOf(0)) {
                         BOT.US.setId_m_m(mSettings.getIdNewMenuMessage());
                     }
                     break;
                 case "/hm":
                     helpmenu mHelpMenu = new helpmenu();
                     if (mHelpMenu.DrowMenu(BOT.UIF.getLanguage(), BOT.UIF.getId_chat(), BOT.UIF.getId_user(), BOT.US.getId_m_m(), BOT.getUserMenuText()) != Long.valueOf(0)) {
                         BOT.US.setId_m_m(mHelpMenu.getIdNewMenuMessage());
                     }
                     break;
                 case "/p":
                     BOT.DUMessage.pinMessage(BOT.UIF.getId_chat(),Long.valueOf(123));
                 break;
                 case "/sql":
                     myselect();
                     System.out.println("Finish_1");
                     myselect2();
                     System.out.println("Finish_2");
                     System.out.println("Sunday");
                     break;
                 default:
                     if (BOT.UIF.getAccess() != 9) {       // резервный контроль доступа
                         BOT.SendMessage( "\uD83D\uDEE0 " + Translate("I'm sorry, but you do not have access to serch service", BOT.UIF.getLanguage()) + " !!!", "HTML", 60);
                     } else {
                         System.out.println("ModeSessionText: "+BOT.US.getModeText());
                         switch (BOT.US.getModeTextArrOfStr(0)) {
                             case "usearch":
                                 if (!BOT.MessageText.equals(BOT.US.getOld_massage_text())){   //   Asked again nefig
                                 String tt = "";
                                 if (BOT.UIF.getAccess_serch() == 9) {                  // Access to search
                                     tt = SerchUser(BOT.MessageText, BOT.UIF.getLanguage(), (byte) 20);
                   //                  System.out.println(tt);
                                     searchInDB mSearchInDB = new searchInDB();
                                     mSearchInDB.EditMenuCaption(BOT.UIF.getLanguage(),BOT.UIF.getId_chat(),BOT.UIF.getId_user(),BOT.US.getId_m_m(),tt);
                                     BOT.SendMessage("\uD83D\uDC47 "+Dictionary.Translate("Enter search parametrs",BOT.UIF.getLanguage()),"HTML",1);
                              //       BOT.DUMessage.UserMessageM(BOT.UIF.getId_user(), Dictionary.Translate("Enter search parametrs",BOT.UIF.getLanguage()));
                                     System.out.println("serch - ok");
                                 } else {
                                     BOT.SendMessage( "☣ " + Translate("I'm sorry, but you do not have access to serch service", BOT.UIF.getLanguage()) + " !!!", "HTML", 60);
                                 }
                                 } else {
                                     BOT.SendMessage("\uD83E\uDD26\uD83C\uDFFB\u200D♀️ "+Dictionary.Translate("You alredy asked , whith this parametrs",BOT.UIF.getLanguage()),"HTML",1);
                                 }
                                 break;
                             case "ldconverter":
                                 if (!BOT.MessageText.equals(BOT.US.getOld_massage_text())) {   //   Asked again nefig
                                     String dateString = null;
                                     try {
                                         Date currentTime = new Date(Long.valueOf(BOT.MessageText) * 1000L);
                                         SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                         dateString = formatter1.format(currentTime);
                                     } catch (Exception e) {
                                         BOT.SendMessage("\uD83E\uDD26\uD83C\uDFFB\u200D♀️ " + Dictionary.Translate("Number input only", BOT.UIF.getLanguage()), "HTML", 1);
                                         e.printStackTrace();
                                     } finally {
                                         BOT.SendMessage("\uD83D\uDC47 " + Dictionary.Translate("Enter correct Linux date", BOT.UIF.getLanguage()), "HTML", 1);
                                     }
                                     if (!dateString.isEmpty()) {
                                         ldConverter mldConverter = new ldConverter();
                                         mldConverter.EditMenuCaption(BOT.UIF.getLanguage(), BOT.UIF.getId_chat(), BOT.UIF.getId_user(), BOT.US.getId_m_m(), BOT.MessageText + " -- " + dateString.toString());
                                     }
                                 } else {
                                     BOT.SendMessage("\uD83E\uDD26\uD83C\uDFFB\u200D♀️ "+Dictionary.Translate("You alredy asked , whith this parametrs",BOT.UIF.getLanguage()),"HTML",1);
                                     BOT.SendMessage("\uD83D\uDC47 " + Dictionary.Translate("Enter correct Linux date", BOT.UIF.getLanguage()), "HTML", 1);
                                 }
                                 break;
                             case "task":
                                 if (BOT.UIF.getAccess_support() == 9) {
                                     switch (BOT.US.getModeTextArrOfStr(1)) {
                                         case "new":
                                           if (BOT.NewITTask()) {
                                               BOT.US.setMode("main");
                                               //       support mSupport = new support();
                                               //       mSupport.EditMenuCaption(BOT.UIF.getLanguage(),BOT.UIF.getId_chat(),BOT.UIF.getId_user(),BOT.US.getId_m_m(),BOT.getUserModeText());
                                               BOT.SendMessage("TASK \n " + BOT.MessageText + "\n поставлена !!!", "HTML", 30);
                                           }
                                           BOT.US.setModeText("demo");
                                         break;
                                         case "edit":
                                             System.out.println(BOT.US.getModeTextArrOfStr(2));
                                         break;
                                         case "add":
                                             if (BOT.AddTaskComment(BOT.US.getModeArrOfStr(2))) {
                                                 BOT.SendMessage("\uD83D\uDD16 " + Translate("Comment added for task", BOT.UIF.getLanguage())+" № " + BOT.US.getModeArrOfStr(2), "HTML", 60);
                                                 System.out.println(BOT.US.getModeTextArrOfStr(2));
                                                 BOT.US.setMode("main");
                                                 BOT.Data = "task_show_"+BOT.US.getModeTextArrOfStr(2);
                                                 BOT.ParseDataString();
                                                 ParseMenu(BOT);
                                             }
                                             break;
                                     default:
                                         break;
                                     }
                                 } else {
                                     BOT.SendMessage("☢ " + Translate("I'm sorry, but you do not have access to support service", BOT.UIF.getLanguage()) + " !!!", "HTML", 60);
                                 }
                                 break;
                             case "user":
                                 switch (BOT.US.getModeTextArrOfStr(1)) {
                                     case "sndmsg":
                                        if (BOT.DUMessage.SendSimplyMessageWithResponsOk(Long.valueOf(BOT.US.getModeTextArrOfStr(2)),BOT.MessageText)) {
                                            BOT.SendMessage("mess send ok","HTML",1);
                                        } else { BOT.DUMessage.AdminMessage("private message to user "+ Long.valueOf(BOT.US.getModeTextArrOfStr(2)) +" error !!!"); }
                                    //     BOT.DUMessage.SendMyMessage(Long.valueOf(BOT.US.getModeTextArrOfStr(2)),Long.valueOf(BOT.US.getModeTextArrOfStr(2)),BOT.MessageText,"HTML");
                                     break;
                                     default:
                                         System.err.println("eEERrr for user case");
                                     break;
                                 }
                             break;
                             default:
                                if  (BOT.US.getMode().equals("demo") || BOT.US.getMode().equals("main")) {
                                    BOT.SendMessage(Translate("Chose mode",BOT.UIF.getLanguage()), "HTML", 20);
                                } else {
                                    BOT.DUMessage.AdminMessage("Mode unknown !!! "+BOT.US.getMode());
                                }
                                 break;
                         }
                     }
   }
   BOT.US.setOld_message_text(BOT.MessageText);
   return true;
 }
}
