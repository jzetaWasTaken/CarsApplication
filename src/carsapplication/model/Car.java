/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package carsapplication.model;

import java.io.Serializable;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author jon
 */
public class Car implements Serializable {
    // Fields
    private final SimpleStringProperty plateNumber;
    private final SimpleStringProperty brand;
    private final SimpleStringProperty model;
    private final SimpleStringProperty color;
    private final SimpleIntegerProperty age;
    private final SimpleObjectProperty<Owner> owner;
    // Construct
    public Car() {
        plateNumber = new SimpleStringProperty();
        brand = new SimpleStringProperty();
        model = new SimpleStringProperty();
        color = new SimpleStringProperty();
        age = new SimpleIntegerProperty();
        owner = new SimpleObjectProperty<>();
    }
    // Getters & setters
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
    public Owner getOwnerCode() {
        return owner.get();
    }
    public void setOwnerCode(Owner owner) {
        this.owner.set(owner);
    }
}

