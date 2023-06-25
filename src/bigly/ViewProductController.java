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
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Rodoshi
 */
public class ViewProductController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private ImageView img;

    @FXML
    private Text namelbl;

    @FXML
    private Text shrtDes;

    @FXML
    private Text offerPrice;

    @FXML
    private Text mainPrice;

    @FXML
    private TextFlow description;
    
    @FXML
    private Text txt;
    
    @FXML
    private Text id;
    
    @FXML
    private Button clsbtn;
    
    PreparedStatement pst = null;
    ResultSet rs;
    //Connection con = Javaconnect.ConnectDB();
    Connection con;
    DBConnect dbc = new DBConnect(); //dbc.connectToDB();
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        try {
            getValue();
        } catch (SQLException ex) {
            Logger.getLogger(ViewProductController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ViewProductController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }    
    
    public void setID(String id) throws SQLException, ClassNotFoundException
    {
        this.id.setText(id);
        getValue();
    }
    
    @FXML
    void addtocart(ActionEvent event) throws IOException, SQLException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("CartAdd.fxml"));
        
        Parent root = fxmlLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        CartAddController display = fxmlLoader.getController();
        display.setName(namelbl.getText());
        stage.setTitle("Add to Cart");
        stage.show();
    }

    @FXML
    void closeWindow(ActionEvent event) {
        Stage stage = (Stage) clsbtn.getScene().getWindow();
 
        stage.close();
    }
    
    public void getValue() throws SQLException, ClassNotFoundException
    {
        String query = "SELECT name,bdt,price,image,short_desc,description  FROM product where product.id=?";
                //con = Javaconnect.ConnectDB();
                con = dbc.connectToDB();
                pst = con.prepareStatement(query);
                pst.setString(1,id.getText());
                rs = pst.executeQuery();
                if (rs.next()) {
                    namelbl.setText(rs.getString("name"));
                    mainPrice.setText(rs.getString("bdt"));
                    offerPrice.setText(rs.getString("price"));
                   // String notChangedimgLoc = rs.getString("image");
                    Image im = new Image("file:"+rs.getString("image"));
                    img.setImage(im);
                    shrtDes.setText(rs.getString("short_desc"));
                    txt.setText(rs.getString("description"));
                    
                }
    }
    
    
}
