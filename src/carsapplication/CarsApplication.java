/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package carsapplication;

import carsapplication.ui.controller.DbSelectionController;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;

/**
 * Application entry point
 *
 * @author Jon Zaballa
 */
public class CarsApplication extends Application {
    
    /**
     * Actions to perform when the application starts
     * 
     * @param primaryStage
     * @throws Exception 
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        //Create Bussines Logic Controller to be passed to UI controllers
        //ManagerInterface bussinessLogicController= ManagerFactory.newManager();
        //Load node graph from fxml file
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("ui/view/db_selection.fxml")
        );
        Parent root = (Parent) loader.load();
        //Get controller for graph 
        DbSelectionController primaryStageController=
                ((DbSelectionController)loader.getController());
        
        primaryStageController.setStage(primaryStage);
        //Initializes primary stage
        primaryStageController.initStage(root);
    }

    /**
     * Main method
     * 
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
