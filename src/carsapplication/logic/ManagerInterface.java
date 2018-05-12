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

/**
 *
 * @author jon
 */
public interface ManagerInterface {
    
    public List<Car> getCars() throws NoCarException, CarDBException;
    public List<Car> getCarsByBrand(String brand) throws NoCarException, CarDBException;
    public List<Car> getCarsByOwner(Owner owner) throws NoCarException, CarDBException;
    public List<Car> getCarsByColor(String color) throws NoCarException, CarDBException;
    public List<Car> getCarsByModel(String model) throws NoCarException, CarDBException;
    public Car getCar(String plateNumber) throws NoCarException, CarDBException;
    public List<Owner> getOwners() throws NoOwnerException, CarDBException;
    public void registerCar(Car car) throws CarDBException;
    public void registerOwner(Owner owner) throws CarDBException;
    public void modifyCar(Car car) throws CarDBException;
}
