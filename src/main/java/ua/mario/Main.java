package ua.mario;

import org.json.JSONException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import org.telegram.telegrambots.updatesreceivers.DefaultWebhook;
import ua.mario.bot.Bot;
import ua.mario.bot.WBot;
import ua.mario.jdbc.GetResultSetJSON;
import ua.mario.jdbc.dbsettings;
import ua.mario.objects.Dictionary;
import ua.mario.utils.Debug;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Main {
 //   private static final Logger log = LoggerFactory.getLogger(Main.class);
    private static ua.mario.utils.Debug LOG = new Debug();
    public static void main(String[] args) {
       // log.info("App Start!!!");
        LOG.Debug("App Start.");
        for(int i = 0; i < args.length; i++) {
            System.err.println(args[i]);    }
        if (args.length > 0)
        try {
            File f = new File(args[0]);
            if (f.exists() && !f.isDirectory()) {
               //JSON parser object to parse read file
               JSONParser jsonParser = new JSONParser();
               FileReader reader = new FileReader(args[0]);
               //Read JSON file
               Object obj = jsonParser.parse(reader);
               JSONObject jsonObject =  (JSONObject) obj;
               String botName = (String) jsonObject.get("botName");
               String botToken = (String) jsonObject.get("botToken");
               String botType = (String) jsonObject.get("botType");
               String bot_function = (String) jsonObject.get("bot_function");
               String dbHost = (String) jsonObject.get("dbHost");
               String dbPort = (String) jsonObject.get("dbPort");
               String dbName = (String) jsonObject.get("dbName");
               String dbusername = (String) jsonObject.get("dbusername");
               String dbpassword = (String) jsonObject.get("dbpassword");
               Boolean DEBUGTERMINAL = (Boolean) jsonObject.get("debugterminal");
               Boolean DEBUGFILE = (Boolean) jsonObject.get("debugfile");
               String DEBUGFILENAME = (String) jsonObject.get("debugfilename");
               res.setBotData(botName, botToken,botType,bot_function ,DEBUGTERMINAL, DEBUGFILE, DEBUGFILENAME);
               dbsettings.setDBSettings(dbHost,dbPort,dbName,dbusername,dbpassword);
               LOG.Debug("Static parameters replace from file :");
               LOG.Debug(jsonObject.toString());
               dbsettings.updateDBUrl();
            } else {  LOG.Debug("Set static parametrs for bot DB :");     }
            org.json.JSONObject ResFromDB = GetResultSetJSON.GetBotResToJSON();
            LOG.Debug(ResFromDB.toString());
            if (ResFromDB.getBoolean("ok")) {
                LOG.Debug("Set BOT Res From DB");
                res.setBotData(ResFromDB.getString("botname"),ResFromDB.getString("bottoken"),ResFromDB.getString("bottype"),ResFromDB.getString("bot_function"),
                        ResFromDB.getBoolean("log_to_term"),ResFromDB.getBoolean("log_to_file"),ResFromDB.getString("log_filename"));
            }
            else { LOG.Debug("Error get Bot Res From DB !!! Static or from file still active."); }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        LOG.Debug("BOT starting whith this parametrs:");
        LOG.Debug(dbsettings.dbHost+":"+dbsettings.dbPort);
        LOG.Debug(dbsettings.dbName);
        LOG.Debug(dbsettings.dbusername+":"+dbsettings.dbpassword);
        LOG.Debug(res.botName);
        LOG.Debug(res.botToken);
        LOG.Debug(res.getBotType());
        LOG.Debug(res.getBot_Function());
        LOG.Debug(res.getDEBUGTERMINAL().toString());
        LOG.Debug(res.getDEBUGFILE().toString());
        LOG.Debug(res.getDEBUGFILENAME());

        if   (!Dictionary.GetDictionary()) {
             System.exit(1); }
        else { LOG.Debug("DB - OK"); }

        if (res.getBotType().equals("LONGPOOLING")) {
            LOG.Debug("The LONGPOOLING starting!");
            try {
                TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
                botsApi.registerBot(new Bot());
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        } else {
            LOG.Debug("The WEBHOOK!");
            try {
                DefaultWebhook webhook = new DefaultWebhook();
          //    SetWebhook setWebhook = new SetWebhook("https://bot-api.heat-point.com:443/758577117:AAGZnoqoX-iWF7ZFJvd9noe-AHTJaYU4xUg/");
              webhook.setInternalUrl("http://0.0.0.0:1445/callback/");
              WBot botik = new WBot();
              webhook.registerWebhook(botik);
          //    webhook.startServer();

               // TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class, webhook);
                TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class, webhook);
                telegramBotsApi.registerBot(botik ,new SetWebhook("https://bot-api.heat-point.com:8443"));

//                botsApi.registerBot(botik, SetWebhook.builder().url("https://bot-api.heat-point.com:443/758577117:AAGZnoqoX-iWF7ZFJvd9noe-AHTJaYU4xUg/").build());

            } catch (TelegramApiException e) {
                e.printStackTrace();
                LOG.Debug("Error !!!");
            }
        }
        LOG.Debug("Finish_Main_App");
    }
}
