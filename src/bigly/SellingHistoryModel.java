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
public class SellingHistoryModel {
    String orid,usid,prid,prname,catid,qnty,date,status,total;

    public SellingHistoryModel(String orid, String usid, String prid, String prname, String catid, String qnty, String date, String status, String total) {
        this.orid = orid;
        this.usid = usid;
        this.prid = prid;
        this.prname = prname;
        this.catid = catid;
        this.qnty = qnty;
        this.date = date;
        this.status = status;
        this.total = total;
    }

    public String getOrid() {
        return orid;
    }

    public void setOrid(String orid) {
        this.orid = orid;
    }

    public String getUsid() {
        return usid;
    }

    public void setUsid(String usid) {
        this.usid = usid;
    }

    public String getPrid() {
        return prid;
    }

    public void setPrid(String prid) {
        this.prid = prid;
    }

    public String getPrname() {
        return prname;
    }

    public void setPrname(String prname) {
        this.prname = prname;
    }

    public String getCatid() {
        return catid;
    }

    public void setCatid(String catid) {
        this.catid = catid;
    }

    public String getQnty() {
        return qnty;
    }

    public void setQnty(String qnty) {
        this.qnty = qnty;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
    
    
}
