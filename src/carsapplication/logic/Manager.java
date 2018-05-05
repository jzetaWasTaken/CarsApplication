/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package carsapplication.logic;

import carsapplication.exception.CarDBException;
import carsapplication.exception.NoCarException;
import carsapplication.exception.NoOwnerException;
import carsapplication.model.Car;
import carsapplication.model.Owner;
import carsapplication.model.dao.DAOFactory;
import carsapplication.model.dao.DAOInterface;
import carsapplication.model.dao.DBType;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author jon
 */
public class Manager implements ManagerInterface {

    private final DAOInterface dao;
    
    Manager(DBType type) {
        dao = DAOFactory.newDAO(type);
    }

    @Override
    public DAOInterface getDao(DBType type) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public List<Car> getCars() throws NoCarException, CarDBException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Car> getCarsByBrand(String brand) throws NoCarException, CarDBException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Car> getCarsByOwner(Owner owner) throws NoCarException, CarDBException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Car> getCarsByColor(String color) throws NoCarException, CarDBException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Car> getCarsByModel(String model) throws NoCarException, CarDBException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Car getCar(String plateNumber) throws NoCarException, CarDBException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Owner> getOwners() throws NoOwnerException, CarDBException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void registerCar() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void registerOwner() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void modifyCar() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void modifyOwner() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}

