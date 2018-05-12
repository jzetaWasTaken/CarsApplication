/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package carsapplication.ui.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

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
      
}
