/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package carsapplication.model.dao;

import carsapplication.exception.CarDBException;
import carsapplication.model.Car;
import carsapplication.model.Owner;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import org.bson.Document;

/**
 *
 * @author jon
 */
public class DAOMongo implements DAOInterface {

    private MongoClient getClient() throws CarDBException {
        try {
            return new MongoClient("localhost",27017);
        } catch (Exception e) {
            throw new CarDBException(e.getCause());
        }
    } 
    
    @Override
    public List<Car> findCars() throws CarDBException {
        List<Car> cars = null;
        try (MongoClient client = getClient()) {
            MongoDatabase database = client.getDatabase("cars_db");
            FindIterable<Document> carsDoc = database.getCollection("cars").find();
            cars = mapCars(carsDoc);
        } catch (Exception e) {
            throw new CarDBException(e.getCause());
        }
        
        return cars;
    }

    private List<Car> mapCars(FindIterable<Document> carsDoc) throws CarDBException {
        List<Car> cars = new ArrayList<>();
        MongoCursor<Document> cursor = carsDoc.iterator();
        while (cursor.hasNext()) {
            Document doc = cursor.next();
            Car car = new Car();
            String carIdHex = doc.get("_id").toString();
            BigInteger id = new BigInteger(carIdHex, 16);
            car.setCarId(id);
            car.setAge(doc.getInteger("age"));
            car.setBrand(doc.getString("brand"));
            car.setColor(doc.getString("color"));
            car.setModel(doc.getString("model"));
            car.setPlateNumber(doc.getString("plate_number"));
            
            String ownerCode = doc.getString("owner");
            car.setOwner(findOwnerByCode(new BigInteger(ownerCode, 16)));

            cars.add(car);
        }
        return cars;
    }

    @Override
    public List<Car> findCarsByBrand(String brand) throws CarDBException {
        List<Car> cars = null;
        try (MongoClient client = getClient()) {
            MongoDatabase database = client.getDatabase("cars_db");
            MongoCollection<Document> collection = database.getCollection("cars");
            BasicDBObject query = new BasicDBObject("brand", brand);
            FindIterable<Document> carsDoc = collection.find(query);
            cars = mapCars(carsDoc);
        } catch (Exception e) {
            throw new CarDBException(e.getCause());
        }
        return cars;
    }

    @Override
    public List<Car> findCarsByOwner(Owner owner) throws CarDBException {
        List<Car> cars = null;
        try (MongoClient client = getClient()) {
            MongoDatabase database = client.getDatabase("cars_db");
            MongoCollection<Document> collection = database.getCollection("cars");
            BasicDBObject query = new BasicDBObject(
                    "owner", owner.getOwnerCode().toString(16)
            );
            FindIterable<Document> carsDoc = collection.find(query);
            cars = mapCars(carsDoc);
        } catch (Exception e) {
            throw new CarDBException(e.getCause());
        }
        return cars;
    }

    @Override
    public List<Car> findCarsByColor(String color) throws CarDBException {
        List<Car> cars = null;
        try (MongoClient client = getClient()) {
            MongoDatabase database = client.getDatabase("cars_db");
            MongoCollection<Document> collection = database.getCollection("cars");
            BasicDBObject query = new BasicDBObject("color", color);
            FindIterable<Document> carsDoc = collection.find(query);
            cars = mapCars(carsDoc);
        } catch (Exception e) {
            throw new CarDBException(e.getCause());
        }
        return cars;
    }

    @Override
    public List<Car> findCarsByModel(String model) throws CarDBException {
        List<Car> cars = null;
        try (MongoClient client = getClient()) {
            MongoDatabase database = client.getDatabase("cars_db");
            MongoCollection<Document> collection = database.getCollection("cars");
            BasicDBObject query = new BasicDBObject("model", model);
            FindIterable<Document> carsDoc = collection.find(query);
            cars = mapCars(carsDoc);
        } catch (Exception e) {
            throw new CarDBException(e.getCause());
        }
        return cars;
    }

    @Override
    public Car findCar(String plateNumber) throws CarDBException {
        List<Car> cars = null;
        try (MongoClient client = getClient()) {
            MongoDatabase database = client.getDatabase("cars_db");
            MongoCollection<Document> collection = database.getCollection("cars");
            BasicDBObject query = new BasicDBObject("brand", plateNumber);
            FindIterable<Document> carsDoc = collection.find(query);
            cars = mapCars(carsDoc);
        } catch (Exception e) {
            throw new CarDBException(e.getCause());
        }
        if (cars != null) return cars.get(0);
        else return null;
    }

