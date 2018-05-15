/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package carsapplication.model;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import java.util.Objects;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Represents car owners
 * 
 * @author Jon Zaballa
 */
@Entity
@Table(name="owners", schema="cars_db")
@NamedQueries({
    @NamedQuery(
            name="findOwners",
            query="SELECT o FROM Owner o ORDER BY o.surname"
    )
})
public class Owner implements Serializable {
    // Fields
    
    /**
     * Owners unique identifier
     */
    private final SimpleObjectProperty<BigInteger> ownerCode;
    
    /**
     * Owner's name
     */
    private final SimpleStringProperty name;
    
    /**
     * Owner's surname
     */
    private final SimpleStringProperty surname;
    
    /**
     * Owner's date of birth
     */
    private final SimpleObjectProperty<Date> dateOfBirth;
    
    /**
     * Constructs an owner instance
     */
    public Owner() {
        ownerCode = new SimpleObjectProperty<>();
        name = new SimpleStringProperty();
        surname =  new SimpleStringProperty();
        dateOfBirth = new SimpleObjectProperty<>();
    }
    
    // Getters & setters
    
    /**
     * Gets owner identifier
     * 
     * @return owner code
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="owner_code")
    public BigInteger getOwnerCode() {
        return ownerCode.get();
    }
    
    /**
     * Sets owner identifier
     * 
     * @param ownerCode 
     */
    public void setOwnerCode(BigInteger ownerCode) {
        this.ownerCode.set(ownerCode);
    }
    
    /**
     * Gets owner name
     * 
     * @return owner name
     */
    public String getName() {
        return name.get();
    }
    
    /**
     * Sets owner name
     * 
     * @param name 
     */
    public void setName(String name) {
        this.name.set(name);
    }
    
    /**
     * Gets owner surname
     * 
     * @return owner surname
     */
    public String getSurname() {
        return surname.get();
    }
    
    /**
     * Sets owner surname
     * 
     * @param surname 
     */
    public void setSurname(String surname) {
        this.surname.set(surname);
    }
    
    /**
     * Gets owner birth date
     * 
     * @return owner birth date
     */
    @Temporal(TemporalType.DATE)
    @Column(name="date_of_birth")
    public Date getDateOfBirth() {
        return dateOfBirth.get();
    }
    
    /**
     * Sets owner birth date
     * 
     * @param dateOfBirth 
     */
    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth.set(dateOfBirth);
    }

    /**
     * Overrides toString() method
     * 
     * @return owner string
     */
    @Override
    public String toString() {
        return String.format("%s %s", this.getName(), this.getSurname());
    }

    /**
     * Overrides hashCode method
     * 
     * @return owner hash code
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.ownerCode);
        return hash;
    }

    /**
     * Overrides equals method
     * 
     * @param obj
     * @return true if owners are equal; false otherwise
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
        final Owner other = (Owner) obj;
        if (!Objects.equals(this.ownerCode, other.ownerCode)) {
            return false;
        }
        return true;
    }
}

