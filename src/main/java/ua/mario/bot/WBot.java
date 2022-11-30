package ua.mario.bot;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

/**
 *
 */
@RestController
public class WBot extends TelegramWebhookBot {

    @Override
    public String getBotUsername() {
        return "@mario_it_bot";
    }

    @Override
    public String getBotToken() {
        return "758577117:AAGZnoqoX-iWF7ZFJvd9noe-AHTJaYU4xUg";
    }

  //  @Override
  //  public void onRegister() {
 //       System.out.println("on Регистер____________");
 //   }
  @PostMapping(value = "/webhook")
    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
        System.out.println(update.getMessage());
        if (update.hasMessage()) {
            Message message = update.getMessage();
            // We check if the update has a message and the message has text
            if (message.hasText()) {
                SendMessage echoMessage = new SendMessage();
                echoMessage.setChatId(Long.toString(message.getChatId()));
                //           echoMessage.setChatId("645111741");
                echoMessage.setText("Hey heres your\n wh \n message:\n" + message.getText());

                try {
                    execute(echoMessage);
                } catch (TelegramApiException e) {
                    // BotLogger.error(LOGTAG, e);
                }
            } else {
                SendMessage echoMessage1 = new SendMessage();
                echoMessage1.setChatId(Long.toString(message.getChatId()));
                //           echoMessage.setChatId("645111741");
                echoMessage1.setText("Hey heres your\n wh \n message:\n" + message.getText());
                try {
                    execute(echoMessage1);
                } catch (TelegramApiException e) {
                    // BotLogger.error(LOGTAG, e);
                }

            }
            System.out.println("mess"+message.getText());
        }
        return null;
    }

    @Override
    public String getBotPath() {
        return "758577117:AAGZnoqoX-iWF7ZFJvd9noe-AHTJaYU4xUg";
    }

}
