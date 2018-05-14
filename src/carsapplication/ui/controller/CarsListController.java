/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package carsapplication.ui.controller;

import carsapplication.exception.CarDBException;
import carsapplication.exception.NoCarException;
import carsapplication.model.Car;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author jon
 */
public class CarsListController extends GenericController {

    @FXML
    private TextField tfSearch;
    @FXML
    private RadioButton rbAllCars;
    @FXML
    private RadioButton rbPlate;
    @FXML
    private RadioButton rbBrand;
    @FXML
    private RadioButton rbModel;
    @FXML
    private RadioButton rbColor;
    @FXML
    private RadioButton rbOwner;
    @FXML
    private TableView tvCars;
    @FXML
    private TableColumn tcPlate;
    @FXML
    private TableColumn tcBrand;
    @FXML
    private TableColumn tcModel;
    @FXML
    private TableColumn tcColor;
    @FXML
    private TableColumn tcAge;
    @FXML
    private TableColumn tcOwner;
    @FXML
    private Button btnNewOwner;
    @FXML
    private Button btnNewCar;
    @FXML
    private Button btnDeleteCar;
    @FXML
    private Button btnExit;
    @FXML
    private Button btnSearch;
    @FXML
    private Button btnUpdate;
    
    private ToggleGroup toggleFilters;
    private ObservableList<Car> tableData;
    
