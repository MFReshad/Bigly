package bigly;

import java.io.FileNotFoundException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author HP
 */
public class Fxmlloader {

    private Pane view;

    public Pane getPage(String fileName) {
        try {

            URL fileUrl = Bigly.class.getResource("/bigly/" + fileName + ".fxml");
            if (fileUrl == null) {
                throw new java.io.FileNotFoundException("fxml file can't be found");
            }
            view = FXMLLoader.load(fileUrl);
        } catch (Exception e) {
            System.out.println("No page" + fileName + "Please check fxmlLoader");
        }

        return view;
    }

}
