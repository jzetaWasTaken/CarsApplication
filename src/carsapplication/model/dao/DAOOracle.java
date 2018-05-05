/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package carsapplication.model.dao;

import carsapplication.exception.CarDBException;
import carsapplication.model.Car;
import carsapplication.model.Owner;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jon
 */
public class DAOOracle implements DAOInterface {

    private Connection connection;
    private String host;
    private String dbName;
    private String dbUser;
    private String dbPassword;
    private String dbPort;
    private PreparedStatement statement;

    public DAOOracle() throws CarDBException {
        try (FileInputStream input = new FileInputStream("db.properties")) {
            Properties config = new Properties();
            config.load(input);
            host = config.getProperty("ip");
            dbName = config.getProperty("dbname");
            dbUser = config.getProperty("user");
            dbPassword = config.getProperty("password");
            dbPort = config.getProperty("port");
        } catch (IOException e) {
            throw new CarDBException(e.getMessage());
        }
    }
    
    private void connect() throws CarDBException {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            String url = String.format("jdbc:oracle:thin:@%s:%s:%s", host, dbPort, dbName);
            connection = DriverManager.getConnection(url, dbUser, dbPassword);
        } catch (ClassNotFoundException | SQLException e) {
            throw new CarDBException(e.getMessage());
        }
    }
    
    private void disconnect() throws CarDBException {
        try {
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        } catch (SQLException e) {
            throw new CarDBException(e.getMessage());
        }
    }
    
    @Override
    public List<Car> findCars() throws CarDBException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Car> findCarsByBrand(String brand) throws CarDBException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Car> findCarsByOwner(Owner owner) throws CarDBException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Car> findCarsByColor(String color) throws CarDBException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Car> findCarsByModel(String model) throws CarDBException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Car findCar(String plateNumber) throws CarDBException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Owner> findOwners() throws CarDBException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void createCar() throws CarDBException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void createOwner() throws CarDBException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void updateCar() throws CarDBException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void updateOwner() throws CarDBException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
