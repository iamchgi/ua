package ua.mario.bot;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import ua.mario.utils.Debug;

import static ua.mario.bot.Action.Autorization;
import static ua.mario.http.messages.AdminMessage;


@NoArgsConstructor
public class Bot extends TelegramLongPollingBot {
    static private ua.mario.utils.Debug LOG = new Debug();
    final int RECONNECT_PAUSE = 10000;

    @Setter
    @Getter
    private String botName;

    @Setter
    private String botToken;

    @Override
    public void onUpdateReceived(Update update) {
        LOG.Debug(update.toString());
        if (update.hasCallbackQuery()) {
            LOG.Debug("Возвратка от МЕНЮ");
            LOG.Debug(update.getCallbackQuery().toString());
            LOG.Debug(update.getCallbackQuery().getData());
        }
        if (update.hasMessage()) {
            LOG.Debug("Возвратка ТЕХТ");
            LOG.Debug(update.getMessage().toString());
            LOG.Debug(update.getMessage().getText());
        }

        if (Autorization(update)) {
            LOG.Debug("Autorization OK !!!\n<--------------------------------------------------->");
        } else {
                 LOG.Debug("Autorization Авторизация , имхо , Errror !!!",true);

         //        UserMessage(Long.valueOf(update.getMessage().getFrom().getId()),"И чо?");

             }
             // We check if the update has a message and the message has text
       //      Message message = new Message();
       //      if (message.hasText()) {
       //         SendMessage echoMessage = new SendMessage();
       //         echoMessage.setChatId(Long.toString(message.getChatId()));
       //         echoMessage.setText("Hey, heres your message:\n" + message.getText());
       //         System.out.println(echoMessage.toString());
     //           try {
    //                 execute(echoMessage);
     //           } catch (TelegramApiException e) {
               //    BotLogger.error(LOGTAG, e);
    //            }
         //    }
    }

    public void setBotUserName (String botName) { this.botName = botName; }
    @Override
    public String getBotUsername() {
        botName = ua.mario.res.botName;
    //    botName = "mario_it_bot";
   //     botName = "mario_1c_bot";
        return botName;
    }
    public void setBotToken (String botToken) { this.botToken = botToken;    }
    @Override
    public String getBotToken() {
        botToken = ua.mario.res.botToken;
    //    botToken = "758577117:AAGZnoqoX-iWF7ZFJvd9noe-AHTJaYU4xUg";
    //    botToken = "936694173:AAGPpaNuGPsxWgsS_2J7yIvxnt79TkR96Ec";
        return botToken;
    }
}
