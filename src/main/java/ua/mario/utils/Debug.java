package ua.mario.utils;

import static ua.mario.http.messages.AdminMessage;

public class Debug {
 private Boolean DEBUGTERMINAL = true;
 private Boolean DEBUGFILE = true;
 private String DEBUGFILENAME = "/bots/log/bots.log";
 public Debug() {
 this.DEBUGTERMINAL = ua.mario.res.getDEBUGTERMINAL();
 this.DEBUGFILE = ua.mario.res.getDEBUGFILE();
 this.DEBUGFILENAME = ua.mario.res.getDEBUGFILENAME();
 }
 public void Debug(String Text) {
     if (this.DEBUGTERMINAL) {
         System.out.println(Text);
     }
     if (this.DEBUGFILE) {
         System.out.println("---------------------");
     }
 }
 public void Debug(String Text, Boolean Admin) {
  Debug(Text);
  AdminMessage(Text);
 }
}
