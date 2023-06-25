/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bigly;

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
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author Rodoshi
 */
public class SellHistoryController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML
    private TableView<SellingHistoryModel> table;

    @FXML
    private TableColumn<SellingHistoryModel, String> col_orid;

    @FXML
    private TableColumn<SellingHistoryModel, String> col_userid;

    @FXML
    private TableColumn<SellingHistoryModel, String> col_proid;

    @FXML
    private TableColumn<SellingHistoryModel, String> col_proname;

    @FXML
    private TableColumn<SellingHistoryModel, String> col_catid;

    @FXML
    private TableColumn<SellingHistoryModel, String> col_qnt;

    @FXML
    private TableColumn<SellingHistoryModel, String> col_date;

    @FXML
    private TableColumn<SellingHistoryModel, String> col_stayus;

    @FXML
    private TableColumn<SellingHistoryModel, String> col_total;

    @FXML
    private Text total;
    
    ObservableList<SellingHistoryModel> oblist = FXCollections.observableArrayList();

    //Connection conn = Javaconnect.ConnectDB();
    Connection conn;
    DBConnect dbc = new DBConnect(); //dbc.connectToDB();
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        try {
            load();
            loadBalance();
        } catch (SQLException ex) {
            Logger.getLogger(SellHistoryController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SellHistoryController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    
    
    public void load() throws ClassNotFoundException
    {
        try {
            conn = dbc.connectToDB();
            ResultSet rs = conn.createStatement().executeQuery("SELECT * from orders");

            while (rs.next()) {
                oblist.add(new SellingHistoryModel(rs.getString("orderid"), rs.getString("userid"), rs.getString("productid"), rs.getString("productname"), rs.getString("categoryid"),rs.getString("quantity"), rs.getString("date"), rs.getString("status"), rs.getString("total")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DashboardAdminController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        col_orid.setCellValueFactory(new PropertyValueFactory<>("orid"));
        col_userid.setCellValueFactory(new PropertyValueFactory<>("usid"));
        col_proid.setCellValueFactory(new PropertyValueFactory<>("prid"));
        col_proname.setCellValueFactory(new PropertyValueFactory<>("prname"));
        col_catid.setCellValueFactory(new PropertyValueFactory<>("catid"));
        col_qnt.setCellValueFactory(new PropertyValueFactory<>("qnty"));
        col_date.setCellValueFactory(new PropertyValueFactory<>("date"));
        col_stayus.setCellValueFactory(new PropertyValueFactory<>("status"));
        col_total.setCellValueFactory(new PropertyValueFactory<>("total"));

        table.setItems(oblist);
    }
    
    public void loadBalance() throws SQLException, ClassNotFoundException
    {
        PreparedStatement pst = null;
        ResultSet rs;
        String query = "SELECT sum(total) as total from orders where status='paid'";
       // Connection con = Javaconnect.ConnectDB();
        Connection con = dbc.connectToDB();
        pst = con.prepareStatement(query);
        rs = pst.executeQuery();
        if (rs.next()) {
        String tday = rs.getString("total");
        total.setText(tday);
        
        }
    }
}
