package ua.mario;

public class res {

   static String US = "\uD83C\uDDFA\uD83C\uDDF2";
 //  static String RU = "\uD83C\uDDF7\uD83C\uDDFA";
   static String RU = "\uD83D\uDCA9";
   static String EN = "\uD83C\uDDEC\uD83C\uDDE7";
   static String EU = "\uD83C\uDDEA\uD83C\uDDFA";
   static String UA = "\ud83c\uddfa\ud83c\udde6";
   static String AU = "\uD83C\uDDE6\uD83C\uDDF9";
   static String AZ = "\uD83C\uDDE6\uD83C\uDDFF";
   static String IL = "\uD83C\uDDEE\uD83C\uDDF1";
   static String ES = "\uD83C\uDDEA\uD83C\uDDF8";
   static String UN = "\uD83C\uDDFA\uD83C\uDDF3";
   static String IT = "\uD83C\uDDEE\uD83C\uDDF9";
   static String MD = "\uD83C\uDDF2\uD83C\uDDE9";
   static String PL = "\uD83C\uDDF5\uD83C\uDDF1";

   static String Heart = "❤";
   static String Love = "ℒℴѵℯ❤";

    //  public static String bot2Name = "mario_1c_bot";
    //  public static String bot2Token = "936694173:AAGPpaNuGPsxWgsS_2J7yIvxnt79TkR96Ec";

    public static String botName = "mario_info_bot";
    public static String botToken = "658807816:AAH-y1ZdKRWOKQBsjBq-KDgqJhKHaQqnyQs";

    //   public static String botName = "mario_it_bot";
 //   public static String botToken = "758577117:AAGZnoqoX-iWF7ZFJvd9noe-AHTJaYU4xUg";
    private static String botType = "LONGPOOLING";
    private static String bot_Function = "all";
    private static Boolean DEBUGTERMINAL = true;
    private static Boolean DEBUGFILE = true;
    private static String DEBUGFILENAME = "/bots/data/"+botName+".log";

    public static String getBotType() { return botType; }
    public static String getBot_Function() { return bot_Function; }
    public static Boolean getDEBUGTERMINAL() { return DEBUGTERMINAL; }
    public static Boolean getDEBUGFILE() { return DEBUGFILE; }
    public static String getDEBUGFILENAME() { return DEBUGFILENAME; }

   public static void setBotData (String name,String token,String BT,String BF,Boolean DT,Boolean DF,String DFN) {
       botName = name;
       botToken = token;
       botType = BT;
       bot_Function = BF;
       DEBUGTERMINAL = DT;
       DEBUGFILE = DF;
       DEBUGFILENAME = DFN;
   }

   public static String GetFlag(String Language) {
       String flag;
       switch (Language) {
           case "ua":
               flag = UA;
               break;
           case "en":
               flag = EN;
               break;
           case "us":
               flag = US;
               break;
           case "ru":
               flag = RU;
               break;
           default:
               flag = EU;
               break;
       }
       return flag;
   }
 public static String getSMessage(String Type, String Language) {
  String text = "";
  switch (Type) {
      case "help":
       switch (Language) {
           case "ua":
             text = "Допомога. \n " +
                     "Для того щоб обрати функціанал \n" +
                     "використовуйте меню. \n" +
                     "На данний час доступні такі функції:\n" +
                     "Пошук - пошук персональних данних робітників;\n" +
                     "Л.Д.К - перетворення дати формату\n Лінукс в звичайний формат;\n" +
                     "Бот на данний час\n" +
                     " знаходиться в розробці.";
           break;
           case "ru":
             text = "Для того чтобы выбрать функционал,\n" +
                     "используйте меню\n" +
                     "бот на данный момент находится \n" +
                     "в стадии разработки";
           break;
           case "en":
             text = "To select the functionality,\n" +
                     "use the menu\n" +
                     "the bot is currently under development";
           break;
           default:
              text = "\uD83C\uDDEA\uD83C\uDDFA";
           break;
       }
          break;
      case "info":
         switch (Language) {
             case "ua":
                 text = "Це богатофункціональний бот \n" +
                         "© 2021 ТОВ ВП МАРІО \n" +
                         "версія №3, надряпан на java ";
                 break;
             case "ru":
                 text = "Это многофункциональный бот \n" +
                         "© 2021 ООО ПП МАРИО\n" +
                         "версия 3, нацарапан на java";
                 break;
             case "en":
                 text = "This is a multifunctional bot\n" +
                         "© 2021 LLC  MARIO\n" +
                         "version 3, scratched on java";
                 break;
             default:
                 text = "\uD83C\uDDEA\uD83C\uDDFA";
                 break;
         }
          break;
      default:
          text = "\uD83C\uDDEA\uD83C\uDDFA";
          break;
  }
  return text;
 }
}