    void initStage(Parent root) {
        try {
            LOGGER.info("Initializing Car List");

            // Set scene
            Scene scene = new Scene(root);
            stage = new Stage();
            stage.setScene(scene);

            // Set stage properties
            stage.setTitle("Cars");
            stage.setResizable(false);
            stage.initStyle(StageStyle.DECORATED);

            // Get session object
            session = manager.getSession();
            
            // On showing listener
            stage.setOnShowing(this::handleWindowShowing);

            // Initialize toggleGroup
            toggleFilters = new ToggleGroup();
            toggleFilters.selectedToggleProperty().addListener(this::handleToggle);

            rbBrand.setToggleGroup(toggleFilters);
            rbColor.setToggleGroup(toggleFilters);
            rbModel.setToggleGroup(toggleFilters);
            rbOwner.setToggleGroup(toggleFilters);
            rbPlate.setToggleGroup(toggleFilters);
            rbAllCars.setToggleGroup(toggleFilters);

            // Button listeners
            btnSearch.setOnAction(this::handleSearch);
            btnUpdate.setOnAction(this::handleUpdate);
            btnExit.setOnAction(this::handleExit);
            btnNewCar.setOnAction(this::handleNewCar);
            btnNewOwner.setOnAction(this::handleNewOwner);
            btnDeleteCar.setOnAction(this::handleDelete);

            // TableView
            tvCars.getSelectionModel().selectedItemProperty().addListener(this::handleTableSelection);
            tvCars.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            tvCars.setPlaceholder(new Label("No cars to show"));
            // Table Columns
            tcBrand.setCellValueFactory(new PropertyValueFactory<>("brand"));
            tcColor.setCellValueFactory(new PropertyValueFactory<>("color"));
            tcColor.setCellFactory(TextFieldTableCell.<Car>forTableColumn());
            tcModel.setCellValueFactory(new PropertyValueFactory<>("model"));
            tcPlate.setCellValueFactory(new PropertyValueFactory<>("plateNumber"));
            tcAge.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Car, String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(TableColumn.CellDataFeatures<Car, String> param) {
                    return param.getValue().formattedAge();
                }
            });
            tcOwner.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Car, String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(TableColumn.CellDataFeatures<Car, String> param) {
                    return param.getValue().ownerFullName();
                }
            });

            // Show Stage
            stage.show();
        } catch (Exception e) {
            showErrorAlert("Error loading Cars Window");
        }
    }
 
    public void handleWindowShowing(WindowEvent event) {
        try {
            // Buttons
            Image imgSearch = new Image(getClass()
                    .getResourceAsStream("/carsapplication/icons/search.png"));
            btnSearch.setGraphic(new ImageView(imgSearch));
            btnUpdate.setDisable(true);
            btnDeleteCar.setDisable(true);

            // Table Data
            tableData = FXCollections.observableArrayList(manager.getCars());
            tvCars.setItems(tableData);
            
            //
            rbPlate.setSelected(true);
            
            // Focus
            Platform.runLater(()->tfSearch.requestFocus());
        } catch (NoCarException e) {
            e.printStackTrace();
            showWarningAlert("No cars");
        } catch (Exception e) {
            e.printStackTrace();
            showErrorAlert("Error loading data");
        }
    }
    
    public void handleSearch(ActionEvent event) {
        if (!tfSearch.getText().trim().equals("")) updateTable();
    }
    
    public void handleUpdate(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/carsapplication/ui/view/cars_form.fxml")
            );
            Parent root = (Parent) loader.load();
            CarsFormController controller = (CarsFormController) loader.getController();
            controller.setUsersManager(manager);
            controller.setSession(session);
            session.put("newCar", false);
            session.put("oldStage", stage);
            session.put("currentCar", (Car) tvCars.getSelectionModel().getSelectedItem());
            controller.setSession(session);
            controller.initStage(root);
            stage.hide();
        } catch (Exception e) {
            e.printStackTrace();
            showErrorAlert("Error Loading Window");
        }
    }
    
    public void handleExit(ActionEvent event) {
        Platform.exit();
    }
    
    public void handleNewCar(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/carsapplication/ui/view/cars_form.fxml")
            );
            Parent root = (Parent) loader.load();
            CarsFormController controller = (CarsFormController) loader.getController();
            controller.setUsersManager(manager);
            controller.setSession(session);
            System.out.println(session == null ? "NULL" : "NOT NULL");
            session.put("newCar", true);
            session.put("oldStage", stage);
            controller.initStage(root);
            stage.hide();
        } catch (Exception e) {
            e.printStackTrace();
            showErrorAlert("Error Loading Window");
        }
    }
    
    public void handleNewOwner(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/carsapplication/ui/view/owners_form.fxml")
            );
            Parent root = (Parent) loader.load();
            OwnersFormController controller = (OwnersFormController) loader.getController();
            controller.setUsersManager(manager);
            controller.setSession(session);
            controller.initStage(root);
            session.put("oldStage", stage);
            stage.hide();
        } catch (Exception e) {
            e.printStackTrace();
            showErrorAlert("Error Loading Window");
        }
    }
    
    public void handleDelete(ActionEvent event) {
        try {
            Car car = (Car) tvCars.getSelectionModel().getSelectedItem();
            manager.deleteCar(car);
            tvCars.getItems().remove(car);
            tvCars.getSelectionModel().clearSelection();
        } catch (Exception e) {
            showErrorAlert("Error deleting car");
        }
    }
    
    public void handleTableSelection(ObservableValue observable, Object oldValue, Object newValue) {
        if (tvCars.getSelectionModel().getSelectedItem() != null) {
            btnDeleteCar.setDisable(false);
            btnUpdate.setDisable(false);
        } else {
            btnDeleteCar.setDisable(true);
            btnUpdate.setDisable(true);
        }
    }

    
    public void handleToggle(ObservableValue observable, Object oldValue, Object newValue) {
        if (newValue != null) {
            tfSearch.setText("");
            if (rbAllCars.isSelected()) resetTable();
        }
    }
            
    private void updateTable() {
        try {
        if (tfSearch.getText().trim().equals("")) {
            tableData = FXCollections.observableArrayList(manager.getCars());
            tvCars.setItems(tableData);
        }
        else if (rbPlate.isSelected()) {
            tableData = FXCollections.observableArrayList(manager.getCarsByPlate(tfSearch.getText()));
            tvCars.setItems(tableData);
        }
        else if (rbBrand.isSelected()) {
            tableData = FXCollections.observableArrayList(manager.getCarsByBrand(tfSearch.getText()));
            tvCars.setItems(tableData);
        }
        else if (rbModel.isSelected()) {
            tableData = FXCollections.observableArrayList(manager.getCarsByModel(tfSearch.getText()));
            tvCars.setItems(tableData);
        }
        else if (rbColor.isSelected()) {
            tableData = FXCollections.observableArrayList(manager.getCarsByColor(tfSearch.getText()));
            tvCars.setItems(tableData);
        }
        else if (rbOwner.isSelected()) {
            tableData = FXCollections.observableArrayList(manager.getCarsByOwnerName(tfSearch.getText()));
            tvCars.setItems(tableData);
        }
        } catch (NoCarException e) {
            tvCars.setItems(null);
            showWarningAlert("No cars found");
        } catch (Exception e) {
            e.printStackTrace();
            showErrorAlert("Unexpected error");
        }
    }

    private void resetTable() {
        try {
            tableData = FXCollections.observableArrayList(manager.getCars());
            tvCars.setItems(tableData);
        } catch (NoCarException ex) {
            tvCars.setItems(null);
            showWarningAlert("No cars found");
        } catch (CarDBException ex) {
            showErrorAlert("Unexpected error");
        }
    }
}