    @Override
    public List<Owner> findOwners() throws CarDBException {
        List<Owner> owners = null;
        try (MongoClient client = getClient()) {
            MongoDatabase database = client.getDatabase("cars_db");
            FindIterable<Document> ownersDoc = database.getCollection("owners").find();
            owners = mapOwners(ownersDoc);
        } catch (Exception e) {
            throw new CarDBException(e.getCause());
        }
        
        return owners;
    }
    
    private Owner findOwnerByCode(BigInteger code) throws CarDBException {
        Owner owner = null;
        try (MongoClient client = getClient()) {
            MongoDatabase database = client.getDatabase("cars_db");
            MongoCollection<Document> collection = database.getCollection("owners");
            String codeHex = code.toString(16);
            BasicDBObject query = new BasicDBObject("_id", codeHex);
            FindIterable<Document> ownersDoc = collection.find(query);
            MongoCursor<Document> cursor = ownersDoc.iterator();
            while (cursor.hasNext()) {
                owner = mapOwner(cursor.next());
            }
        } catch (Exception e) {
            throw new CarDBException(e.getCause());
        }
        return owner;
    }

    @Override
    public void createCar(Car car) throws CarDBException {
        try (MongoClient client = getClient()) {
            MongoDatabase database = client.getDatabase("cars_db");
            MongoCollection<Document> collection = database.getCollection("cars");
            Document doc = new Document("brand", car.getBrand())
                    .append("plate_number", car.getPlateNumber())
                    .append("model", car.getModel())
                    .append("color", car.getColor())
                    .append("age", car.getAge())
                    .append("owner", car.getOwner().getOwnerCode().toString(16));
            collection.insertOne(doc);
        }
    }

    @Override
    public void createOwner(Owner owner) throws CarDBException {
        try (MongoClient client = getClient()) {
            MongoDatabase database = client.getDatabase("cars_db");
            MongoCollection<Document> collection = database.getCollection("owners");
            Document doc = new Document("name", owner.getName())
                    .append("surname", owner.getSurname())
                    .append("birth_date", owner.getDateOfBirth());
            collection.insertOne(doc);
        }
    }

    @Override
    public void updateCar(Car car) throws CarDBException {
        try (MongoClient client = getClient()) {
            MongoDatabase database = client.getDatabase("cars_db");
            MongoCollection<Document> collection = database.getCollection("cars");
            BasicDBObject query = 
                    new BasicDBObject("_id", car.getCarId().toString(16));
            BasicDBObject doc = new BasicDBObject("brand", car.getBrand())
                    .append("plate_number", car.getPlateNumber())
                    .append("model", car.getModel())
                    .append("color", car.getColor())
                    .append("age", car.getAge())
                    .append("owner", car.getOwner().getOwnerCode().toString(16));
            collection.findOneAndUpdate(query, doc);
        }
    }

    @Override
    public void deleteCar(Car car) throws CarDBException {
        try (MongoClient client = getClient()) {
            MongoDatabase database = client.getDatabase("cars_db");
            MongoCollection<Document> collection = database.getCollection("cars");
            BasicDBObject query = 
                    new BasicDBObject("_id", car.getCarId().toString(16));
            collection.findOneAndDelete(query);
        }
    }

    private Owner mapOwner(Document ownerDoc) {
        Owner owner = new Owner();
        owner.setName(ownerDoc.getString("name"));
        owner.setSurname(ownerDoc.getString("surname"));
        owner.setDateOfBirth(ownerDoc.getDate("birth_date"));
        String ownerCodeHex = ownerDoc.get("_id").toString();
        owner.setOwnerCode(new BigInteger(ownerCodeHex,16));
        
        return owner;
    }

    private List<Owner> mapOwners(FindIterable<Document> ownersDoc) {
        List<Owner> owners = new ArrayList<>();
        MongoCursor<Document> cursor = ownersDoc.iterator();
        while (cursor.hasNext()) {
            Document ownerDoc = cursor.next();
            Owner owner = new Owner();
            owner.setName(ownerDoc.getString("name"));
            owner.setSurname(ownerDoc.getString("surname"));
            owner.setDateOfBirth(ownerDoc.getDate("birth_date"));
            String codeHex = ownerDoc.get("_id").toString();
            owner.setOwnerCode(new BigInteger(codeHex, 16));
            owners.add(owner);
        }
        return owners;
    }
}
