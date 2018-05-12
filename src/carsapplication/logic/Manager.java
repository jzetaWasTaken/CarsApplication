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
import java.util.List;

/**
 *
 * @author jon
 */
public class Manager implements ManagerInterface {

    private final DAOInterface dao;
    private static final String NO_CAR_MSG = "No cars found";
    private static final String NO_OWNER_MSG = "No owners found";
    
    Manager(DBType type) throws CarDBException {
        dao = DAOFactory.newDAO(type);
    }
    
    @Override
    public List<Car> getCars() throws CarDBException, NoCarException {
        List<Car> cars = dao.findCars();
        if (cars == null || cars.isEmpty()) 
            throw new NoCarException(NO_CAR_MSG);
        return cars;
    }

    @Override
    public List<Car> getCarsByBrand(String brand) 
            throws NoCarException, CarDBException {
        List<Car> cars = dao.findCarsByBrand(brand);
        if (cars == null || cars.isEmpty()) 
            throw new NoCarException(NO_CAR_MSG);
        return cars;
    }

    @Override
    public List<Car> getCarsByOwner(Owner owner) 
            throws NoCarException, CarDBException {
        List<Car> cars = dao.findCarsByOwner(owner);
        if (cars == null || cars.isEmpty()) 
            throw new NoCarException(NO_CAR_MSG);
        return cars;
    }

    @Override
    public List<Car> getCarsByColor(String color) 
            throws NoCarException, CarDBException {
        List<Car> cars = dao.findCarsByColor(color);
        if (cars == null || cars.isEmpty()) 
            throw new NoCarException(NO_CAR_MSG);
        return cars;
    }

    @Override
    public List<Car> getCarsByModel(String model) 
            throws NoCarException, CarDBException {
        List<Car> cars = dao.findCarsByModel(model);
        if (cars == null || cars.isEmpty()) 
            throw new NoCarException(NO_CAR_MSG);
        return cars;
    }

    @Override
    public Car getCar(String plateNumber) 
            throws NoCarException, CarDBException {
        Car car = dao.findCar(plateNumber);
        if (car == null) 
            throw new NoCarException(NO_CAR_MSG);
        return car;
    }

    @Override
    public List<Owner> getOwners() throws NoOwnerException, CarDBException {
        List<Owner> owners = dao.findOwners();
        if (owners == null || owners.isEmpty()) 
            throw new NoOwnerException(NO_OWNER_MSG);
        return owners;
    }

    @Override
    public void registerCar(Car car) throws CarDBException {
        dao.createCar(car);
    }

    @Override
    public void registerOwner(Owner owner) throws CarDBException {
        dao.createOwner(owner);
    }

    @Override
    public void modifyCar(Car car) throws CarDBException {
        dao.updateCar(car);
    }
}
