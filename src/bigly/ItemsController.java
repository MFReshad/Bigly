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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author HP
 */
public class ItemsController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
     @FXML
    private TableView<ItemModel> table;

    @FXML
    private TableColumn<ItemModel, String> col_proID;

    @FXML
    private TableColumn<ItemModel, String> col_proName;

    @FXML
    private TableColumn<ItemModel, String> col_shortDesc;

    @FXML
    private TableColumn<ItemModel, String> col_mainPrice;

    @FXML
    private TableColumn<ItemModel, String> col_offerPrice;

    @FXML
    private ComboBox<String> comb;

    @FXML
    private TextField high;

    @FXML
    private TextField low;
    
    
    PreparedStatement pst = null;
    ResultSet rs;
   // Connection con = Javaconnect.ConnectDB();
    Connection con ;
    DBConnect dbc = new DBConnect(); //dbc.connectToDB();
    
    int index = -1;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         try {
             // TODO
             showCategory();
             showTable();
         } catch (ClassNotFoundException ex) {
             Logger.getLogger(ItemsController.class.getName()).log(Level.SEVERE, null, ex);
         } catch (SQLException ex) {
             Logger.getLogger(ItemsController.class.getName()).log(Level.SEVERE, null, ex);
         }
        
    }

    @FXML
    void productDetails(ActionEvent event) {
        index = table.getSelectionModel().getSelectedIndex();
        if (index <= -1) {
            return;
        }
        
        try{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ViewProduct.fxml"));
        
        Parent root = fxmlLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        ViewProductController display = fxmlLoader.getController();
         display.setID(col_proID.getCellData(index));
         stage.setTitle("Product Details");
        stage.show();
        }
        catch(Exception e)
        {
        JOptionPane.showMessageDialog(null,e);
        }
    }

    @FXML
    void sortTable(ActionEvent event) throws SQLException, ClassNotFoundException {
        String cat = comb.getValue();
        
        String pl = low.getText();
        String ph = high.getText();
      //  System.out.println(cat);
        
        if(cat==null && pl.length()==0 && ph.length()==0)
        {
            
            showTable();
        }
        else if(cat.length()!=0 && pl.length()==0 && ph.length()==0 )
        {
            
            ObservableList<ItemModel> oblist = FXCollections.observableArrayList();
            
            try {
                con = dbc.connectToDB();
                ResultSet rs = con.createStatement().executeQuery("SELECT product.id, product.name,product.bdt,product.price,product.short_desc  FROM product left join categories on product.categories_id = categories.id where product.status=1 and categories.category =  "+"'"+cat+"'");
            while (rs.next()) {
                
                oblist.add(new ItemModel(rs.getString("id"),rs.getString("name"),rs.getString("short_desc"),rs.getString("bdt"),rs.getString("price")));
            }
        } catch (SQLException ex) {
             JOptionPane.showMessageDialog(null, ex);
        }
                    
                    col_proID.setCellValueFactory(new PropertyValueFactory<>("id"));
                    col_proName.setCellValueFactory(new PropertyValueFactory<>("name"));
                    col_shortDesc.setCellValueFactory(new PropertyValueFactory<>("shrtdesc"));
                    col_mainPrice.setCellValueFactory(new PropertyValueFactory<>("mprice"));
                    col_offerPrice.setCellValueFactory(new PropertyValueFactory<>("oprice"));
                    table.setItems(oblist);
        }
        
        else if(cat.length()!=0 && pl.length()!=0 && ph.length()==0 || pl.length()==0 && ph.length()!=0 )
        {
            JOptionPane.showMessageDialog(null, "Fill the both price range");
        }
        
        else if(cat.length()!=0 && pl.length()!=0 && ph.length()!=0 )
        {
            
            ObservableList<ItemModel> oblist = FXCollections.observableArrayList();
            
            try {
                con = dbc.connectToDB();
                ResultSet rs = con.createStatement().executeQuery("SELECT product.id, product.name,product.bdt,product.price,product.short_desc  FROM product left join categories on product.categories_id = categories.id where product.status=1 and categories.category = '"+cat+"' and product.price>= '"+pl+"' and product.price<= '"+ph+"' or product.status=1 and categories.category = '"+cat+"' and product.bdt>= '"+pl+"' and product.bdt<= '"+ph+"'");
            while (rs.next()) {
                
                oblist.add(new ItemModel(rs.getString("id"),rs.getString("name"),rs.getString("short_desc"),rs.getString("bdt"),rs.getString("price")));
            }
        } catch (SQLException ex) {
             JOptionPane.showMessageDialog(null, ex);
        }
                    
                    col_proID.setCellValueFactory(new PropertyValueFactory<>("id"));
                    col_proName.setCellValueFactory(new PropertyValueFactory<>("name"));
                    col_shortDesc.setCellValueFactory(new PropertyValueFactory<>("shrtdesc"));
                    col_mainPrice.setCellValueFactory(new PropertyValueFactory<>("mprice"));
                    col_offerPrice.setCellValueFactory(new PropertyValueFactory<>("oprice"));
                    table.setItems(oblist);
        }
        
        else 
        {
            
            ObservableList<ItemModel> oblist = FXCollections.observableArrayList();
            
            try {
                con = dbc.connectToDB();
                ResultSet rs = con.createStatement().executeQuery("SELECT id, name,bdt,price,short_desc  FROM product where status=1 and price>= '"+pl+"' and price<= '"+ph+"' or status=1 and bdt>= '"+pl+"' and bdt<= '"+ph+"'");
            while (rs.next()) {
                
                oblist.add(new ItemModel(rs.getString("id"),rs.getString("name"),rs.getString("short_desc"),rs.getString("bdt"),rs.getString("price")));
            }
        } catch (SQLException ex) {
             JOptionPane.showMessageDialog(null, ex);
        }
                    
                    col_proID.setCellValueFactory(new PropertyValueFactory<>("id"));
                    col_proName.setCellValueFactory(new PropertyValueFactory<>("name"));
                    col_shortDesc.setCellValueFactory(new PropertyValueFactory<>("shrtdesc"));
                    col_mainPrice.setCellValueFactory(new PropertyValueFactory<>("mprice"));
                    col_offerPrice.setCellValueFactory(new PropertyValueFactory<>("oprice"));
                    table.setItems(oblist);
        }
        
        

    }
    
    @FXML
    void clearCat(ActionEvent event) {
        comb.setValue(null);
        
    }
    
    void showCategory() throws ClassNotFoundException, SQLException {
         
        //con = Javaconnect.ConnectDB();
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
    
    public void showTable() throws ClassNotFoundException
    {
        ObservableList<ItemModel> oblist = FXCollections.observableArrayList();
        try {
            con = dbc.connectToDB();
            ResultSet rs = con.createStatement().executeQuery("SELECT id,name,bdt,price,short_desc  FROM product where status=1");
            while (rs.next()) {
                oblist.add(new ItemModel(rs.getString("id"),rs.getString("name"),rs.getString("short_desc"),rs.getString("bdt"),rs.getString("price")));
            }
        } catch (SQLException ex) {
             JOptionPane.showMessageDialog(null, ex);
        }
        
        col_proID.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_proName.setCellValueFactory(new PropertyValueFactory<>("name"));
        col_shortDesc.setCellValueFactory(new PropertyValueFactory<>("shrtdesc"));
        col_mainPrice.setCellValueFactory(new PropertyValueFactory<>("mprice"));
        col_offerPrice.setCellValueFactory(new PropertyValueFactory<>("oprice"));
        
        table.setItems(oblist);
    }

}
