package ua.mario.http;

import org.json.JSONObject;
import ua.mario.res;

import java.io.*;
import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


// "https://api.telegram.org/bot758577117:AAGZnoqoX-iWF7ZFJvd9noe-AHTJaYU4xUg/sendMessage?chat_id=-1001348818155&text="+message

public class messages {

     String parse_mode = "HTML";
     private  Long idMessage = Long.valueOf(0);
     String response;
     private  Long MessageDate = Long.valueOf(0);

     Long idUser = Long.valueOf(0);
     Long idChat = Long.valueOf(0);

    static String BaseURLSendMessage = "https://api.telegram.org/bot%s/sendMessage?chat_id=%s&text=%s&parse_mode=%s";

    static String BaseURL = "https://api.telegram.org/bot";
    static String MidlURL = "/sendMessage?chat_id=";
    static String PastURL = "&text=";

    static String Token = res.botToken;

 //   static String Token = "758577117:AAGZnoqoX-iWF7ZFJvd9noe-AHTJaYU4xUg";
    static Long idAUser = Long.valueOf("-1001348818155");

public void pinMessage (Long idChat,Long idMessage) {
 String urlString = "https://api.telegram.org/bot"+Token+"/pinChatMessage?chat_id="+idChat+"&message_id="+idMessage;
 System.err.println(urlString);

}

public Boolean SendSimplyMessageWithResponsOk (Long id_Chat, String text){
 Boolean Reet = false;
 try {
     String urlString = String.format(BaseURLSendMessage, Token, id_Chat, text, parse_mode);
     URL url = new URL(urlString);
     URLConnection conn = url.openConnection();

     StringBuilder sb = new StringBuilder();
     InputStream is = new BufferedInputStream(conn.getInputStream());
     BufferedReader br = new BufferedReader(new InputStreamReader(is));
     String inputLine = "";
     while ((inputLine = br.readLine()) != null) {
         sb.append(inputLine);
     }
     String response = sb.toString();
   //  System.err.println(response);
     JSONObject jsonResponse = new JSONObject(response);
     System.out.println(jsonResponse.getBoolean("ok"));
     Reet = jsonResponse.getBoolean("ok");
 } catch (IOException e) { e.printStackTrace(); }
 return Reet;
}

public void SendMyMessage(Long idUser, Long idChat, String text, String parse_mode){
  this.idUser = idUser;
  this.idChat = idChat;
  this.response = SendMessageM(idChat,text,this.parse_mode);
     JSONObject jsonresponse = new JSONObject(this.response.toString());
     if (jsonresponse.getBoolean("ok") == true) {
        JSONObject jsonresult;
        jsonresult = jsonresponse.getJSONObject("result");
        this.idMessage = jsonresult.getLong("message_id");
        this.MessageDate = jsonresult.getLong("date");
        System.out.println(jsonresult);
     }
}

public static String SendMessageM (Long idChat, String message,String parse_mode)  {
    String text = null;
    String response = null;
    try {
        text = URLEncoder.encode(message, "UTF-8");
    } catch (UnsupportedEncodingException e) {
        e.printStackTrace();
    }
    String urlString = String.format(BaseURLSendMessage, Token, idChat,  text, parse_mode);
    try {
         URL url = new URL(urlString);
         URLConnection conn = url.openConnection();
         StringBuilder sb = new StringBuilder();
         InputStream is = new BufferedInputStream(conn.getInputStream());
         BufferedReader br = new BufferedReader(new InputStreamReader(is));
         String inputLine = "";
         while ((inputLine = br.readLine()) != null) {
              sb.append(inputLine);
         }
    response = sb.toString();
    } catch (IOException e) {
        e.printStackTrace();
    }
 //    System.out.println(response);
    return response;
    }

 public static void UserMessageM (Long idChat,String message) {
    UserMessageM(idChat,message,"HTML");
 }

 public static void UserMessageM(Long idUser,String message,String parse_mode)  {
        SendMessageM(idUser,message,parse_mode);
 }

 public static void SendMessage (String message)  {
        HttpRequest request = null;
        try {
            request = HttpRequest.newBuilder()
                    .uri(new URI(message))
                    .GET()
                    .build();
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return;
        }
        HttpResponse response = null;
        try {
            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.err.println(response.body());
        }
 }

 public static void AdminMessage(String message)  {
    UserMessage(idAUser,message);
 }

 public static void UserMessage(Long idUser,String message)  {
  String text;
  try {
        text = URLEncoder.encode(message, "UTF-8");
      } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return;
      }
  SendMessage(BaseURL+Token+MidlURL+idUser+PastURL+text);
 }

  public static void deleteMessage (Long idChat, Long idMessage) {
      String urlString = "https://api.telegram.org/bot%s/deleteMessage?chat_id=%s&message_id=%s";
      urlString = String.format(urlString, Token, idChat, idMessage);
      HttpResponse response = null;
      HttpRequest request = null;
      try {
          request = HttpRequest.newBuilder()
                  .uri(new URI(urlString))
                  .GET()
                  .build();
      } catch (URISyntaxException e) {
          e.printStackTrace();
      }
      try {
           response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
      } catch (IOException e) {
          e.printStackTrace();
      } catch (InterruptedException e) {
          e.printStackTrace();
      }finally {
          System.err.println(response.body());
      }
  }

 public static void deleteMessageM1 (Long idChat, Long idMessage) {
        String urlString = "https://api.telegram.org/bot%s/deleteMessage?chat_id=%s&message_id=%s";
        try {
            urlString = String.format(urlString, Token, idChat, idMessage);
            URL url = new URL(urlString);
            URLConnection conn = url.openConnection();

            StringBuilder sb = new StringBuilder();
            InputStream is = new BufferedInputStream(conn.getInputStream());
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String inputLine = "";
            while ((inputLine = br.readLine()) != null) {
                sb.append(inputLine);
            }
            String response = sb.toString();
    //        System.out.println(response);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
 }

 public Long getIdMessage (){  return this.idMessage; }
 public Long getMessageDate (){  return this.MessageDate; }

}
