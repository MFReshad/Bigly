/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bigly;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import com.jfoenix.controls.JFXToggleButton;
import java.awt.HeadlessException;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author Rodoshi
 */
public class ProductDetailsAdminController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private TextArea shortDescTxt;

    @FXML
    private TextArea descTxt;
    
    
    @FXML
    private AnchorPane mainPricetxt;

    @FXML
    private TextField id;

    @FXML
    private TextField proName;

    @FXML
    private ComboBox<String> comb;

    @FXML
    private TextField mainPriceTxt;

    @FXML
    private TextField offerPricetxt;

    @FXML
    private Button addbtn;

    @FXML
    private Button upbtn;

    @FXML
    private ImageView img;

    @FXML
    private JFXToggleButton toggle;
    
    private FileChooser filechooser;
    private File filepath;
    
    int x=0,im=0;
    
    PreparedStatement pst = null;
    ResultSet rs;
    //Connection con = Javaconnect.ConnectDB();
    Connection con;
    DBConnect dbc = new DBConnect(); //dbc.connectToDB();
    
    public void setID(String id) throws SQLException, ClassNotFoundException
    {
        this.id.setText(id);
        loadData();
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            showCategory();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ProductDetailsAdminController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ProductDetailsAdminController.class.getName()).log(Level.SEVERE, null, ex);
        }
        descTxt.setWrapText(true);
        shortDescTxt.setWrapText(true);
    }   
    String cat ;
    @FXML
    void Addproduct(ActionEvent event) throws SQLException, ClassNotFoundException {
        //con = Javaconnect.ConnectDB();
        con = dbc.connectToDB();
        String name = comb.getValue();
        String que = "SELECT id FROM categories WHERE category=?";
        
        pst = con.prepareStatement(que);
            pst.setString(1, name);
             rs = pst.executeQuery();
            if (rs.next()) {
                cat = rs.getString("id");
            }
        
        try {
        String query = "INSERT INTO product(name,bdt,price,categories_id,image,short_desc,status,description) VALUES (?,?,?,?,?,?,?,?)";
        PreparedStatement pst = con.prepareStatement(query);
        
        
        String imgLoc = filepath.toString();
        
        pst.setString(1, proName.getText());
        pst.setString(2, mainPriceTxt.getText());
        pst.setString(3, offerPricetxt.getText());
        pst.setString(4, cat);
        pst.setString(5, imgLoc);
        pst.setString(6, shortDescTxt.getText());
        pst.setString(7, String.valueOf(x) );
        pst.setString(8, descTxt.getText());
        
        pst.executeUpdate();
        JOptionPane.showMessageDialog(null, "Record Added Successfully!");
        clear();
        Stage stage = (Stage) upbtn.getScene().getWindow();
 
        stage.close();
        }catch(HeadlessException | SQLException e){
        JOptionPane.showMessageDialog(null, e);
        }
    }

    @FXML
    void UpdateProduct(ActionEvent event) throws SQLException, ClassNotFoundException {
        //System.out.println("\n\n");
        //System.out.println(im);
        //System.out.println("\n\n");
        String finalPic;
        if(im==0)
        {
            finalPic = notChangedimgLoc;
        }
        else
        {
            finalPic = filepath.toString();
        }
        //con = Javaconnect.ConnectDB();
        con = dbc.connectToDB();
        String name = comb.getValue();
        String que = "SELECT id FROM categories WHERE category=?";
        
        pst = con.prepareStatement(que);
            pst.setString(1, name);
             rs = pst.executeQuery();
            if (rs.next()) {
                cat = rs.getString("id");
            }
        // String gen = comb.getSelectionModel().getSelectedItem().toString();
        String imgLc = filepath.toString();
        PreparedStatement pst = null;
        try {
        String query = "UPDATE product SET categories_id=?,name=?,bdt=?,price=?,image=?,short_desc=?,description=?,status=? WHERE  id=?";
        
        pst = con.prepareStatement(query);
        pst.setString(1, cat);
        pst.setString(2, proName.getText());
        pst.setString(3, mainPriceTxt.getText());
        pst.setString(4, offerPricetxt.getText());
        pst.setString(5, finalPic);
        pst.setString(6, shortDescTxt.getText());
        pst.setString(7, descTxt.getText());
        pst.setString(8, String.valueOf(x));
        pst.setString(9, id.getText());
        
        pst.executeUpdate();
        JOptionPane.showMessageDialog(null, "Record Updated Successfully!");
        clear();
        Stage stage = (Stage) upbtn.getScene().getWindow();
 
        stage.close();
        
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
            
        }
        
        
    }
    
    @FXML
    void setImg(ActionEvent event) {
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        filechooser = new FileChooser();
        filechooser.setTitle("Open image");
        
        this.filepath = filechooser.showOpenDialog(stage);
        try{
            BufferedImage bi = ImageIO.read(filepath);
            Image image = SwingFXUtils.toFXImage((bi), null);
            img.setImage(image);
            im = 1;
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
        
    }

    @FXML
    void setToggel(ActionEvent event) {
        if(toggle.isSelected())
        {
            toggle.setText("Active");
            toggle.setTextFill(Color.CHARTREUSE);
            x=1;
        }
        else
        {
            toggle.setText("Inactive");
            toggle.setTextFill(Color.BLACK);
            x=0;
        }
    }
    
    
    void showCategory() throws ClassNotFoundException, SQLException {
         
       // con = Javaconnect.ConnectDB();
        con = dbc.connectToDB();
        try {
            ObservableList<String> listacombo= FXCollections.observableArrayList();
            String consulta = "select category from Categories";
            PreparedStatement ps =con.prepareStatement(consulta);
              ResultSet rs1 = ps.executeQuery();
               
              while ( rs1.next() ) 
             {  
               listacombo.add(rs1.getString("category"));
             }
               
           comb.setItems(listacombo);
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void clearbtn(ActionEvent event) throws SQLException, ClassNotFoundException {
        clear();
            
    }
    
    public void clear() throws ClassNotFoundException, SQLException {
        
        id.setText("");
        proName.setText("");
        mainPriceTxt.setText("");
        offerPricetxt.setText("");
        comb.setItems(null);
        Image image = new Image("file:D:/Cse/3.1/Spring 21/zLab/Database/Bigly/src/image/Bigly.jpg");
        //Image imageObject = new Image(inStream);
        img.setImage(image);
        shortDescTxt.setText("");
        toggle.setSelected(false);
        toggle.setText("Inactive");
        toggle.setTextFill(Color.BLACK);
        x=0;
        descTxt.setText("");
        showCategory();
    }
    
    String notChangedimgLoc ;
    public void loadData() throws SQLException, ClassNotFoundException
    {
        String query = "SELECT product.name,categories.category,product.bdt,product.price,product.image,product.short_desc,product.description,product.status  FROM product left join categories on product.categories_id = categories.id where product.id=?";
               // con = Javaconnect.ConnectDB();
               con = dbc.connectToDB();
                pst = con.prepareStatement(query);
                pst.setString(1,id.getText());
                rs = pst.executeQuery();
                if (rs.next()) {
                    proName.setText(rs.getString("name"));
                    mainPriceTxt.setText(rs.getString("bdt"));
                    offerPricetxt.setText(rs.getString("price"));
                    comb.setValue(rs.getString("category"));
                    notChangedimgLoc = rs.getString("image");
                    Image im = new Image("file:"+rs.getString("image"));
                    img.setImage(im);
                    shortDescTxt.setText(rs.getString("short_desc"));
                    String val = rs.getString("status");
                    int m = Integer.valueOf(val);
                    if(m==1)
                    {
                        toggle.setSelected(true);
                        x=1;
                        toggle.setText("Active");
                        toggle.setTextFill(Color.CHARTREUSE);
                    }
                    descTxt.setText(rs.getString("description"));
                    System.out.println(notChangedimgLoc);
                    
                }
    }
}
