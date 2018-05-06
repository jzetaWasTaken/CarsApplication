/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package carsapplication.model.dao;

import carsapplication.exception.CarDBException;
import carsapplication.model.Car;
import carsapplication.model.Owner;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import java.util.ArrayList;
import java.util.List;
import org.bson.Document;

/**
 *
 * @author jon
 */
public class DAOMongo implements DAOInterface {

    private MongoClient getClient() {
        return new MongoClient("localhost",27017);
    } 
    
    @Override
    public List<Car> findCars() throws CarDBException {
        List<Car> cars = new ArrayList<>();
        MongoClient client = getClient();
        MongoDatabase database = client.getDatabase("cars_db");
        FindIterable<Document> docs = database.getCollection("cars").find();
        for(Document doc : docs) {
            Car car = new Car();
            car.setCarId(doc.getInteger("_id"));
            car.setAge(doc.getInteger("age"));
            car.setBrand(doc.getString("brand"));
            car.setColor(doc.getString("color"));
            car.setModel(doc.getString("model"));
            car.setPlateNumber(doc.getString("plate_number"));
            car.setOwner((Owner) doc.get("owner"));
        }
        
        
        client.close();
        return null;
    }

    @Override
    public List<Car> findCarsByBrand(String brand) throws CarDBException {
        MongoClient client = getClient();
        MongoDatabase database = client.getDatabase("cars_db");
        
        client.close();
        return null;
    }

    @Override
    public List<Car> findCarsByOwner(Owner owner) throws CarDBException {
        MongoClient client = getClient();
        MongoDatabase database = client.getDatabase("cars_db");
        
        client.close();
        return null;
    }

    @Override
    public List<Car> findCarsByColor(String color) throws CarDBException {
        MongoClient client = getClient();
        MongoDatabase database = client.getDatabase("cars_db");
        
        client.close();
        return null;
    }

    @Override
    public List<Car> findCarsByModel(String model) throws CarDBException {
        MongoClient client = getClient();
        MongoDatabase database = client.getDatabase("cars_db");
        
        client.close();
        return null;
    }

    @Override
    public Car findCar(String plateNumber) throws CarDBException {
        MongoClient client = getClient();
        MongoDatabase database = client.getDatabase("cars_db");
        
        client.close();
        return null;
    }

    @Override
    public List<Owner> findOwners() throws CarDBException {
        MongoClient client = getClient();
        MongoDatabase database = client.getDatabase("cars_db");
        
        client.close();
        return null;
    }

    @Override
    public void createCar(Car car) throws CarDBException {
        MongoClient client = getClient();
        MongoDatabase database = client.getDatabase("cars_db");
        
        client.close();
    }

    @Override
    public void createOwner(Owner owner) throws CarDBException {
        MongoClient client = getClient();
        MongoDatabase database = client.getDatabase("cars_db");
        
        client.close();
    }

    @Override
    public void updateCar(Car car) throws CarDBException {
        MongoClient client = getClient();
        MongoDatabase database = client.getDatabase("cars_db");
        
        client.close();
    }

    @Override
    public void deleteCar(Car car) throws CarDBException {
        MongoClient client = getClient();
        MongoDatabase database = client.getDatabase("cars_db");
        
        client.close();
    }
}
