//package warehouse.system;

import java.util.*;
import java.io.*;
public class ManufacturerList implements Serializable {
  private static final long serialVersionUID = 1L;
  private List manufacturers = new LinkedList();
  private static ManufacturerList manufacturerList;
  private ManufacturerList() {
  }
  public static ManufacturerList instance() {
    if (manufacturerList == null) {
      return (manufacturerList = new ManufacturerList());
    } else {
      return manufacturerList;
    }
  }
  public Manufacturer search(String manufacturerId) {
    for (Iterator iterator = manufacturers.iterator(); iterator.hasNext(); ) {
      Manufacturer manufacturer = (Manufacturer) iterator.next();
      if (manufacturer.getId().equals(manufacturerId)) {
        return manufacturer;
      }
    }
    return null;
  }
  
  public boolean insertManufacturer(Manufacturer manufacturer) {
    manufacturers.add(manufacturer);
    return true;
  }
   public boolean removeManufacturer(Manufacturer manufacturer) {
    manufacturers.remove(manufacturer);
    return true;
  }
  private void writeObject(java.io.ObjectOutputStream output) {
    try {
      output.defaultWriteObject();
      output.writeObject(manufacturerList);
    } catch(IOException ioe) {
      ioe.printStackTrace();
    }
  }
  private void readObject(java.io.ObjectInputStream input) {
    try {
      if (manufacturerList != null) {
        return;
      } else {
        input.defaultReadObject();
        if (manufacturerList == null) {
          manufacturerList = (ManufacturerList) input.readObject();
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
  public String toString() {
    return manufacturers.toString();
  }
  
  public Iterator getManufacturers(){
      return manufacturers.iterator();
  }
  
  public Iterator getProductManufacturers(String pid){
      List productManufacturers = new ArrayList();
      for(int i = 0; i < manufacturers.size(); i++){
          Manufacturer manu = (Manufacturer) manufacturers.get(i);
          Iterator prodIterator = manu.getProducts();
          while(prodIterator.hasNext()){
              Product prod = (Product) prodIterator.next();
              if(prod.getID().equals(pid)){
                  productManufacturers.add(manufacturers.get(i));
                  break;
              }
          }
      }
      
      return productManufacturers.iterator();
  }
}