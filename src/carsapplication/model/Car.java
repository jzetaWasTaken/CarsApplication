/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package carsapplication.model;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Objects;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * Represents cars
 * 
 * @author Jon Zaballa
 */
@Entity
@Table(name="cars", schema="cars_db")
@NamedQueries({
    @NamedQuery(
            name="findCars",
            query="SELECT c FROM Car c ORDER BY c.brand"
    ),
    @NamedQuery(
            name="findCarsByBrand",
            query="SELECT c FROM Car c WHERE c.brand LIKE concat('%', :brand, '%') ORDER BY c.brand"
    ),
    @NamedQuery(
            name="findCarsByOwner",
            query="SELECT c FROM Car c WHERE c.owner.name LIKE concat('%', :owner, '%') OR c.owner.surname LIKE concat('%', :owner, '%') ORDER BY c.brand"
    ),
    @NamedQuery(
            name="findCarsByColor",
            query="SELECT c FROM Car c WHERE c.color LIKE concat('%', :color, '%') ORDER BY c.brand"
    ),
    @NamedQuery(
            name="findCarsByModel",
            query="SELECT c FROM Car c WHERE c.model LIKE concat('%', :model, '%') ORDER BY c.brand"
    ),
    @NamedQuery(
            name="findCarsByPlate",
            query="SELECT c FROM Car c WHERE c.plateNumber LIKE concat('%', :plate, '%') ORDER BY c.brand"
    )
})
public class Car implements Serializable {
    // Fields
    /**
     * The car unique identifier
     */
    private final SimpleObjectProperty<BigInteger> carId;
    /**
     * Car plate number
     */
    private final SimpleStringProperty plateNumber;
    /**
     * Car brand
     */
    private final SimpleStringProperty brand;
    /**
     * Car model
     */
    private final SimpleStringProperty model;
    /**
     * Car color
     */
    private final SimpleStringProperty color;
    /**
     * Car age
     */
    private final SimpleIntegerProperty age;
    /**
     * Car owner
     * 
     * @see Owner
     */
    private final SimpleObjectProperty<Owner> owner;
    
    /**
     * Constructs a car object
     */
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
    
    /**
     * Gets the car identifier
     * 
     * @return car ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="car_id")
    public BigInteger getCarId() {
        return carId.get();
    }
    
    /**
     * Sets the car identifier
     * 
     * @param carId 
     */
    public void setCarId(BigInteger carId) {
        this.carId.set(carId);
    }
    
    /**
     * Gets the plate number
     * 
     * @return plate number
     */
    @Column(name = "plate_number", unique=true)
    public String getPlateNumber() {
        return plateNumber.get();
    }
    
    /**
     * Sets the plate number
     * 
     * @param plateNumber 
     */
    public void setPlateNumber(String plateNumber) {
        this.plateNumber.set(plateNumber);
    }
    
    /**
     * Gets car brand
     * 
     * @return brand
     */
    public String getBrand() {
        return brand.get();
    }
    
    /**
     * Sets car brand
     * 
     * @param brand 
     */
    public void setBrand(String brand) {
        this.brand.set(brand);
    }
    
    /**
     * Gets car model
     * 
     * @return 
     */
    public String getModel() {
        return model.get();
    }
    
    /**
     * Sets car model
     * 
     * @param model 
     */
    public void setModel(String model) {
        this.model.set(model);
    }
    
    /**
     * Gets car color
     * 
     * @return color
     */
    public String getColor() {
        return color.get();
    }
    
    /**
     * Sets car color
     * 
     * @param color 
     */
    public void setColor(String color) {
        this.color.set(color);
    }
    
    /**
     * Gets car age
     * 
     * @return age
     */
    public Integer getAge() {
        return age.get();
    }
    
    /**
     * Sets car age
     * 
     * @param age 
     */
    public void setAge(int age) {
        this.age.set(age);
    }
    
    /**
     * Gets car owner
     * 
     * @return owner
     */
    @ManyToOne
    @JoinColumn(name="owner_code")
    public Owner getOwner() {
        return owner.get();
    }
    
    /**
     * Sets car owner
     * 
     * @param owner 
     */
    public void setOwner(Owner owner) {
        this.owner.set(owner);
    }

    /**
     * Gets the date with format
     * 
     * @return 
     */
    public StringProperty formattedAge() {
        return new SimpleStringProperty(
                String.format("%s year(s)", this.getAge().toString())
        );
    }

    /**
     * Gets owners full name
     * 
     * @return owner full name
     */
    public ObservableValue<String> ownerFullName() {
        return new SimpleStringProperty(this.getOwner().toString());
    }

    /**
     * Overrides toString() method
     * 
     * @return car string
     */
    @Override
    public String toString() {
        return "Car{" + "carId=" + carId + ", plateNumber=" + plateNumber 
                + ", brand=" + brand + ", model=" + model + ", color=" + color 
                + '}';
    }

    /**
     * Overrides hashCode method
     * 
     * @return car hash code
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.carId);
        hash = 59 * hash + Objects.hashCode(this.plateNumber);
        return hash;
    }

    /**
     * Overrides equals method
     * 
     * @param obj
     * @return true if cars are equal; false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Car other = (Car) obj;
        if (!Objects.equals(this.carId, other.carId)) {
            return false;
        }
        if (!Objects.equals(this.plateNumber, other.plateNumber)) {
            return false;
        }
        return true;
    }
}

