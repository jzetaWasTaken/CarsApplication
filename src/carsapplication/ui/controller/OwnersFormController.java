/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package carsapplication.ui.controller;

import carsapplication.exception.CarDBException;
import carsapplication.model.Owner;
import static carsapplication.ui.controller.GenericController.LOGGER;
import java.time.ZoneId;
import java.util.Date;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

/**
 * FXML Controller class
 *
 * @author Jon Zaballa
 */
public class OwnersFormController extends GenericController {

    /**
     * Owner name text field
     */
    @FXML
    private TextField tfName;
    
    /**
     * Owner surname text field
     */
    @FXML
    private TextField tfSurname;
    
    /**
     * Owner birth date date picker
     */
    @FXML
    private DatePicker dpBirthDate;
    
    /**
     * Cancel button to return to previous view
     */
    @FXML
    private Button btnCancel;
    
    /**
     * Save button to store the owner and return to previous view
     */
    @FXML
    private Button btnSave;

    /**
     * Window stage initializer
     * 
     * @param root 
     */
    void initStage(Parent root) {
        try {
            LOGGER.info("Initializing Car Form");

            // Set scene
            Scene scene = new Scene(root);
            stage = new Stage();
            stage.setScene(scene);

            // Set stage properties
            stage.setTitle("Cars");
            stage.setResizable(false);
            stage.initStyle(StageStyle.DECORATED);
            
            
            // On showing listener
            stage.setOnShowing(this::handleWindowShowing);

            // Nodes
            tfName.textProperty().addListener(this::handleTextFields);
            tfSurname.textProperty().addListener(this::handleTextFields);
            
            
            btnCancel.setOnAction(this::handleCancel);
            btnSave.setOnAction(this::handleSave);
            
            dpBirthDate.valueProperty().addListener(this::handleDate);
            
            // Show stage
            stage.show();
        } catch (Exception e) {
            showErrorAlert("Error initializing car form");
        }
    }
    
    /**
     * Handles the action to be taken before the window shows
     * 
     * @param event 
     */
    public void handleWindowShowing(WindowEvent event) {
        btnSave.setDisable(true);
    }
    
    /**
     * Handles save button. It stores the new owner, closes the current view and
     * opens the car list view
     * 
     * @param event 
     */
    public void handleSave(ActionEvent event) {
        try {
            manager.registerOwner(getOwner());
            stage.close();
            ((Stage) session.get("oldStage")).show();
            //loadCarsList();
        } catch (CarDBException e) {
            showErrorAlert("Error");
        } catch (NumberFormatException e) {
            showErrorAlert("Age must be a positive integer");
        }
    }
    
    /**
     * It returns to the previous view
     * 
     * @param event 
     */
    public void handleCancel(ActionEvent event) {
        //loadCarsList();
        stage.close();
        ((Stage) session.get("oldStage")).show();
        
    }
    
    /**
     * Handle text value changes in the text fields
     * 
     * @param observable
     * @param oldValue
     * @param newValue 
     */
    public void handleTextFields(ObservableValue observable,
            String oldValue,
            String newValue) {
        handleFields();
    }
    
    /**
     * Handles date picker value changes
     * 
     * @param observable
     * @param oldValue
     * @param newValue 
     */
    public void handleDate(ObservableValue observable,
            Object oldValue,
            Object newValue) {
        handleFields();
    }

    private void handleFields() {
        btnSave.setDisable(!(
            tfName.getText().trim().length() > 0 &&
            tfSurname.getText().trim().length() > 0 && 
            dpBirthDate.getValue() != null)
        );
    }

    private Owner getOwner() {
        Owner owner = new Owner();
        owner.setName(tfName.getText());
        owner.setSurname(tfSurname.getText());
        owner.setDateOfBirth(Date.from(dpBirthDate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        
        return owner;
    }
}
