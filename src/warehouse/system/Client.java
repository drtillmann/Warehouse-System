import java.util.*;
import java.io.*;
public class Client implements Serializable {
  private static final long serialVersionUID = 1L;
  private String name;
  private String address;
  private String phone;
  private String id;
  private static final String CLIENT_STRING = "C";
  
  public  Client (String name, String address, String phone) {
    this.name = name;
    this.address = address;
    this.phone = phone;
    this.id = CLIENT_STRING + (ClientIdServer.instance()).getId();
  }

  public String getName() {
    return this.name;
  }
  public String getPhone() {
    return this.phone;
  }
  public String getAddress() {
    return this.address;
  }
  public String getId() {
    return this.id;
  }
  public void setName(String newName) {
    this.name = newName;
  }
  public void setAddress(String newAddress) {
    this.address = newAddress;
  }
  public void setPhone(String newPhone) {
    this.phone = newPhone;
  }
   public void setID() {
    this.id = CLIENT_STRING + (ClientIdServer.instance()).getId();
  }
  public boolean equals(String id) {
    return this.id.equals(id);
  }
  public String toString() {
    String string = "Client name: " + name + ", Address: " + address + ", ID: " + id + ", Phone " + phone;
    return string;
  }

    int getQty() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    Object getID() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    void setQty(int missingQty) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
