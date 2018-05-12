/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package carsapplication.model;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author jon
 */
@Entity
@Table(name="owners", schema="cars_db")
public class Owner implements Serializable {
    // Fields
    private final SimpleObjectProperty<BigInteger> ownerCode;
    private final SimpleStringProperty name;
    private final SimpleStringProperty surname;
    private final SimpleObjectProperty<Date> dateOfBirth;
    // Constructors
    public Owner() {
        ownerCode = new SimpleObjectProperty<>();
        name = new SimpleStringProperty();
        surname =  new SimpleStringProperty();
        dateOfBirth = new SimpleObjectProperty<>();
    }
    // Getters & setters
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="owner_code")
    public BigInteger getOwnerCode() {
        return ownerCode.get();
    }
    
    public void setOwnerCode(BigInteger ownerCode) {
        this.ownerCode.set(ownerCode);
    }
    
    public String getName() {
        return name.get();
    }
    
    public void setName(String name) {
        this.name.set(name);
    }
    
    public String getSurname() {
        return surname.get();
    }
    
    public void setSurname(String surname) {
        this.surname.set(surname);
    }
    
    @Temporal(TemporalType.DATE)
    @Column(name="date_of_birth")
    public Date getDateOfBirth() {
        return dateOfBirth.get();
    }
    
    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth.set(dateOfBirth);
    }
}

