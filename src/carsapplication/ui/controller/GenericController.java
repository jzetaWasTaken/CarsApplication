/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package carsapplication.ui.controller;

import carsapplication.logic.ManagerInterface;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

/**
 * This is the base class for UI controllers in this application. It contains 
 * common methods and references for objects used by UI controllers
 * @author javi
 */
public abstract class GenericController {
    /**
     * Logger object used to log messages for application.
     */
    protected static final Logger LOGGER=Logger.getLogger("carsapplication.controller");
    /**
     * Maximum text fields length.
     */
    protected final int MAX_LENGTH=255;
    /**
     * The business logic object containing all business methods.
     */
    protected ManagerInterface manager;
    
    protected Map session;
    
    public void setSession(Map session) {
        this.session = session;
    }
    
    /**
     * Sets the business logic object to be used by this UI controller. 
     * @param manager An object implementing {@link Manager} interface.
     */
    public void setUsersManager(ManagerInterface manager){
        this.manager = manager;
    }
    /**
     * The Stage object associated to the Scene controlled by this controller.
     * This is an utility method reference that provides quick access inside the 
     * controller to the Stage object in order to make its initialization. Note 
     * that this makes Application, Controller and Stage being tightly coupled.
     */
    protected Stage stage;
    /**
     * Gets the Stage object related to this controller.
     * @return The Stage object initialized by this controller.
     */
    public Stage getStage(){
        return stage;
    }
    /**
     * Sets the Stage object related to this controller. 
     * @param stage The Stage object to be initialized.
     */
    public void setStage(Stage stage){
        this.stage = stage;
    }
    /**
     * Shows an error message in an alert dialog.
     * @param errorMsg The error message to be shown.
     */
    protected void showErrorAlert(String errorMsg){
        //Shows error dialog.
        Alert alert=new Alert(Alert.AlertType.ERROR,
                              errorMsg,
                              ButtonType.OK);
        // TODO add css
        alert.getDialogPane().getStylesheets().add(
              getClass().getResource("").toExternalForm());
        alert.showAndWait();
    }
    
    /**
     * Shows a message in an alert dialog.
     * @param msg The message to be shown.
     */
    protected void showInfoAlert(String msg){
        //Shows error dialog.
        Alert alert=new Alert(Alert.AlertType.INFORMATION,
                              msg,
                              ButtonType.OK);
        // TODO add css
        alert.getDialogPane().getStylesheets().add(
              getClass().getResource("").toExternalForm());
        alert.showAndWait();
    }
    
    /**
     * Shows a warning message in an alert dialog.
     * @param warningMsg The warning message to be shown.
     */
    protected void showWarningAlert(String warningMsg){
        //Shows error dialog.
        Alert alert=new Alert(Alert.AlertType.WARNING,
                              warningMsg,
                              ButtonType.OK);
        // TODO add css
        alert.getDialogPane().getStylesheets().add(
              getClass().getResource("").toExternalForm());
        alert.showAndWait();
    }
    
    /**
     * Shows a message in an alert dialog.
     * @param confirmMsg The confirmation message to be shown.
     * @return 
     */
    protected Optional<ButtonType> showConfirmationAlert(String confirmMsg){
        //Shows error dialog.
        Alert alert=new Alert(Alert.AlertType.CONFIRMATION,
                              confirmMsg,
                              ButtonType.YES,
                              ButtonType.CANCEL
        );
        // TODO add css
        alert.getDialogPane().getStylesheets().add(
              getClass().getResource("").toExternalForm());
        final Optional<ButtonType> result = alert.showAndWait();
        return result;
    }
    
    /*
    protected void loadCarsList() throws IOException {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/carsapplication/ui/view/cars_list.fxml")
        );
        Parent root = (Parent) loader.load();
        CarsListController controller = (CarsListController)loader.getController();
        controller.setUsersManager(manager);
        stage.hide();
        controller.initStage(root);
    }
    */
}
