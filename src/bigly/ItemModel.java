/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bigly;

/**
 *
 * @author Rodoshi
 */
public class ItemModel {
    
    String id,name,shrtdesc,mprice,oprice;

    public ItemModel(String id, String name, String shrtdesc, String mprice, String oprice) {
        this.id = id;
        this.name = name;
        this.shrtdesc = shrtdesc;
        this.mprice = mprice;
        this.oprice = oprice;
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

    public String getShrtdesc() {
        return shrtdesc;
    }

    public void setShrtdesc(String shrtdesc) {
        this.shrtdesc = shrtdesc;
    }

    public String getMprice() {
        return mprice;
    }

    public void setMprice(String mprice) {
        this.mprice = mprice;
    }

    public String getOprice() {
        return oprice;
    }

    public void setOprice(String oprice) {
        this.oprice = oprice;
    }
    
    
    
}
