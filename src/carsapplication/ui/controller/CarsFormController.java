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
import java.io.IOException;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

/**
 * FXML Controller class
 *
 * @author jon
 */
public class CarsFormController extends GenericController {

    @FXML
    private TextField tfPlate;
    @FXML
    private TextField tfBrand;
    @FXML
    private TextField tfModel;
    @FXML
    private TextField tfColor;
    @FXML
    private TextField tfAge;
    @FXML
    private ComboBox cbOwner;
    @FXML
    private Button btnCancel;
    @FXML
    private Button btnSave;

    private ObservableList<Owner> comboData;
    private boolean newCar;
    Car car;
    
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
            e.printStackTrace();
            showErrorAlert("Error initializing car form");
        }
    }

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

    public void handleTextFields(
            ObservableValue observable,
            String oldValue,
            String newValue) {
        handleData();
    }
    
    public void handleCombo(ObservableValue observable,
            Object oldValue,
            Object newValue) {
        handleData();
    }
    
    public void handleCancel(ActionEvent event) {
        stage.close();
        ((Stage) session.get("oldStage")).show();
        //loadCarsList();
    }
    
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
            e.printStackTrace();
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
