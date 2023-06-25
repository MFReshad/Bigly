/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bigly;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.awt.Desktop.Action;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author Rodoshi
 */
public class LoginAsAdminController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML
    private JFXButton btnBack;

    @FXML
    private Button btnLogin;

    @FXML
    private JFXTextField txt1field;

    @FXML
    private JFXPasswordField pwfield;

    PreparedStatement pst = null;
    ResultSet rs;
    //Connection con = Javaconnect.ConnectDB();
    DBConnect dbc = new DBConnect(); //dbc.connectToDB();
    Connection con;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML
    void LoginAdmin(ActionEvent event) throws SQLException, IOException, ClassNotFoundException {

             String n = txt1field.getText();
            String p = pwfield.getText();
            String query = "SELECT * FROM admin WHERE username=? and password=?";
                //con = Javaconnect.ConnectDB();
                con = dbc.connectToDB();
                pst = con.prepareStatement(query);
                pst.setString(1, n);
                pst.setString(2, p);
                rs = pst.executeQuery();
                if (rs.next()) {
                    /*FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("Admin.fxml"));
                    loader.load();
                    // AdminController display = loader.getController();
                    // display.setUserName(n);
                    Parent pane = loader.getRoot();
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    stage.setScene(new Scene(pane));
                    stage.centerOnScreen();
                    stage.show();*/
                    Parent pane = FXMLLoader.load(getClass().getResource("Admin.fxml"));
                    Scene scene = new Scene(pane);
                    Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    window.setScene(scene);
                    window.centerOnScreen();
                    window.show();
                    } 
                else {
                    JOptionPane.showMessageDialog(null, "Wrong username/email or password\nor\nYou are not belong to Admin.");
                    }
    }

    @FXML
    void goToLoginPage(ActionEvent event) throws IOException {

        Parent pane = FXMLLoader.load(getClass().getResource("Login.fxml"));
        Scene scene = new Scene(pane);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.centerOnScreen();
        window.show();
    }
    
}
