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
import java.util.List;
import java.util.Map;

/**
 * Abstraction of business logic manager classes
 * 
 * @author Jon Zaballa
 */
public interface ManagerInterface {
    
    /**
     * Get session data
     * 
     * @return session
     */
    public Map getSession();
    
    /**
     * Gets all cars
     * 
     * @return cars
     * @throws NoCarException
     * @throws CarDBException 
     */
    public List<Car> getCars() throws NoCarException, CarDBException;
    
    /**
     * Gets cars from a given brand
     * 
     * @param brand
     * @return cars
     * @throws NoCarException
     * @throws CarDBException 
     */
    public List<Car> getCarsByBrand(String brand) throws NoCarException, CarDBException;
    
    /**
     * Gets cars from a given owner
     * 
     * @param ownerName
     * @return cars
     * @throws NoCarException
     * @throws CarDBException 
     */
    public List<Car> getCarsByOwnerName(String ownerName) throws NoCarException, CarDBException;
    
    /**
     * Gets cars from a given color
     * 
     * @param color
     * @return cars
     * @throws NoCarException
     * @throws CarDBException 
     */
    public List<Car> getCarsByColor(String color) throws NoCarException, CarDBException;
    
    /**
     * Gets cars from a given model
     * 
     * @param model
     * @return cars
     * @throws NoCarException
     * @throws CarDBException 
     */
    public List<Car> getCarsByModel(String model) throws NoCarException, CarDBException;
    
    /**
     * Gets cars given a plate number
     * 
     * @param plateNumber
     * @return cars
     * @throws NoCarException
     * @throws CarDBException 
     */
    public List<Car> getCarsByPlate(String plateNumber) throws NoCarException, CarDBException;
    
    /**
     * Gets all owners
     * 
     * @return cars
     * @throws carsapplication.exception.NoOwnerException
     * @throws CarDBException 
     */
    public List<Owner> getOwners() throws NoOwnerException, CarDBException;
    
    /**
     * Creates a new car
     * 
     * @param car
     * @throws CarDBException 
     */
    public void registerCar(Car car) throws CarDBException;
    
    /**
     * Creates a new owner
     * 
     * @param owner
     * @throws CarDBException 
     */
    public void registerOwner(Owner owner) throws CarDBException;
    
    /**
     * Modifies a car
     * 
     * @param car
     * @throws CarDBException 
     */
    public void modifyCar(Car car) throws CarDBException;
    
    /**
     * Deletes a car
     * 
     * @param car
     * @throws CarDBException 
     */
    public void deleteCar(Car car) throws CarDBException;
}
