/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package carsapplication.model.dao;

import carsapplication.exception.CarDBException;
import carsapplication.model.Car;
import carsapplication.model.Owner;
import java.util.List;

/**
 * Abstraction of data access object classes
 * 
 * @author Jon Zaballa
 */
public interface DAOInterface {
    
    /**
     * Gets all cars
     * 
     * @return cars
     * @throws CarDBException 
     */
    public List<Car> findCars() throws CarDBException;
    
    /**
     * Gets cars from a given brand
     * 
     * @param brand
     * @return cars
     * @throws CarDBException 
     */
    public List<Car> findCarsByBrand(String brand) throws CarDBException;
    
    /**
     * Gets cars from a given owner
     * 
     * @param ownerName
     * @return cars
     * @throws CarDBException 
     */
    public List<Car> findCarsByOwnerName(String ownerName) throws CarDBException;
    
    /**
     * Gets cars from a given color
     * 
     * @param color
     * @return cars
     * @throws CarDBException 
     */
    public List<Car> findCarsByColor(String color) throws CarDBException;
    
    /**
     * Gets cars from a given model
     * 
     * @param model
     * @return cars
     * @throws CarDBException 
     */
    public List<Car> findCarsByModel(String model) throws CarDBException;
    
    /**
     * Gets cars given a plate number
     * 
     * @param plateNumber
     * @return cars
     * @throws CarDBException 
     */
    public List<Car> findCarsByPlate(String plateNumber) throws CarDBException;
    
    /**
     * Gets all owners
     * 
     * @return cars
     * @throws CarDBException 
     */
    public List<Owner> findOwners() throws CarDBException;
    
    /**
     * Creates a new car
     * 
     * @param car
     * @throws CarDBException 
     */
    public void createCar(Car car) throws CarDBException;
    
    /**
     * Creates a new owner
     * 
     * @param owner
     * @throws CarDBException 
     */
    public void createOwner(Owner owner) throws CarDBException;
    
    /**
     * Modifies a car
     * 
     * @param car
     * @throws CarDBException 
     */
    public void updateCar(Car car) throws CarDBException;
    
    /**
     * Deletes a car
     * 
     * @param car
     * @throws CarDBException 
     */
    public void deleteCar(Car car) throws CarDBException;
}
