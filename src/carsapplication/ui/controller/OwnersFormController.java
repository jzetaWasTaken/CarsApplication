/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package carsapplication.ui.controller;

import carsapplication.exception.CarDBException;
import carsapplication.model.Owner;
import static carsapplication.ui.controller.GenericController.LOGGER;
import java.io.IOException;
import java.net.URL;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
 * @author jon
 */
public class OwnersFormController extends GenericController {

    @FXML
    private TextField tfName;
    @FXML
    private TextField tfSurname;
    @FXML
    private DatePicker dpBirthDate;
    @FXML
    private Button btnCancel;
    @FXML
    private Button btnSave;

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
            e.printStackTrace();
            showErrorAlert("Error initializing car form");
        }
    }
    
    public void handleWindowShowing(WindowEvent event) {
        btnSave.setDisable(true);
    }
    
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
    
    public void handleCancel(ActionEvent event) {
        //loadCarsList();
        stage.close();
        ((Stage) session.get("oldStage")).show();
        
    }
    
    public void handleTextFields(ObservableValue observable,
            String oldValue,
            String newValue) {
        handleFields();
    }
    
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
