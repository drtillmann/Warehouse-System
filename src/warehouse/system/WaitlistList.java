/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package warehouse.system;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author drtil
 */
public class WaitlistList implements Serializable{
    private static final long serialVersionUID = 1L;
    private List<Waitlist> waitlists = new ArrayList<>();
    private static WaitlistList waitlistList;
    
    public WaitlistList(){
        
    }
    
    public static WaitlistList instance(){
        if(waitlistList == null){
            return waitlistList = new WaitlistList();
        }else{
            return waitlistList;
        }
    }
    
    public boolean addWaitlist(Waitlist wl){
        this.waitlists.add(wl);
        return true;
    }
    
    public Iterator getWaitlists(){
        return this.waitlists.iterator();
    }
    
   private void writeObject(java.io.ObjectOutputStream output) {
    try {
      output.defaultWriteObject();
      output.writeObject(waitlistList);
    } catch(IOException ioe) {
      ioe.printStackTrace();
    }
  }
  
  private void readObject(java.io.ObjectInputStream input) {
    try {
      if (waitlistList != null) {
        return;
      } else {
        input.defaultReadObject();
        if (waitlistList == null) {
          waitlistList = (WaitlistList) input.readObject();
        } else {
          input.readObject();
        }
      }
    } catch(IOException ioe) {
      ioe.printStackTrace();
    } catch(ClassNotFoundException cnfe) {
      cnfe.printStackTrace();
    }
  }
  
  public String toString(){
      String sWaitlists = "";
      for(Waitlist wl : this.waitlists){
          sWaitlists += wl.toString() + "\n";
      }
      return sWaitlists;
  }
}
