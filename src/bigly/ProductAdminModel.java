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
public class ProductAdminModel {
    String id,productname,category,mainP,offerP,status,shortDes,imgloc;

    public ProductAdminModel(String id, String productname, String category, String mainP, String offerP, String status, String shortDes, String imgloc) {
        this.id = id;
        this.productname = productname;
        this.category = category;
        this.mainP = mainP;
        this.offerP = offerP;
        this.status = status;
        this.shortDes = shortDes;
        this.imgloc = imgloc;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getMainP() {
        return mainP;
    }

    public void setMainP(String mainP) {
        this.mainP = mainP;
    }

    public String getOfferP() {
        return offerP;
    }

    public void setOfferP(String offerP) {
        this.offerP = offerP;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getShortDes() {
        return shortDes;
    }

    public void setShortDes(String shortDes) {
        this.shortDes = shortDes;
    }


    public String getImgloc() {
        return imgloc;
    }

    public void setImgloc(String imgloc) {
        this.imgloc = imgloc;
    }
    
    
    
}
