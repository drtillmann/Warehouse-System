import java.util.*;
import java.text.*;
import java.io.*;

public class Security {
	
	Security(){
		
	}
	
    Boolean verifyPassword(String user, String password){
		if (user.equals(password))
			return true;
		else
			return false;
	}
	
	Boolean verifyManager(String password){
		if (password.equals("manager"))
			return true;	
		else
			return false;
	}

}
