import java.util.*;
import java.text.*;
import java.io.*;
public class LibContext {
  
  private int currentState;
  private static Library library;
  private static LibContext context;
  private int currentUser;
  private String userID;
  private BufferedReader reader = new BufferedReader(new 
                                      InputStreamReader(System.in));
  public static final int IsClerk = 0;
  public static final int IsUser = 1;
  /*private LibState[] states;
  private int[][] nextState;*/

  public String getToken(String prompt) {
    do {
      try {
        System.out.println(prompt);
        String line = reader.readLine();
        StringTokenizer tokenizer = new StringTokenizer(line,"\n\r\f");
        if (tokenizer.hasMoreTokens()) {
          return tokenizer.nextToken();
        }
      } catch (IOException ioe) {
        System.exit(0);
      }
    } while (true);
  }
  
  private boolean yesOrNo(String prompt) {
    String more = getToken(prompt + " (Y|y)[es] or anything else for no");
    if (more.charAt(0) != 'y' && more.charAt(0) != 'Y') {
      return false;
    }
    return true;
  } 

  private void retrieve() {
    try {
      Library tempLibrary = Library.retrieve();
      if (tempLibrary != null) {
        System.out.println(" The library has been successfully retrieved from the file LibraryData \n" );
        library = tempLibrary;
      } else {
        System.out.println("File doesnt exist; creating new library" );
        library = Library.instance();
      }
    } catch(Exception cnfe) {
      cnfe.printStackTrace();
    }
  }

  //public void setLogin(int code)
  //{currentUser = code;}
  
  public void changeState(int exitCode) {
    if (exitCode == 0 )
        System.out.println(" Return to Login state" );
    if (exitCode == 1 )
        System.out.println(" Switch to menu for " + userID );
  }
    
  public void setUser(String uID) {
   userID = uID; //System.out.println(userID);
  }

  //public int getLogin()
  //{ return currentUser;}

  public String getUser()
  { return userID;}

  private LibContext() { //constructor
    System.out.println("In Libcontext constructor");
    if (yesOrNo("Look for saved data and  use it?")) {
      retrieve();
    } else {
      library = Library.instance();
    }
     
  }

   
  public static LibContext instance() {
    if (context == null) {
       System.out.println("calling constructor");
      context = new LibContext();
    }
    return context;
  }

  /*public void process(){
    states[currentState].run();
  }*/
  
  /*public static void main (String[] args){
    LibContext.instance().process(); 
  }*/


}
