/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package carsapplication.ui.controller;

import carsapplication.exception.CarDBException;
import carsapplication.exception.NoOwnerException;
import carsapplication.model.Car;
import carsapplication.model.Owner;
import static carsapplication.ui.controller.GenericController.LOGGER;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

/**
 * FXML Controller class for cars form view. Enables car creation and 
 * modification actions
 *
 * @author Jon Zaballa
 */
public class CarsFormController extends GenericController {

    /**
     * Car Plate number text field
     */
    @FXML
    private TextField tfPlate;
    
    /**
     * Car brand text field
     */
    @FXML
    private TextField tfBrand;
    
    /**
     * Car model text field
     */
    @FXML
    private TextField tfModel;
    
    /**
     * Car color text field
     */
    @FXML
    private TextField tfColor;
    
    /**
     * Car age text field
     */
    @FXML
    private TextField tfAge;
    
    /**
     * Car owners selection box
     */
    @FXML
    private ComboBox cbOwner;
    
    /**
     * Cancel button
     */
    @FXML
    private Button btnCancel;
    
    /**
     * Save button
     */
    @FXML
    private Button btnSave;

    /**
     * Owners observable list to fill the owners selection box
     */
    private ObservableList<Owner> comboData;
    
    /**
     * Whether the use of the view is going to be create or update
     */
    private boolean newCar;
    
    /**
     * Car that the form represents
     */
    Car car;
    
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
            
            // Set controller fields
            newCar = (boolean) session.get("newCar");
            
            // On showing listener
            stage.setOnShowing(this::handleWindowShowing);

            // Nodes
            tfAge.textProperty().addListener(this::handleTextFields);
            tfBrand.textProperty().addListener(this::handleTextFields);
            tfColor.textProperty().addListener(this::handleTextFields);
            tfModel.textProperty().addListener(this::handleTextFields);
            tfPlate.textProperty().addListener(this::handleTextFields);

            
            cbOwner.valueProperty().addListener(this::handleCombo);
            
            btnCancel.setOnAction(this::handleCancel);
            btnSave.setOnAction(this::handleSave);
            
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
        if (!newCar) {
            car = (Car) session.get("currentCar");
            tfBrand.setText(car.getBrand());
            tfAge.setText(car.getAge().toString());
            tfColor.setText(car.getColor());
            tfPlate.setText(car.getPlateNumber());
            tfModel.setText(car.getModel());
            cbOwner.getSelectionModel().select(car.getOwner());

            tfModel.setDisable(true);
            tfBrand.setDisable(true);
            btnSave.setDisable(false);
        } else {
            btnSave.setDisable(true);
        }
        initComboData();
    }

    /**
     * Handles text field changes
     * 
     * @param observable
     * @param oldValue
     * @param newValue 
     */
    public void handleTextFields(
            ObservableValue observable,
            String oldValue,
            String newValue) {
        handleData();
    }
    
    /**
     * Handles selection box changes
     * 
     * @param observable
     * @param oldValue
     * @param newValue 
     */
    public void handleCombo(ObservableValue observable,
            Object oldValue,
            Object newValue) {
        handleData();
    }
    
    /**
     * Handles the cancel button. It closes the form view and opens the car list
     * view
     * 
     * @param event 
     */
    public void handleCancel(ActionEvent event) {
        stage.close();
        ((Stage) session.get("oldStage")).show();
    }
    
    /**
     * Handles save button. Calls the manger to save the current car, closes the
     * car form and opens the car list view
     * 
     * @param event 
     */
    public void handleSave(ActionEvent event) {
        try {
            mapCar();
            if (newCar)
                manager.registerCar(car);
            else
                manager.modifyCar(car);
            stage.close();
            ((Stage) session.get("oldStage")).show(); 
            //loadCarsList();
        } catch (CarDBException e) {
            showErrorAlert("Error");
        } catch (NumberFormatException e) {
            showErrorAlert("Age must be a positive integer");
        }
    }
    
    
    private void handleData() {
        if (tfAge.getText().trim().length() > 0 && tfBrand.getText().trim().length() > 0
                && tfColor.getText().trim().length() > 0 && tfModel.getText().trim().length() > 0
                && tfPlate.getText().trim().length() > 0 && cbOwner.getValue() != null)
            btnSave.setDisable(false);
        else 
            btnSave.setDisable(true);
    }

    private void mapCar() throws NumberFormatException {
        if (car == null) car = new Car();
        car.setAge(Integer.parseInt(tfAge.getText()));
        if (car.getAge() < 0)
            throw new NumberFormatException();
        car.setBrand(tfBrand.getText());
        car.setColor(tfColor.getText());
        car.setModel(tfModel.getText());
        car.setOwner((Owner) cbOwner.getSelectionModel().getSelectedItem());
        car.setPlateNumber(tfPlate.getText());
    }
    
    private void initComboData() {
        try {
            comboData = FXCollections.observableArrayList(manager.getOwners());
            cbOwner.setItems(comboData);
        } catch (NoOwnerException e) {
            showWarningAlert("No Owners. Create owners before cars");
        } catch (CarDBException ex) {
            showErrorAlert("Error loading data");
        }
    }
}
