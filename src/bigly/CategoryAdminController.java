/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bigly;

import com.jfoenix.controls.JFXTextField;
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
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author Rodoshi
 */
public class CategoryAdminController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML
    private TableView<CategoryAdminModelTable> catTable;

    @FXML
    private TableColumn<CategoryAdminModelTable, String> col_id;

    @FXML
    private TableColumn<CategoryAdminModelTable, String> col_catName;

    @FXML
    private JFXTextField txt;
    
    
    int index = -1;
   // Connection con = Javaconnect.ConnectDB();
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
            Logger.getLogger(CategoryAdminController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }  
    
    @FXML
    void EditCat(ActionEvent event) throws SQLException, ClassNotFoundException {
        index = catTable.getSelectionModel().getSelectedIndex();
        if (index <= -1) {
            return;
        }
        String query = "UPDATE categories SET category=? WHERE  id=?";
        con = dbc.connectToDB();
        pst = con.prepareStatement(query);
        pst.setString(1, txt.getText());
        pst.setString(2, col_id.getCellData(index));
        
        pst.executeUpdate();
        showTable();
    }

    @FXML
    void addCategory(ActionEvent event) throws SQLException, ClassNotFoundException {
        //con = Javaconnect.ConnectDB();
        con =dbc.connectToDB();
        String query = "INSERT INTO categories (category) VALUES (?)";
        PreparedStatement pst = con.prepareStatement(query);
        
        
        pst.setString(1, txt.getText());
        
        pst.executeUpdate();
        showTable();
    }

    @FXML
    void deleteCAT(ActionEvent event) {
        index = catTable.getSelectionModel().getSelectedIndex();
        if (index <= -1) {
            return;
        }
        String query = "DELETE FROM categories where id=?";
        try {
            con = dbc.connectToDB();
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1, col_id.getCellData(index));
            pst.execute();
            JOptionPane.showMessageDialog(null, "A category has been deleted.");
            showTable();
            
        } catch (Exception e) {
        }
    }
    
    public void showTable() throws ClassNotFoundException
    {
        ObservableList<CategoryAdminModelTable> oblist = FXCollections.observableArrayList();
        try {
            con = dbc.connectToDB();
            ResultSet rs = con.createStatement().executeQuery("SELECT * from categories");
            while (rs.next()) {
                oblist.add(new CategoryAdminModelTable(rs.getString("id"),rs.getString("category")));
            }
        } catch (SQLException ex) {
             JOptionPane.showMessageDialog(null, ex);
        }
        
        col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_catName.setCellValueFactory(new PropertyValueFactory<>("name"));
        
        catTable.setItems(oblist);
        txt.setText("");
    }
    
    @FXML
    void getVal(MouseEvent event) {
        index = catTable.getSelectionModel().getSelectedIndex();
        if (index <= -1) {
            return;
        }
        txt.setText(col_catName.getCellData(index));
    }
    
}
