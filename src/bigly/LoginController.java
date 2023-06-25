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
import java.util.Properties;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author Rodoshi
 */
public class LoginController implements Initializable {

    /**
     * Initializes the controller class.
     */
      @FXML
    private Button btnSignup;

    @FXML
    private JFXButton btnAdmin;

    @FXML
    private Button btnLogin;

    @FXML
    private JFXTextField txt1field;

    @FXML
    private JFXPasswordField pwfield;

    @FXML
    private JFXButton btnForgetPass;
    
    PreparedStatement pst = null;
    ResultSet rs;
    String expass;
    Connection conn = null;
    DBConnect dbc = new DBConnect(); //dbc.connectToDB();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
     @FXML
    void Login(ActionEvent event) throws SQLException, IOException, ClassNotFoundException {

        //conn = Javaconnect.ConnectDB();
        conn = dbc.connectToDB();
        String qu = "DELETE FROM users";
        try {
            PreparedStatement pst = conn.prepareStatement(qu);
            pst.execute();
            
        } catch (Exception e) {
        }
        
        String name = txt1field.getText();
        String pass = pwfield.getText();
        int i = 1;
        if (name.equals("") || pass.equals("")) {
            JOptionPane.showMessageDialog(null, "Please Fillup all the information");
        } else {
            String query = "SELECT * FROM logindata WHERE username=? and password=? or email_id=? and password=?";
            
            pst = conn.prepareStatement(query);
            pst.setString(1, name);
            pst.setString(2, pass);
            pst.setString(3, name);
            pst.setString(4, pass);
            rs = pst.executeQuery();
            if (rs.next()) {
               
                String nn = rs.getString("userid");
               
                String qname = "INSERT INTO users(userid) VALUES (?)";
                pst = conn.prepareStatement(qname);
                pst.setString(1, nn);
                pst.executeUpdate();
                
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("Homepage.fxml"));
                loader.load();
                Parent pane = loader.getRoot();
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(new Scene(pane));
                stage.centerOnScreen();
                stage.show();

            } else {
                JOptionPane.showMessageDialog(null, "Wrong username/email or password.\nor You don't"
                        + " have any account.");
            }
        }
    }

    @FXML
    void forgetPass(ActionEvent event) {

    }

    @FXML
    void openAdminPanel(ActionEvent event) throws IOException {

        Parent pane = FXMLLoader.load(getClass().getResource("LoginAsAdmin.fxml"));
        Scene scene = new Scene(pane);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.centerOnScreen();
        window.show();
    }

    @FXML
    void openSignUpPanel(ActionEvent event) throws IOException {

        Parent pane = FXMLLoader.load(getClass().getResource("SignUp.fxml"));
                    Scene scene = new Scene(pane);
                    Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    window.setScene(scene);
                    window.centerOnScreen();
                    window.show();
    }
    
    public void sendPass() //method to send OTP to user 
    {
        /*Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "465");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.ssl.enable", "true");
        
        Session session = Session.getInstance(props, new Authenticator() {
        @Override
        protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication("checkinn.cse@gmail.com", "checkinn123");
        }
        });
        session.setDebug(true);
        MimeMessage message = new MimeMessage(session);
        
        try {
        message.setFrom("checkinn.cse@gmail.com");
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(mailfld.getText()));
        message.setSubject("CheckInn Password");
        message.setText("Your password is : " + expass);
        message.saveChanges();
        
        Transport.send(message);
        JOptionPane.showMessageDialog(null, "Check your email for password.");
        
        } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Please check your internet connection");
        }*/

    }
    
}





   

    


