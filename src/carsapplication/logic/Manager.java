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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Manager implementation. Contains the application business logic
 * 
 * @author Jon Zaballa
 */
public class Manager implements ManagerInterface {

    /**
     * Data access object instance
     */
    private final DAOInterface dao;
    
    /**
     * Application session data
     */
    private static final Map SESSION = new HashMap<>();
    
    /**
     * No cars found message
     */
    private static final String NO_CAR_MSG = "No cars found";
    
    /**
     * No owners found message
     */
    private static final String NO_OWNER_MSG = "No owners found";
    
    /**
     * Constructs a new manager object and initializes data access object 
     * instance
     * 
     * @param type
     * @throws CarDBException 
     */
    Manager(DBType type) throws CarDBException {
        dao = DAOFactory.newDAO(type);
    }
    
    /**
     * Gets the current session data object
     * 
     * @return session
     */
    @Override 
    public Map getSession() {
        return SESSION;
    }
    
    /**
     * Gets all cars
     * 
     * @return cars
     * @throws CarDBException
     * @throws NoCarException 
     */
    @Override
    public List<Car> getCars() throws CarDBException, NoCarException {
        List<Car> cars = dao.findCars();
        if (cars == null || cars.isEmpty()) 
            throw new NoCarException(NO_CAR_MSG);
        return cars;
    }

    /**
     * Gets cars from a given brand
     * 
     * @param brand
     * @return cars
     * @throws NoCarException
     * @throws CarDBException 
     */
    @Override
    public List<Car> getCarsByBrand(String brand) 
            throws NoCarException, CarDBException {
        List<Car> cars = dao.findCarsByBrand(brand);
        if (cars == null || cars.isEmpty()) 
            throw new NoCarException(NO_CAR_MSG);
        return cars;
    }

    /**
     * Gets cars from a given owner
     * 
     * @param ownerName
     * @return cars
     * @throws NoCarException
     * @throws CarDBException 
     */
    @Override
    public List<Car> getCarsByOwnerName(String ownerName) 
            throws NoCarException, CarDBException {
        List<Car> cars = dao.findCarsByOwnerName(ownerName);
        if (cars == null || cars.isEmpty()) 
            throw new NoCarException(NO_CAR_MSG);
        return cars;
    }

    /**
     * Gets cars from a given color
     * 
     * @param color
     * @return cars
     * @throws NoCarException
     * @throws CarDBException 
     */
    @Override
    public List<Car> getCarsByColor(String color) 
            throws NoCarException, CarDBException {
        List<Car> cars = dao.findCarsByColor(color);
        if (cars == null || cars.isEmpty()) 
            throw new NoCarException(NO_CAR_MSG);
        return cars;
    }

    /**
     * Gets cars from a given model
     * 
     * @param model
     * @return cars
     * @throws NoCarException
     * @throws CarDBException 
     */
    @Override
    public List<Car> getCarsByModel(String model) 
            throws NoCarException, CarDBException {
        List<Car> cars = dao.findCarsByModel(model);
        if (cars == null || cars.isEmpty()) 
            throw new NoCarException(NO_CAR_MSG);
        return cars;
    }

    /**
     * Gets cars given a plate number
     * 
     * @param plateNumber
     * @return cars
     * @throws NoCarException
     * @throws CarDBException 
     */
    @Override
    public List<Car> getCarsByPlate(String plateNumber) 
            throws NoCarException, CarDBException {
        List<Car> cars = dao.findCarsByPlate(plateNumber);
        if (cars == null || cars.isEmpty()) 
            throw new NoCarException(NO_CAR_MSG);
        return cars;
    }

    /**
     * Gets all owners
     * 
     * @return cars
     * @throws carsapplication.exception.NoOwnerException
     * @throws CarDBException 
     */
    @Override
    public List<Owner> getOwners() throws NoOwnerException, CarDBException {
        List<Owner> owners = dao.findOwners();
        if (owners == null || owners.isEmpty()) 
            throw new NoOwnerException(NO_OWNER_MSG);
        return owners;
    }

    /**
     * Creates a new car
     * 
     * @param car
     * @throws CarDBException 
     */
    @Override
    public void registerCar(Car car) throws CarDBException {
        dao.createCar(car);
    }

    /**
     * Creates a new owner
     * 
     * @param owner
     * @throws CarDBException 
     */
    @Override
    public void registerOwner(Owner owner) throws CarDBException {
        dao.createOwner(owner);
    }

    /**
     * Modifies a car
     * 
     * @param car
     * @throws CarDBException 
     */
    @Override
    public void modifyCar(Car car) throws CarDBException {
        dao.updateCar(car);
    }

    /**
     * Deletes a car
     * 
     * @param car
     * @throws CarDBException 
     */
    @Override
    public void deleteCar(Car car) throws CarDBException {
        dao.deleteCar(car);
    }
}
