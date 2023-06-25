/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bigly;


import java.awt.Label;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author HP
 */
public class HomepageController implements Initializable {

    @FXML
    private Label label;
    @FXML
    private BorderPane mainpane;
    @FXML
    private MediaView mv;

    /**
     * Initializes the controller class.
     */
    PreparedStatement ps;
    Connection conn;
    ResultSet rs;
    DBConnect dbc = new DBConnect(); //dbc.connectToDB();
    
   
    public void products(ActionEvent event) {
        
        Fxmlloader object = new Fxmlloader();
        Pane view = object.getPage("Items");
        mainpane.setCenter(view);

    }

    public void cart(ActionEvent event) {

        
        Fxmlloader object = new Fxmlloader();
        Pane view = object.getPage("Cart");
        mainpane.setCenter(view);
        
    }

    public void logoutbtn(ActionEvent event) throws IOException, SQLException, ClassNotFoundException {
        
        PreparedStatement pst = null;
        Connection conn;
        //conn = Javaconnect.ConnectDB();
        conn = dbc.connectToDB();
        
        String sql = "DELETE FROM users";
        
        pst = conn.prepareStatement(sql);
        pst.executeUpdate();
           
        Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        Scene scene = new Scene(root);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.centerOnScreen();
        window.show();

    }

    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
      //  medi();
      

    }
    /*MediaPlayer mediaplayer;
    
    public void medi() {
    String loc = "file:/D:/Cse/2.1/Spring-20/zLAB/SD/CheckInn/src/image/welcomFinal.mp4";
    Media media = new Media(loc);
    mediaplayer = new MediaPlayer(media);
    mediaplayer.setAutoPlay(true);
    mediaplayer.setCycleCount(MediaPlayer.INDEFINITE);
    mv.setMediaPlayer(mediaplayer);
    
    mediaplayer.setOnEndOfMedia(new Runnable() {
    @Override
    public void run() {
    mediaplayer.seek(Duration.ZERO);
    mediaplayer.play();
    }
    });
    }*/

}
