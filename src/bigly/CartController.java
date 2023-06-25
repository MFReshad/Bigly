/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bigly;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author HP
 */
public class CartController implements Initializable {

    @FXML
    private TableView<modeltable3> table3;
    @FXML
    private TableView<modeltable5> table4;
    
    @FXML
    private Label prc;
    @FXML
    private TableColumn<modeltable3, String> fdname;
    @FXML
    private TableColumn<modeltable3, String> fdqt;
    @FXML
    private TableColumn<modeltable3, String> fdprice;
    @FXML
    private TableColumn<modeltable3, String> col_orID;
    modeltable3 st;
    @FXML
    private TableColumn<modeltable5, String> fdname1;
    @FXML
    private TableColumn<modeltable5, String> fdqt1;
    @FXML
    private TableColumn<modeltable5, String> fdprice1;
    modeltable5 st1;

    

    int index = -1;
    //Connection con = Javaconnect.ConnectDB();
    PreparedStatement pst = null;
    ResultSet rs;
    DBConnect dbc = new DBConnect();
    Connection con;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            load();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(CartController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void confirmbtn(ActionEvent event) throws ClassNotFoundException, SQLException {
        //
        String pr = "SELECT userid  FROM users";
        String nam = null;
        //con = Javaconnect.ConnectDB();
        con = dbc.connectToDB();
        pst = con.prepareStatement(pr);
        rs = pst.executeQuery();
        if (rs.next()) {
            nam = rs.getString("userid");
        }

        System.out.println(nam);
      
        PreparedStatement pst1 = con.prepareStatement("select sum(product.price) as sum from product left join orders on orders.productid = product.id where orders.userid = ? and orders.status='unpaid'");
        pst1.setString(1, nam);
        rs = pst1.executeQuery();
        if (rs.next()) {
            prc.setText(rs.getString("sum"));
        }
    }

    @FXML
    private void removebtn(ActionEvent event) throws SQLException, ClassNotFoundException {

        index = table3.getSelectionModel().getSelectedIndex();
        if (index <= -1) {
            return;
        }
        String query = "DELETE FROM orders where orderid=?";
        try {
            con = dbc.connectToDB();
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1, col_orID.getCellData(index));
            pst.execute();
            JOptionPane.showMessageDialog(null, "An item has been deleted.");

            load();

        } catch (Exception e) {
        }
    }

    @FXML
    private void order(ActionEvent event) throws SQLException, ClassNotFoundException {

        String pr = "SELECT userid  FROM users";
        String nam = null;
        //con = Javaconnect.ConnectDB();
        con = dbc.connectToDB();
        pst = con.prepareStatement(pr);
        rs = pst.executeQuery();
        if (rs.next()) {
            nam = rs.getString("userid");
        }

        try {
            PreparedStatement pst1 = con.prepareStatement("UPDATE orders SET status='paid' WHERE userid = ?");

            pst1.setString(1, nam);

            pst1.executeUpdate();
            load();

            JOptionPane.showMessageDialog(null, "Your order has been confired");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }

    }

    public void load() throws ClassNotFoundException {
        table3.getItems().clear();
        table4.getItems().clear();
        ObservableList<modeltable3> oblist = FXCollections.observableArrayList();
    
        try {
            /*Connection con = null;
            DBConnect dbc = new DBConnect();*/
            con  = dbc.connectToDB();
           // Connection con = Javaconnect.ConnectDB();
            rs = con.createStatement().executeQuery("SELECT orders.orderid,orders.productname , product.price  ,  orders.quantity from orders left join product on orders.productname=product.name join users on orders.userid= users.userid where orders.status= 'unpaid' ");
            while (rs.next()) {
                oblist.add(new modeltable3(rs.getString("orderid"), rs.getString("productname"), rs.getString("price"), rs.getString("quantity")));
            }
        } catch (SQLException ex) {

        }
        col_orID.setCellValueFactory(new PropertyValueFactory<>("id"));
        fdname.setCellValueFactory(new PropertyValueFactory<>("name"));
        fdprice.setCellValueFactory(new PropertyValueFactory<>("Price"));
        fdqt.setCellValueFactory(new PropertyValueFactory<>("Qty"));
        table3.setItems(oblist);

        ObservableList<modeltable5> oblist1 = FXCollections.observableArrayList();
        try {
            /*Connection con = null;
            DBConnect dbc = new DBConnect();*/
            con  = dbc.connectToDB();
          //  Connection con = Javaconnect.ConnectDB();
            rs = con.createStatement().executeQuery("SELECT orders.productname , product.price  ,  orders.quantity from orders left join product on orders.productname=product.name join users on orders.userid= users.userid where orders.status= 'paid' ");
            while (rs.next()) {
                oblist1.add(new modeltable5(rs.getString("productname"), rs.getString("price"), rs.getString("quantity")));
            }
        } catch (SQLException ex) {

        }

        fdname1.setCellValueFactory(new PropertyValueFactory<>("fdname"));
        fdprice1.setCellValueFactory(new PropertyValueFactory<>("fdprice"));
        fdqt1.setCellValueFactory(new PropertyValueFactory<>("fdqty"));
        table4.setItems(oblist1);
    }

}
