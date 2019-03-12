    package warehouse.system;

    /*
     * To change this license header, choose License Headers in Project Properties.
     * To change this template file, choose Tools | Templates
     * and open the template in the editor.
     */

    /**
     *
     * @author Daniel
     */

    import java.io.*;
    public class Product implements Serializable {
      private static final long serialVersionUProductID = 1L;
      private String productName;
      private String productID;
      private Manufacturer manufacturer;
      private static final String PRODUCT_STRING = "P";
      private double price;
      private int qty;

      /* begin constructor*/
      
      public Product(String productName, Manufacturer manufacturer, double price) {
        this.productName = productName;

        ProductList temp = ProductList.instance();
        if (!temp.isEmpty()){
            String str = null;
            try{
                str = temp.searchName(productName).getID();
            }catch(Exception e){
                System.out.println(e.getMessage());
            }

            if(str == null) {
                this.productID = PRODUCT_STRING + (ProductIdServer.instance()).getId();
            }
            else {
                this.productID = str;
            }
        }else{
            this.productID = PRODUCT_STRING + 
                    (ProductIdServer.instance()).getId();
        }

        this.manufacturer = manufacturer;
        this.price = price;
        this.qty = 0;
      }
      /* end constructor */
      
      public void setQy(int qty){
          this.qty = qty;
      }

      public Manufacturer getManufacturer() {
        return this.manufacturer;
      }

      public String getProductName() {
        return this.productName;
      }

      public String getID() {
        return this.productID;
      }

      public double getPrice() {
            return this.price;
      }

      public String toString() {
        return "productName: " + productName + ", productID: " + productID + ", price: " + String.valueOf(price) + ", quantity: " + String.valueOf(qty)+ ", Manufacturer Information: " 
                + manufacturer;
      }
    } 