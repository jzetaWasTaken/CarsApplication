/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package carsapplication.ui.controller;

import carsapplication.exception.CarDBException;
import carsapplication.logic.ManagerFactory;
import carsapplication.model.dao.DBType;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * FXML Controller class to handle the database selection view
 *
 * @author Jon Zaballa
 */
public class DbSelectionController extends GenericController {

    @FXML
    private Button btnOracle;
    @FXML
    private Button btnMongo;
    @FXML
    private Button btnHibernate;

    public void initStage(Parent root) {
        try {
            LOGGER.info("Initializing Database Selection Window");

            // Set scene
            Scene scene = new Scene(root);
            if (stage == null) stage = new Stage();
            stage.setScene(scene);

            // Set stage properties
            stage.setTitle("Database Selection");
            stage.setResizable(false);

            // On showing listener
            stage.setOnShowing(this::handleWindowShowing);

            // Graphical node listeners
            btnOracle.setOnAction(this::handleOracleAction);
            btnMongo.setOnAction(this::handleMongoAction);
            btnHibernate.setOnAction(this::handleHibernateAction);

            // Show stage
            stage.show();
        } catch (Exception e) {
            showErrorAlert("Error loading Database Selection Window");
        }
    }
    
    public void handleWindowShowing(WindowEvent event) {
        Image imgOracle = new Image(getClass()
                .getResourceAsStream("/carsapplication/icons/oracle.png"));
        Image imgMongo = new Image(getClass()
                .getResourceAsStream("/carsapplication/icons/mongo.png"));
        Image imgHibernate = new Image(getClass()
                .getResourceAsStream("/carsapplication/icons/hibernate.png"));
        btnOracle.setGraphic(new ImageView(imgOracle));
        btnOracle.setText("Oracle");
        btnMongo.setGraphic(new ImageView(imgMongo));
        btnMongo.setText("Mongo");
        btnHibernate.setGraphic(new ImageView(imgHibernate));
        btnHibernate.setText("Hibernate");
    }
    
    public void handleOracleAction(ActionEvent event) {
        handleAction(DBType.ORACLE);
    }
    
    public void handleMongoAction(ActionEvent event) {
        handleAction(DBType.MONGO_DB);
    }
    
    public void handleHibernateAction(ActionEvent event) {
        handleAction(DBType.HIBERNATE);
    }
    
    private void handleAction(DBType type) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/carsapplication/ui/view/cars_list.fxml")
            );
            Parent root = (Parent) loader.load();
            CarsListController controller = (CarsListController)loader.getController();
            controller.setUsersManager(ManagerFactory.newManager(type));
            stage.close();
            controller.initStage(root);
        } catch (CarDBException e) {
            showErrorAlert("Database Error");
        } catch (IOException e) {
            showErrorAlert("Error Loading Window");
        }
    }
}
