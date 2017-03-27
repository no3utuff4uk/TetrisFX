/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.no3utuff4uk.tetris;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author torne
 */
public class Tetris extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainWindow.fxml"));
        Parent root = loader.load();
        
        MainWindowController controller = loader.getController();
        Scene scene = new Scene(root);
        
        scene.setOnKeyPressed(event -> controller.keyHandler(event));
        
        
        
        stage.setTitle("Tetris");
        stage.setScene(scene);
        
        stage.setOnCloseRequest(event -> controller.clearOnExit());
        
                
        stage.setResizable(false);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
