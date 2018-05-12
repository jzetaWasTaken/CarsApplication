/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package carsapplication.ui.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author jon
 */
public class CarsListController extends GenericController {

    @FXML
    private TextField tfSearch;
    @FXML
    private RadioButton rbPlate;
    @FXML
    private RadioButton rbBrand;
    @FXML
    private RadioButton rbModel;
    @FXML
    private RadioButton rbColor;
    @FXML
    private RadioButton rbAge;
    @FXML
    private RadioButton rbOwner;
    @FXML
    private TableView<?> tvCars;
    @FXML
    private TableColumn<?, ?> tcPlate;
    @FXML
    private TableColumn<?, ?> tcBrand;
    @FXML
    private TableColumn<?, ?> tcModel;
    @FXML
    private TableColumn<?, ?> tcColor;
    @FXML
    private TableColumn<?, ?> tcAge;
    @FXML
    private TableColumn<?, ?> tcOwner;
    @FXML
    private Button btnNewOwner;
    @FXML
    private Button btnNewCar;
    @FXML
    private Button btnDeleteCar;
    @FXML
    private Button btnExit;

    void initStage(Parent root) {
        
    }
 
    
}
