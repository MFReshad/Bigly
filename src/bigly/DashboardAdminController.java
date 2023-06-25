/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bigly;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class DashboardAdminController implements Initializable {

     @FXML
    private Label totalUserLbl;

    @FXML
    private Label totalProductLbl;

    @FXML
    private Label totalCatLbl;

    @FXML
    private Label pendinLbl;

    @FXML
    private Label date;

    @FXML
    private TableView<ModelTableDashAdmin> table;

    @FXML
    private TableColumn<ModelTableDashAdmin, String> col_bookID;

    @FXML
    private TableColumn<ModelTableDashAdmin, String> col_userId;

    @FXML
    private TableColumn<ModelTableDashAdmin, String> col_productid;

    @FXML
    private TableColumn<ModelTableDashAdmin, String> col_category;

    @FXML
    private TableColumn<ModelTableDashAdmin, String> col_quantity;

    @FXML
    private TableColumn<ModelTableDashAdmin, String> col_phone;

    @FXML
    private TableColumn<ModelTableDashAdmin, String> col_mail;

    @FXML
    private TableColumn<ModelTableDashAdmin, String> col_regdate;

    @FXML
    private TableColumn<ModelTableDashAdmin, String> col_cost;

    ObservableList<ModelTableDashAdmin> oblist = FXCollections.observableArrayList();

    //Connection conn = Javaconnect.ConnectDB();
    DBConnect dbc = new DBConnect(); //dbc.connectToDB();
    Connection conn;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        time();

        try {

            categoryTotal();
            pending();
            productCount();
            userCount();
        } catch (SQLException ex) {
            Logger.getLogger(DashboardAdminController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
             Logger.getLogger(DashboardAdminController.class.getName()).log(Level.SEVERE, null, ex);
         }

        

        try {
            conn = dbc.connectToDB();
            ResultSet rs = conn.createStatement().executeQuery("SELECT orders.orderid, orders.userid, orders.productid, categories.category, orders.quantity , logindata.phoneno, logindata.email_id, orders.date, orders.total from orders left join categories on categories.id = orders.categoryid left join logindata on logindata.userid = orders.userid");

            while (rs.next()) {
                oblist.add(new ModelTableDashAdmin(rs.getString("orderid"), rs.getString("userid"), rs.getString("productid"), rs.getString("category"), rs.getString("quantity"), rs.getString("phoneno"), rs.getString("email_id"), rs.getString("date"),rs.getString("total")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DashboardAdminController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
             Logger.getLogger(DashboardAdminController.class.getName()).log(Level.SEVERE, null, ex);
         }
        
        col_bookID.setCellValueFactory(new PropertyValueFactory<>("bookingid"));
        col_userId.setCellValueFactory(new PropertyValueFactory<>("userid"));
        col_productid.setCellValueFactory(new PropertyValueFactory<>("productid"));
        col_category.setCellValueFactory(new PropertyValueFactory<>("category"));
        col_quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        col_phone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        col_mail.setCellValueFactory(new PropertyValueFactory<>("email"));
        col_regdate.setCellValueFactory(new PropertyValueFactory<>("date"));
        col_cost.setCellValueFactory(new PropertyValueFactory<>("cost"));

        table.setItems(oblist);
    }

    public void time() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MMM-yy   hh:mm");
        LocalDateTime now = LocalDateTime.now();
        date.setText(dtf.format(now));
    }

    public void pending() throws SQLException, ClassNotFoundException {
        
        
        PreparedStatement pst = null;
        ResultSet rs;
        String query = "SELECT COUNT(status) as cou from orders where status='unpaid'";
        //Connection con = Javaconnect.ConnectDB();
        Connection con = dbc.connectToDB();
        pst = con.prepareStatement(query);
        rs = pst.executeQuery();
        if (rs.next()) {
        String tday = rs.getString("cou");
        pendinLbl.setText(tday);
        
        }

    }

    public void categoryTotal() throws SQLException, ClassNotFoundException {
        
        PreparedStatement pst = null;
        ResultSet rs;
        String qury = "SELECT COUNT(*) as cat FROM categories";
       // Connection con = Javaconnect.ConnectDB();
       Connection con = dbc.connectToDB();
        pst = con.prepareStatement(qury);
        rs = pst.executeQuery();
        if (rs.next()) {
            String ttday = rs.getString("cat");
            totalCatLbl.setText(ttday);

        }

    }

    public void userCount() throws SQLException, ClassNotFoundException {

        DateTimeFormatter dtfll = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime now = LocalDateTime.now();
        String today = dtfll.format(now);

        PreparedStatement pst = null;
        ResultSet rs;
        String qurye = "SELECT COUNT(*) as total FROM logindata";
       // Connection con = Javaconnect.ConnectDB();
        Connection con =dbc.connectToDB();
        pst = con.prepareStatement(qurye);
        rs = pst.executeQuery();
        if (rs.next()) {
            String ttday = rs.getString("total");
            totalUserLbl.setText(ttday);

        }
    }

    public void productCount() throws SQLException, ClassNotFoundException {

        PreparedStatement pst = null;
        ResultSet rs;
        String qury = "SELECT COUNT(*) as pro FROM product";
        //Connection con = Javaconnect.ConnectDB();
        Connection con = dbc.connectToDB();
        pst = con.prepareStatement(qury);
        rs = pst.executeQuery();
        if (rs.next()) {
            String pro = rs.getString("pro");
            totalProductLbl.setText(pro);

        }
    }

}
