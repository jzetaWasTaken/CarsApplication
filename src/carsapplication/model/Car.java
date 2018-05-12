/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package carsapplication.model;

import java.io.Serializable;
import java.math.BigInteger;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author jon
 */
@Entity
@Table(name="cars", schema="cars_db")
public class Car implements Serializable {
    // Fields
    private final SimpleObjectProperty<BigInteger> carId;
    private final SimpleStringProperty plateNumber;
    private final SimpleStringProperty brand;
    private final SimpleStringProperty model;
    private final SimpleStringProperty color;
    private final SimpleIntegerProperty age;
    private final SimpleObjectProperty<Owner> owner;
    // Construct
    public Car() {
        carId = new SimpleObjectProperty<>();
        plateNumber = new SimpleStringProperty();
        brand = new SimpleStringProperty();
        model = new SimpleStringProperty();
        color = new SimpleStringProperty();
        age = new SimpleIntegerProperty();
        owner = new SimpleObjectProperty<>();
    }
    // Getters & setters
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="car_id")
    public BigInteger getCarId() {
        return carId.get();
    }
    
    public void setCarId(BigInteger carId) {
        this.carId.set(carId);
    }
    
    @Column(name = "plate_number", unique=true)
    public String getPlateNumber() {
        return plateNumber.get();
    }
    
    public void setPlateNumber(String plateNumber) {
        this.plateNumber.set(plateNumber);
    }
    
    public String getBrand() {
        return brand.get();
    }
    
    public void setBrand(String brand) {
        this.brand.set(brand);
    }
    
    public String getModel() {
        return model.get();
    }
    
    public void setModel(String model) {
        this.model.set(model);
    }
    
    public String getColor() {
        return color.get();
    }
    
    public void setColor(String color) {
        this.color.set(color);
    }
    
    public Integer getAge() {
        return age.get();
    }
    
    public void setAge(int age) {
        this.age.set(age);
    }
    
    @ManyToOne
    public Owner getOwner() {
        return owner.get();
    }
    
    public void setOwner(Owner owner) {
        this.owner.set(owner);
    }
}

