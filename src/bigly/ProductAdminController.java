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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author Rodoshi
 */
public class ProductAdminController implements Initializable {

    @FXML
    private TableView<ProductAdminModel> productTable;
    
    @FXML
    private TableColumn<ProductAdminModel, String> col_id;

    @FXML
    private TableColumn<ProductAdminModel, String> col_productName;

    @FXML
    private TableColumn<ProductAdminModel, String> col_cat;

    @FXML
    private TableColumn<ProductAdminModel, String> col_mainPrice;

    @FXML
    private TableColumn<ProductAdminModel, String> col_offerPrice;

    @FXML
    private TableColumn<ProductAdminModel, String> col_status;

    @FXML
    private TableColumn<ProductAdminModel, String> col_shortDesc;

    

    @FXML
    private TableColumn<ProductAdminModel, String> col_img;
    
    String parseID;
    int index = -1;
    //Connection con = Javaconnect.ConnectDB();
    PreparedStatement pst = null;
    ResultSet rs;
    Connection con;
    DBConnect dbc = new DBConnect(); //dbc.connectToDB();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            showTable();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ProductAdminController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    
    @FXML
    void addItem(ActionEvent event) {
        try{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ProductDetailsAdmin.fxml"));
        
        Parent root = fxmlLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Product Info");
        stage.show();
        }
        catch(IOException e)
        {
        JOptionPane.showMessageDialog(null,e);
        }
    }

    @FXML
    void deleteItem(ActionEvent event) {
        index = productTable.getSelectionModel().getSelectedIndex();
        if (index <= -1) {
            return;
        }
        String query = "DELETE FROM product where id=?";
        try {
            con = dbc.connectToDB();
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1, col_id.getCellData(index));
            pst.execute();
            JOptionPane.showMessageDialog(null, "An item has been deleted.");
            showTable();
            
        } catch (Exception e) {
        }
    }

    @FXML
    void editItem(ActionEvent event) throws IOException {
        index = productTable.getSelectionModel().getSelectedIndex();
        if (index <= -1) {
            return;
        }
        
        try{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ProductDetailsAdmin.fxml"));
        
        Parent root = fxmlLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        ProductDetailsAdminController display = fxmlLoader.getController();
         display.setID(col_id.getCellData(index));
         stage.setTitle("Product Info");
        stage.show();
        }
        catch(Exception e)
        {
        JOptionPane.showMessageDialog(null,e);
        }
    }
    
    public void showTable() throws ClassNotFoundException
    {
        ObservableList<ProductAdminModel> oblist = FXCollections.observableArrayList();
        try {
            con = dbc.connectToDB();
            ResultSet rs = con.createStatement().executeQuery("SELECT product.id,product.name,categories.category,product.bdt,product.price,product.image,product.short_desc,status  FROM product left join categories on product.categories_id = categories.id");
            while (rs.next()) {
                oblist.add(new ProductAdminModel(rs.getString("id"),rs.getString("name")
                       ,rs.getString("category"),rs.getString("bdt"),rs.getString("price"),rs.getString("status"),rs.getString("short_desc"),rs.getString("image")));
            }
        } catch (SQLException ex) {
             JOptionPane.showMessageDialog(null, ex);
        }
        
        col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_productName.setCellValueFactory(new PropertyValueFactory<>("productname"));
        col_cat.setCellValueFactory(new PropertyValueFactory<>("category"));
        col_mainPrice.setCellValueFactory(new PropertyValueFactory<>("mainP"));
        col_offerPrice.setCellValueFactory(new PropertyValueFactory<>("offerP"));
        col_status.setCellValueFactory(new PropertyValueFactory<>("status"));
        col_shortDesc.setCellValueFactory(new PropertyValueFactory<>("shortDes"));
        col_img.setCellValueFactory(new PropertyValueFactory<>("imgloc"));
        productTable.setItems(oblist);
    }
    
    
    @FXML
    void refreshTable(ActionEvent event) throws ClassNotFoundException {
        showTable();
    }
}

