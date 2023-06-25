/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bigly;

/**
 *
 * @author HP
 */
public class modeltable3 {

    String id,name,Price, Qty;

    public modeltable3(String id, String name, String Price, String Qty) {
        this.id = id;
        this.name = name;
        this.Price = Price;
        this.Qty = Qty;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String Price) {
        this.Price = Price;
    }

    public String getQty() {
        return Qty;
    }

    public void setQty(String Qty) {
        this.Qty = Qty;
    }
    
    
    
}
