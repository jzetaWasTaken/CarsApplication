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
 *
 * @author jon
 */
public interface DAOInterface {
    public List<Car> findCars() throws CarDBException;
    public List<Car> findCarsByBrand(String brand) throws CarDBException;
    public List<Car> findCarsByOwnerName(String ownerName) throws CarDBException;
    public List<Car> findCarsByColor(String color) throws CarDBException;
    public List<Car> findCarsByModel(String model) throws CarDBException;
    public List<Car> findCarsByPlate(String plateNumber) throws CarDBException;
    public List<Owner> findOwners() throws CarDBException;
    public void createCar(Car car) throws CarDBException;
    public void createOwner(Owner owner) throws CarDBException;
    public void updateCar(Car car) throws CarDBException;
    public void deleteCar(Car car) throws CarDBException;
}
