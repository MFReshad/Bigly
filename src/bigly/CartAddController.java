/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bigly;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author Rodoshi
 */
public class CartAddController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private JFXButton m1;

    @FXML
    private TextField tx1;

    @FXML
    private JFXButton p1;

    @FXML
    private JFXButton cancle;

    @FXML
    private Label nam;

    PreparedStatement pst = null;
    ResultSet rs;
    //Connection con = Javaconnect.ConnectDB();
    DBConnect dbc = new DBConnect(); //dbc.connectToDB();
    Connection con = null;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }

    
    String nm;

    public void setName(String name) throws SQLException {
        this.nam.setText(name);
        nm = nam.getText();

    }

    @FXML
    private void decrement1(ActionEvent event) {
        String s = tx1.getText();
        int n = Integer.parseInt(s);
        if (n > 0) {
            n = n - 1;
            tx1.setText(String.valueOf(n));
        }

    }

    @FXML
    private void increment1(ActionEvent event) {
        String s = tx1.getText();
        int n = Integer.parseInt(s) + 1;
        tx1.setText(String.valueOf(n));
    }

    @FXML
    void add(ActionEvent event) throws SQLException, ClassNotFoundException {
        //

        String pr = null;
        String query = "SELECT price  FROM product where name=?";
        con = dbc.connectToDB();
        pst = con.prepareStatement(query);
        pst.setString(1, nam.getText());
        rs = pst.executeQuery();
        if (rs.next()) {
            pr = rs.getString("price");
        }
        
        float p = Float.valueOf(pr);
        float n = Float.valueOf(tx1.getText());
        String t = String.format("%.02f", p * n);
        

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yy");
        LocalDateTime now = LocalDateTime.now();
        String date = dtf.format(now);

       try {
        String quy = "insert into orders( userid, productid, productname, categoryid,quantity,date,status,total) SELECT users.userid, product.id,product.name,product.categories_id,'" + tx1.getText() +"' , '"+date+"' ,'unpaid', '" +t+"' from users, product where product.name = ?";
        pst = con.prepareStatement(quy);
        
        pst.setString(1, nam.getText());
        
        pst.executeUpdate();
        Stage stage = (Stage) cancle.getScene().getWindow();
        
        stage.close();
        }
        catch(SQLException e)
        {
        JOptionPane.showMessageDialog(null, e);
        }
    }

    @FXML
    void back(ActionEvent event) {
        Stage stage = (Stage) cancle.getScene().getWindow();

        stage.close();
    }
}
